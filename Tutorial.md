# Introduction #

You can get the source code from svn (unless a binary version is released).

Folder src/ contains the middleware, that includes 7 packages:
```
it.unipr.ce.dsg.nam4j.interfaces
it.unipr.ce.dsg.nam4j.impl
it.unipr.ce.dsg.nam4j.impl.context
it.unipr.ce.dsg.nam4j.impl.resource
it.unipr.ce.dsg.nam4j.impl.service
it.unipr.ce.dsg.nam4j.impl.task
```

Folder examples/ contains demo code. Details are given below.


# demonam #
This folder contains the code that implements a NAM. There is a unique class

```
DemoNam extends NetworkedAutonomicMachine
```

The constructor instantiates five functional modules:

```
cfm = new ChordFunctionalModule(this);
this.addFunctionalModule(cfm);
tmfm = new TaskManagerFunctionalModule(this);
this.addFunctionalModule(tmfm);
rfm = new ReasonerFunctionalModule(this);
this.addFunctionalModule(rfm);
sfm = new SensorFunctionalModule(this);
this.addFunctionalModule(sfm);
bfm = new BuildingFunctionalModule(this);
this.addFunctionalModule(bfm);
```

whose details are given in the following sections.
In the `main()` method, `DemoNam` starts the task manager thread (with one task called "AmITask"). If the first argument to the `main()` method is `NOTIFICATION`, the NAM acts as a context publisher. If the first argument is `LOOKUP`, the NAM acts as a context consumer. Special cases are when the first argument is set to `BUILDINGNOTIFICATION` or `BUILDINGLOOKUP`. For these, the application looks, or publishes, data related to sensors placed inside buildings.

If the NAM is a context publisher, the second argument must be a location name, and the third argument must be a numeric value representing a temperature.

If the NAM is a context consumer, the fourth argument must be the path of a text file containing a list of location names. The NAM will periodically search for the most recent temperature notification related to one of those locations (randomly selected).


# chordfm #
This folder contains the code that implements a functional module that provides Chord-based overlay functionalities. It is based on a Chord implementation that uses Sip2Peer as networking middleware.

There are three classes. the main one is

```
ChordFunctionalModule extends FunctionalModule implements ChordEventListener
```

that provides the following services:

```
Lookup lookupService = new Lookup();
Publish publishService = new Publish();
Subscribe subscribeService = new Subscribe();
```

When one these services is called, a thread is detached, executing the code implemented in the following classes:

```
LookupRunnable implements Runnable
PublishRunnable implements Runnable
SubscribeRunnable implements Runnable (TODO)
```

After a successful lookup, the chordfm searches for the Notify service provided by the Lookup caller, to send the response.


# taskmanagerfm #
This folder contains the code that implements a functional module that manages tasks described by the `UPCPFTaskDescriptor`, i.e. tasks that may be in one of the following states:

  * UNSTARTED
  * PROCESSING
  * COMPLETED
  * PAUSED
  * FAILED

The task manager is very simple. Like other functional modules, It does run in a separate thread. For each task (one, in this case) it looks for services that are necessary for the PROCESSING task. If the task is UNSTARTED or PAUSED, and processing services are available, the task manager changes the state of the task to PROCESSING. the check is performed every 10 seconds. If the task is in PROCESSING state but services are not available, the state of the task is set to PAUSED.
The check is repeated every 10 seconds.
Of course, more complex task management strategies could be implemented.

# sensorfm #
This folder contains the code that implements a functional module that simulates a temperature sensor. There are two classes. The main one is

```
SensorFunctionalModule extends FunctionalModule
```

exposing the method `startTemperatureNotification()`, that once called detaches a thread that runs an instance of

```
ProvideTemperatureRunnable implements Runnable
```

Such a class implements the functional logic of the sensorfm. Its `run()` method looks for a `Publish` service within those exposed by the functional modules of the NAM. Then it creates a context event (instance of `TemperatureNotification`), turns it into a JSON message, and publish the latter using the `Publish` service (with period 20 seconds).


# reasonerfm #
This folder contains the code that implements a functional module that acts as a reasoner. There are two classes. The main one is

```
ReasonerFunctionalModule extends FunctionalModule
```

exposing the methods

```
subscribeToTemperatureNotifications()
startTemperatureNotificationLookup()
```

The first method looks for a `Subscribe` service within those exposed by the functional modules of the NAM. If such a service is found, it is used to subscribe to instances of `TemperatureNotification` context events that may be published within the network.

The second method detaches a thread that runs an instance of

```
LookupRunnable implements Runnable
```

Its `run()` method looks for a `Lookup` service within those exposed by the functional modules of the NAM. If such a a service is found, it is used to search for the latest instance of `TemperatureNotification` context event that may have been published within the network (with period 20 seconds).

The reasonerfm provides the following service:

```
Notify notifyService = new Notify();
```

When such a service is called, a thread is detached, executing the code implemented in the following classe:

```
NotifyRunnable implements Runnable (TODO)
```


# ontology #
This folder contains the code that implements the domain ontology of the demo. The following classes have been implemented:

```
Lookup (extends Service)
Notify (extends Service)
Publish (extends Service)
Room (extends Parameter)
Subscribe (extends Service)
Temperature (extends Parameter)
TemperatureNotification (extends ContextEvent)
```

We adopt the following convention: domain-specific classes, like those listed above, are always grouped in an ontology package, which should be linked to all NAMs / functional modules that require those classes. Base classes (`Service`, `Parameter`, `ContextEvent`) are defined in different NAM4J packages - `context` and `service` - for the sake of modularity and coherence.

# migration #
This folder contains the code which implements a NAM which supports the migration of Fuctional Modules and Services. Description and code examples can be found in [this tutorial](https://code.google.com/p/nam4j/wiki/Migration).