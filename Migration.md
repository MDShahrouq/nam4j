# Migration #

Folder examples/migration/src contains the code of a demo which implements a NAM which supports the migration of two Fuctional Modules and one Service. Three classes are included in such a demo.

The first is:
```
Migration extends NetworkedAutonomicMachine
```

This class contains the `main` method, which requires as argument a String specifying the role the NAM assumes, either `SERVER` or `CLIENT`. By extending `NetworkedAutonomicMachine`, the `Migration` class requires, as a first parameter of its constructor, the size of the thread pool which is created to manage incoming migration requests, as a second parameter, the path where files to be sent and received are stored and, as a third parameter, the number of times a NAM client tries to connect to a client server (i.e. if, for instance, it is set to 3, the clients tries two more times if the first one fails).

```
super(10, "examples/migration", 3);
```

The migration server invokes the `activateMigration()` method which creates a socket and waits for incoming connections. The client invokes the `findRemoteFM()` and `findRemoteService()` methods to request remote items (Functional Modules or Services, respectively). Both methods take as parameters the name of the required Functional Module or Service, and the platform. The latter is an enumeration data type whose possible values are `DESKTOP` and `ANDROID`. Platform specification is required since desktop nodes use Jar files, while Android nodes require Dex files.

```
Migration migration = new Migration(args[0]);
if (args[0].equals("SERVER")) {
	migration.activateMigration();
}
else {
	FunctionalModule chordfm = migration.findRemoteFM("ChordFunctionalModule", Platform.DESKTOP);
	FunctionalModule testfm = migration.findRemoteFM("TestFunctionalModule", Platform.DESKTOP);
	Service service = migration.findRemoteService("TestService", Platform.DESKTOP);

	if (service != null) {
		serviceId = "TestService";
		testfm.addProvidedService(serviceId, service);
	}
}
```


---

The second class is:

```
TestFunctionalModule extends FunctionalModule
```

Such a class is a very simple Functional Module which just prints a message stating the id of the NAM it belongs to.


---

The third class is:

```
TestService extends Service
```

This class is simple service that creates a thread, simulates data acquisition from environmental sensors and prints related messages.


---


To run the demo, you should first launch the server and then the client. To do that, create two run configurations in Eclipse using `it.unipr.ce.dsg.examples.migration.Migration` as main class. You also need to specify, as input parameter, `SERVER` for the first configuration and `CLIENT` for the second. The client will receive `Chord` Functional Module and the two items described above. After receiving each item, the client adds it to the classpath, instantiates its main class and adds the Service to the Functional Module. Then the NAM tries to join a Chord network and runs the service.


# Android migration #

To migrate Functional Modules and Services to an Android node, the Jar files must be converted into Dex files. To do this, the standard `ADT` installation provides the `dx` tool. If input.jar is the file to convert, you can use the following instruction:

```
dx --dex --output = output.dex input.jar
```

The file output.dex can be placed in the path the server uses to store files to be migrated. This must take into account the limits of Android application sandbox. An example, on the external storage card, is:

```
String pathToSaveFile = Environment.getExternalStorageDirectory().toString() + "/";
super(10, pathToSaveFile, 3);
```

To manage the migration we suggest the use of an asynchronous task, by invoking the `findRemoteFM()` or `findRemoteService()` methods, by specifying the Android platform as their second parameter.

```
this.findRemoteFM("TestFunctionalModule", Platform.ANDROID);		
BundleDescriptor toClient = this.getDescriptor();
fileName = toClient.getFileName();
completeClassName = toClient.getCompleteName();
testfm = (FunctionalModule)addFileToClassPath(fileName, completeClassName, "FunctionalModule");
```

The function `addFileToClasspath` can use a `DexClassLoader` object to write an optimized version of the received Dex file and supply it to the class loader. The type of the item is required for appropriate type cast; in the example above is passed as third parameter.

```
String dexFile = "/output.dex";
File f = new File(Environment.getExternalStorageDirectory().toString() + dexFile);
final File optimizedDexOutputPath = getDir("outdex", 0);
DexClassLoader classLoader = new DexClassLoader(f.getAbsolutePath(),
optimizedDexOutputPath.getAbsolutePath(),null, getClassLoader());
Class<?> myClass = classLoader.loadClass(completeClassName);
```

It is possible to instantiate the main class of the Functional Module, or the Service, and to invoke its methods using Java **Reflection**. The code below also shows how the item's type information is used.

```
Object obj = null;
Constructor<?> cs;
if(fType.equalsIgnoreCase("FunctionalModule")) {
	cs = classLoader.loadClass(completeClassName).getConstructor(NetworkedAutonomicMachine.class);
	obj = (FunctionalModule)cs.newInstance(this);
}
if(fType.equalsIgnoreCase("Service")) {
	cs = classLoader.loadClass(completeClassName).getConstructor();
	obj = (Service)cs.newInstance();
}

Method m = myClass.getMethod(methodToInvoke);
m.invoke(obj);
```

**Reflection** allows to get many information regarding the class, such as the list of its methods:

```
Method[] methods = myClass.getMethods();
ArrayList<Method> methodsAL = new ArrayList<Method>();
for(int mIndex = 0; mIndex < methods.length; mIndex++)
	methodsAL.add(methods[mIndex]);
```

Further information about Android dynamic class loading and Java Reflection can be found in our [blog](http://dsgwords.blogspot.it/2013/03/android-runtime-class-loading.html).