package it.unipr.ce.dsg.nam4j.impl;

import it.unipr.ce.dsg.nam4j.impl.mobility.ClientCopyActionManager;
import it.unipr.ce.dsg.nam4j.impl.mobility.ServerMobilityActionManager;
import it.unipr.ce.dsg.nam4j.impl.resource.ResourceDescriptor;
import it.unipr.ce.dsg.nam4j.interfaces.INetworkedAutonomicMachine;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * This is the main class of nam4j.
 * 
 * @author Michele Amoretti (michele.amoretti@unipr.it)
 * @author Marco Muro
 * @author Alessandro Grazioli (grazioli@ce.unipr.it)
 * 
 * This file is part of nam4j.
 *
 * nam4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * nam4j is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with nam4j. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
public abstract class NetworkedAutonomicMachine implements
		INetworkedAutonomicMachine {

	/**
	 * A String identifying the NAM.
	 */
	String id = "networkedAutonomicMachine";

	/**
	 * A String representing the name of the NAM.
	 */
	String name = "Networked Autonomic Machine";

	/**
	 * The client platform.
	 */
	public enum Platform {
		DESKTOP, ANDROID
	};

	/**
	 * The mobility action to be performed.
	 */
	public enum Action {
		BACK, COPY, GO, MIGRATE, OFFLOAD
	}

	/**
	 * The type of the mobility action subject.
	 */
	public enum MigrationSubject {
		FM, SERVICE
	}

	/**
	 * An array representing the type of the NAM (ANDROID or DESKTOP). i-th
	 * element is relative to the client served by the i-th thread in the pool
	 */
	Platform[] clientPlatform;

	/**
	 * A HashMap for the functional modules added to the NAM The keys are String
	 * identifying the functional modules. The values are FunctionalModule
	 * objects.
	 */
	HashMap<String, FunctionalModule> functionalModules = new HashMap<String, FunctionalModule>();

	/**
	 * A HashMap for the Resources of the NAM node. The keys are String
	 * identifying the Resources. The values are Resources objects.
	 */
	HashMap<String, ResourceDescriptor> resourceDescriptors = new HashMap<String, ResourceDescriptor>();

	/**
	 * A HashMap to store the address of NAMs which sent Functional Modules.
	 */
	HashMap<String, String> fmSender = new HashMap<String, String>();

	/**
	 * A HashMap to store the address of NAMs which sent Services.
	 */
	HashMap<String, String> serviceSender = new HashMap<String, String>();

	/**
	 * An int representing the size of the threads pool for the migration.
	 */
	int poolSize;

	/**
	 * The threads pool to manage the migration requests.
	 */
	ExecutorService poolForServerMobilityAction;

	/**
	 * The threads pool to manage the migration requests.
	 */
	ExecutorService poolForClientMobilityAction;

	/**
	 * The number of times a client tries to connect to the server
	 */
	private int trialsNumber = 3;

	/**
	 * Address of the server to which the migration requests should be sent.
	 */
	String serverAddress = "localhost";

	/**
	 * Ports of the server to which the migration requests should be sent. Each
	 * thread of the pool listens on a different port
	 */
	int serverPort = 11111;

	/**
	 * The path where the java, Jar and Dex files for migration are stored (both
	 * received and sent).
	 */
	String migrationStore;

	/**
	 * Class constructor.
	 * 
	 * @param poolSize
	 *            the size of the thread pool to manage incoming requests
	 * 
	 * @param migrationStorePath
	 *            the path to store files received via migration
	 * 
	 * @param trials
	 *            the number of times a client tries to connect to a server
	 */
	public NetworkedAutonomicMachine(int poolSize, String migrationStorePath,
			int trials) {

		setPoolSize(poolSize);
		setMigrationStore(migrationStorePath);
		setTrialsNumber(trials);

		// Creation of the thread pools to manage incoming mobility action requests.
		createPoolForServerMobilityAction();
		createPoolForClientMobilityAction();

		clientPlatform = new Platform[getPoolSize()];
	}

	/**
	 * Sets the address of the server to which the migration requests should be
	 * sent.
	 * 
	 * @param addr
	 *            a String identifying the address of the server for migration
	 */
	public void setServerAddress(String addr) {
		this.serverAddress = addr;
	}

	/**
	 * Returns the address of the server to which the migration requests should
	 * be sent.
	 * 
	 * @return a String identifying the address of the server to which the
	 *         migration requests should be sent
	 */
	public String getServerAddress() {
		return serverAddress;
	}

	/**
	 * Sets the String representing the type of the NAM.
	 * 
	 * @param cp
	 *            a representation of the NAM type (ANDROID or DESKTOP)
	 * @param index
	 *            an int representing the index of the clients platforms array
	 *            where the value has to be stored
	 */
	public void setClientPlatform(Platform cp, int index) {
		this.clientPlatform[index] = cp;
	}

	/**
	 * Returns the String representing the type of the NAM.
	 * 
	 * @param index
	 *            an int representing the index of the clients platforms array
	 *            where the required value is stored
	 * @return a representation of the NAM type (ANDROID or DESKTOP)
	 */
	public Platform getClientPlatform(int index) {
		return clientPlatform[index];
	}

	/**
	 * Sets the port of the server to which the migration requests should be
	 * sent.
	 * 
	 * @param port
	 *            an int identifying the port of the server for migration
	 * @param index
	 *            an int representing the index of the port array where the
	 *            value has to be stored
	 */
	public void setServerPort(int port) {
		this.serverPort = port;
	}

	/**
	 * Returns the port of the server to which the migration requests should be
	 * sent.
	 * 
	 * @param index
	 *            an int representing the index of the port array where the
	 *            required value is stored
	 * @return the port of the server to which the migration requests should be
	 *         sent
	 */
	public int getServerPort() {
		return serverPort;
	}

	/**
	 * Sets the identifier of the NAM.
	 * 
	 * @param id
	 *            a String identifying the NAM
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Returns the identifier of the NAM.
	 * 
	 * @return a String identifying the NAM
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the name of the NAM.
	 * 
	 * @param name
	 *            a String identifying the name of the NAM
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the name of the NAM.
	 * 
	 * @return a String identifying the name of the NAM
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the size of the thread pool to manage migration requests.
	 * 
	 * @param size
	 *            the size of the thread pool to manage migration requests
	 */
	public void setPoolSize(int size) {
		this.poolSize = size;
	}

	/**
	 * Gets the size of the thread pool to manage migration requests.
	 * 
	 * @return the size of the thread pool to manage migration requests
	 */
	public int getPoolSize() {
		return poolSize;
	}

	/**
	 * Sets the path where the files to be migrated are stored.
	 * 
	 * @param ms
	 *            the path where the files to be migrated are stored
	 */
	public void setMigrationStore(String ms) {
		this.migrationStore = ms;
	}

	/**
	 * Gets the the path where the files to be migrated are stored.
	 * 
	 * @return the path where the files to be migrated are stored
	 */
	public String getMigrationStore() {
		return migrationStore;
	}

	/**
	 * Set the number of times a client tries to connect to a server.
	 * 
	 * @param num
	 *            the number of times a client tries to connect to a server.
	 */
	public void setTrialsNumber(int num) {
		trialsNumber = num;
	}

	/**
	 * Get the number of times a client tries to connect to a server.
	 * 
	 * @return the number of times a client tries to connect to a server.
	 */
	public int getTrialsNumber() {
		return trialsNumber;
	}

	/**
	 * Adds a Functional Module to the NAM.
	 * 
	 * @param functionalModule
	 *            a reference to the Functional Module to be added to the NAM
	 */
	public void addFunctionalModule(FunctionalModule functionalModule) {
		functionalModules.put(functionalModule.getId(), functionalModule);
	}

	/**
	 * Remove a Functional Module from the NAM.
	 * 
	 * @param id
	 *            a String identifying the Functional Module to be removed from
	 *            the NAM
	 */
	public void removeFunctionalModule(String id) {
		functionalModules.remove(id);
	}

	/**
	 * Gets the Functional Modules added to the NAM.
	 * 
	 * @return a reference to the Hash Table containing the Functional Modules
	 *         added to the NAM
	 */
	public HashMap<String, FunctionalModule> getFunctionalModules() {
		return functionalModules;
	}

	/**
	 * Gets a Functional Module added to the NAM.
	 * 
	 * @param id
	 *            a String identifying the required Functional Module
	 * @return a reference to the required Functional Module
	 */
	public FunctionalModule getFunctionalModule(String id) {
		return functionalModules.get(id);
	}

	/**
	 * Removes a Resource from the NAM node.
	 * 
	 * @param id
	 *            a String identifying the Resource to be removed
	 */
	public void removeResource(String id) {
		resourceDescriptors.remove(id);
	}

	/**
	 * Add the address of a copied FM sender to a HashMap.
	 * 
	 * @param fm
	 *            The file name and extension of the sent FM
	 * @param sender
	 *            The address of the sender
	 */
	public void addFmSender(String fm, String sender) {
		fmSender.put(fm, sender);
	}

	/**
	 * Remove the address of a copied FM sender from the HashMap.
	 * 
	 * @param fm
	 *            The file name and extension of the FM to be removed
	 */
	public void removeFmSender(String fm) {
		fmSender.remove(fm);
	}

	/**
	 * Add the address of a Service sender to a HashMap.
	 * 
	 * @param service
	 *            The file name and extension of the sent Service
	 * @param sender
	 *            The address of the sender
	 */
	public void addServiceSender(String service, String sender) {
		serviceSender.put(service, sender);
	}

	/**
	 * Remove the address of a FM sender from the HashMap.
	 * 
	 * @param fm
	 *            The file name and extension of the Service to be removed
	 */
	public void removeServiceSender(String service) {
		serviceSender.remove(service);
	}

	/**
	 * Creates the thread pool to manage the migration requests.
	 * newFixedThreadPool(int nThreads) method creates a thread pool that reuses
	 * a fixed number of threads operating off a shared unbounded queue. At any
	 * point, at most nThreads threads will be active processing tasks. If
	 * additional tasks are submitted when all threads are active, they will
	 * wait in the queue until a thread is available.
	 * 
	 * @return a reference to the pool
	 */
	private ExecutorService createPoolForServerMobilityAction() {
		poolForServerMobilityAction = Executors.newFixedThreadPool(poolSize);
		return poolForServerMobilityAction;
	}

	/**
	 * Creates the thread pool to manage the migration requests.
	 * newFixedThreadPool(int nThreads) method creates a thread pool that reuses
	 * a fixed number of threads operating off a shared unbounded queue. At any
	 * point, at most nThreads threads will be active processing tasks. If
	 * additional tasks are submitted when all threads are active, they will
	 * wait in the queue until a thread is available.
	 * 
	 * @return a reference to the pool
	 */
	private ExecutorService createPoolForClientMobilityAction() {
		poolForClientMobilityAction = Executors.newFixedThreadPool(poolSize);
		return poolForClientMobilityAction;
	}

	/**
	 * Gets the Resources List of the NAM node.
	 * 
	 * @return a HashMap storing the Resources of the NAM node.
	 */
	public HashMap<String, ResourceDescriptor> getResources() {
		return resourceDescriptors;
	}

	/**
	 * Get a Resource of the NAM node.
	 * 
	 * @param id
	 *            a String identifying the required Resource
	 * @return the required Resource of the NAM node.
	 */
	public ResourceDescriptor getResource(String id) {
		return resourceDescriptors.get(id);
	}

	/**
	 * Server implementation: it waits for incoming connections and dispatches
	 * them to the threadpool.
	 */
	public void startMobilityAction() {

		try {

			Socket cs = null;
			ServerSocket ss = null;

			ss = new ServerSocket(serverPort);

			System.out.println("SERVER: thread "
					+ Thread.currentThread().getId()
					+ " has created server socket " + ss);

			System.out.println("SERVER: thread "
					+ Thread.currentThread().getId()
					+ " is waiting for connection...");

			while (true) {

				cs = ss.accept();

				poolForServerMobilityAction
						.execute(new ServerMobilityActionManager(cs, this));

				System.out
						.println("SERVER: thread "
								+ Thread.currentThread().getId()
								+ " accepted connection; now is waiting for another one...");

			}
		} catch (IOException e1) {
			System.err.println("SERVER: connection failed for thread "
					+ Thread.currentThread().getId());
		}

	}

	/**
	 * Start a COPY mobility action.
	 * 
	 * @param functionalModule
	 *            the name of the FM to copy
	 * @param service
	 *            the name of the Service of functionalModule to be copied (can
	 *            be null if you need to copy just a FM)
	 * @param serviceId
	 *            the id of the Service of functionalModule to be copied (can be
	 *            null if you need to copy just a FM)
	 * @param clientType
	 *            (ANDROID or DESKTOP)
	 */
	public void startCopyAction(String functionalModule, String[] service,
			String[] serviceId, Platform clientType) {

		ClientCopyActionManager clientCopyActionManager = new ClientCopyActionManager(this,
				functionalModule, service, serviceId, clientType, Action.COPY);
		poolForClientMobilityAction.execute(clientCopyActionManager);
	}

}