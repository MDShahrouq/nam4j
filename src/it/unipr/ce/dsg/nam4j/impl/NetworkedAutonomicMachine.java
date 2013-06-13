/**
 Ê* 
 Ê* This is the main class of nam4j.
 Ê* 
 Ê* @author Michele Amoretti (michele.amoretti@unipr.it)
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
 Ê* 
 Ê*/

package it.unipr.ce.dsg.nam4j.impl;

import it.unipr.ce.dsg.nam4j.impl.resource.ResourceDescriptor;
import it.unipr.ce.dsg.nam4j.impl.service.Service;
import it.unipr.ce.dsg.nam4j.interfaces.INetworkedAutonomicMachine;
import it.unipr.ce.dsg.nam4j.thread.ThreadPool;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.org.apache.bcel.internal.classfile.ClassParser;

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
	 * A String array representing the type of the NAM (A: an Android node ; F:
	 * a desktop node). i-th element is relative to the client served by the
	 * i-th thread in the pool
	 */
	String[] clientPlatform;

	/**
	 * The descriptor of the object to be migrated.
	 */
	BundleDescriptor to;

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
	 * An int representing the size of the threads pool for the migration.
	 */
	int poolSize;

	/**
	 * The threads pool to manage the migration requests.
	 */
	ThreadPool poolForMigration;

	/**
	 * A Class array used for the reflection.
	 */
	private static final Class<?>[] parameters = new Class[] { URL.class };

	/**
	 * Address of the server to which the migration requests should be sent.
	 */
	String serverAddress = "localhost";

	/**
	 * Ports of the server to which the migration requests should be sent. Each
	 * thread of the pool listens on a different port
	 */
	int[] serverPort;

	/**
	 * The path where the migrated received files are stored.
	 */
	String locationToSaveReceivedFile = "lib/";

	/**
	 * The path where the java, Jar and Dex files for migration are stored.
	 */
	String classesJarDexDir = "examples/migration";
	
	/**
	 * Class constructor.
	 * 
	 * @param poolSize
	 *            the size of the thread pool to manage incoming requests
	 */
	public NetworkedAutonomicMachine(int poolSize) {

		setPoolSize(poolSize);

		clientPlatform = new String[getPoolSize()];
		serverPort = new int[getPoolSize()];

		/*
		 * Each thread of the pool managing the migration requests listens to a
		 * different port, so the serverPort array must be fulfilled with the
		 * right port values.
		 * 
		 * TODO: a protocol to manage the port requests by the clients, and a
		 * dictionary of available ports used to fulfill the array, have yet to
		 * be implemented (currently using port 11111 stored in the first
		 * element of the array)
		 * 
		 * for(int i = 0; i < getPoolSize(); i++) { serverPort[i] = < PORT
		 * NUMBER >; }
		 */
		serverPort[0] = 11111;
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
	 *            a String representing the type of the NAM (A: an Android node
	 *            ; F: a desktop node)
	 * @param index
	 *            an int representing the index of the clients platforms array
	 *            where the value has to be stored
	 */
	public void setClientPlatform(String cp, int index) {
		this.clientPlatform[index] = cp;
	}

	/**
	 * Returns the String representing the type of the NAM.
	 * 
	 * @param index
	 *            an int representing the index of the clients platforms array
	 *            where the required value is stored
	 * @return a String representing the type of the NAM (A: an Android node ;
	 *         F: a desktop node)
	 */
	public String getClientPlatform(int index) {
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
	public void setServerPort(int port, int index) {
		this.serverPort[index] = port;
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
	public int getServerPort(int index) {
		return serverPort[index];
	}

	/**
	 * Sets the location where the received migrated file is stored.
	 * 
	 * @param p
	 *            a String identifying the location where the received migrated
	 *            file is stored
	 */
	public void setLocationToSaveReceivedFile(String addr) {
		this.locationToSaveReceivedFile = addr;
	}

	/**
	 * Returns the location where the received migrated file is stored.
	 * 
	 * @return a String identifying the location where the received migrated
	 *         file is stored
	 */
	public String getLocationToSaveReceivedFile() {
		return locationToSaveReceivedFile;
	}

	/**
	 * Set a reference to the descriptor of the migrated file.
	 * 
	 * @param descriptor
	 *            the descriptor of the migrated file
	 */
	public void setDescriptor(BundleDescriptor descriptor) {
		this.to = descriptor;
	}

	/**
	 * Returns a reference to the descriptor of the migrated file.
	 * 
	 * @return a reference to the descriptor of the migrated file
	 */
	public BundleDescriptor getDescriptor() {
		return to;
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
	 * Creates the thread pool to manage the migration requests.
	 * 
	 * @return a reference to the pool
	 */
	public ThreadPool createThreadPoolForMigration() {
		poolForMigration = new ThreadPool(poolSize);
		return poolForMigration;
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
	 * Adds a file to the classpath.
	 * 
	 * @param s
	 *            a String pointing to the file
	 * @throws IOException
	 */
	public void addFile(String s) throws IOException {
		File f = new File(s);
		addFile(f);
	}

	/**
	 * Adds a file to the classpath
	 * 
	 * @param f
	 *            the file to be added
	 * @throws IOException
	 */
	public void addFile(File f) throws IOException {
		addURL(f.toURI().toURL());
	}

	/**
	 * Adds the content pointed by the URL to the classpath.
	 * 
	 * @param u
	 *            the URL pointing to the content to be added
	 * @throws IOException
	 */
	public void addURL(URL u) throws IOException {

		if (getClientPlatform(0).equalsIgnoreCase("F")) {

			// Adding to classpath on a desktop node
			URLClassLoader sysloader = (URLClassLoader) ClassLoader
					.getSystemClassLoader();

			Class<?> sysclass = URLClassLoader.class;

			try {
				Method method = sysclass
						.getDeclaredMethod("addURL", parameters);
				method.setAccessible(true);
				method.invoke(sysloader, new Object[] { u });
			} catch (Throwable t) {
				t.printStackTrace();
				throw new IOException(
						"Error, could not add URL to system classloader");
			}
		} else {
			// Adding to the classpath on an Android node happens locally on the
			// device
		}
	}

	/**
	 * Returns an object of the main class of the file dynamically added to the
	 * path.
	 * 
	 * @param cName
	 *            complete class name of the functional module, or the service,
	 *            added to the class path.
	 * @param cType
	 *            the type of the class to be added - SERVICE or FM (Functional
	 *            Module)
	 * @return an object of the class added to the path
	 */
	public Object getObjectFromFile(String cName, String cType) {

		Object obj = null;

		try {

			if (cType.equalsIgnoreCase("SERVICE")) {
				Class<?> c = ClassLoader.getSystemClassLoader()
						.loadClass(cName);
				obj = c.newInstance();
			}

			if (cType.equalsIgnoreCase("FM")) {
				Constructor<?> cs;
				cs = ClassLoader.getSystemClassLoader().loadClass(cName)
						.getConstructor(NetworkedAutonomicMachine.class);
				obj = cs.newInstance(this);
			}

		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return obj;
	}

	/**
	 * Dynamically adds a file to the path and returns an object of its main
	 * class.
	 * 
	 * @param receivedFilename
	 *            a String representing the name of the file received from the
	 *            server
	 * @param completeClassName
	 *            a String representing the complete name of the file main class
	 * @param fType
	 *            the type of the class to be added - SERVICE or FM (Functional
	 *            Module)
	 * @throws IOException
	 */
	public Object addFileToClassPath(String receivedFilename,
			String completeClassName, String fType) {

		Object obj = null;

		try {
			addFile(receivedFilename);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		if (getClientPlatform(0).equalsIgnoreCase("F")) {
			if (fType.equalsIgnoreCase("SERVICE"))
				obj = getObjectFromFile(completeClassName, "SERVICE");
			if (fType.equalsIgnoreCase("FM"))
				obj = getObjectFromFile(completeClassName, "FM");
		} else
			System.out.println("CLIENT: Android platform");

		return obj;

	}

	/**
	 * Server implementation: it creates the threads of the pool and starts them
	 * to manage incoming requests.
	 * 
	 * FIXME hardcoded class names must be removed
	 * 
	 */
	public void activateMigration() {

		// Creating runnables to handle more requests at the same time

		// int poolSize = getPoolSize();

		// for (int i = 0; i < poolSize; i++) {

		Runnable runner = new Runnable() {

			public void run() {

				Socket cs = null;
				ServerSocket ss = null;

				BufferedReader is = null;
				PrintStream os = null;

				try {
					ss = new ServerSocket(serverPort[0]);
				} catch (IOException e) {
					System.err.println(e.getMessage());
				}
				System.out.println("SERVER: created server socket: " + ss);

				while (true) {

					System.out.println("\nSERVER: waiting for connection...");

					try {
						cs = ss.accept();
					} catch (IOException e) {
						System.err.println("SERVER: connection failed");
					}

					System.out.println("SERVER: connection from " + cs);

					try {
						BufferedInputStream ib = new BufferedInputStream(
								cs.getInputStream());
						is = new BufferedReader(new InputStreamReader(ib));
						BufferedOutputStream ob = new BufferedOutputStream(
								cs.getOutputStream());
						os = new PrintStream(ob, false);

						String line;
						line = new String(is.readLine());

						setClientPlatform(line, 0);

						if (getClientPlatform(0).equalsIgnoreCase("F"))
							System.out
									.println("SERVER: client is a desktop node");
						else
							System.out
									.println("SERVER: client is using Android");

						line = new String(is.readLine());

						System.out
								.println("SERVER: message received from client = \""
										+ line + "\"");

						// Get the list of all files
						String filename = "";
						File folder = new File(classesJarDexDir);
						File[] listOfFiles = folder.listFiles();
						
						boolean found = false; // It is set to true when the file to be migrated is found

						if (line.equalsIgnoreCase("FM")) {

							System.out.println("SERVER: client asked for a FM");

							line = new String(is.readLine());

							System.out.println("SERVER: client requested \""
									+ line + "\"");

							// Data of the file to be migrated
							String className = line;
							String completeName = null;
							String fileToBeMigrated = "";
							File file = null;
							
							for (int i = 0; i < listOfFiles.length; i++) {
 								
								if (listOfFiles[i].isFile() && listOfFiles[i].getName().endsWith(".jar")) {
									filename = listOfFiles[i].getAbsolutePath();
									
									System.out.println("SERVER: checking inside file " + filename);
									
									/* The JarFile class allows to read the content of a Jar
									 * @param: a string representing the path of the jar to open
									 * @param: a boolean stating whether or not to verify if the Jar is signed
									 */
									JarFile jarFile = new JarFile(filename, false);
									
									Enumeration<JarEntry> entries = jarFile.entries();
									
									while (entries.hasMoreElements()) {
										
										JarEntry entry = entries.nextElement();
										String entryName = entry.getName();
								        
										if (entryName.endsWith(".class")) {
								        	
											String[] currentClassName = entryName.split("/");
											String justClassName = currentClassName[currentClassName.length - 1].replace(".class", "");
								        	
											System.out.println("SERVER: comparing " + justClassName + " with " + line);
											
											if(line.equalsIgnoreCase(justClassName)) {
								        		
								        		// listOfFiles[i] is the file to be migrated
												
								        		/* Using BCEL class parser to get the package name for the class
								        		 * A new copy of the class file is created and then parsed to get it
								        		 */
												
								        		File f = new File("temp.class");
								        		
								        		// Copying the content of the class in the new file
								        		InputStream inputS = jarFile.getInputStream(entry);
								        	    java.io.FileOutputStream fos = new FileOutputStream(f);
								        	    while (inputS.available() > 0) {
								        	    	fos.write(inputS.read());
								        	    }
								        	    fos.close();
								        	    inputS.close();
								        	    
								        	    if(f.exists()) {
								        	    
								        	    	found = true;
									        		
									        		fileToBeMigrated = listOfFiles[i].getAbsolutePath();
									        		
									        	    // Parsing the class to get its package name
									        	    ClassParser parser = new ClassParser(f.getAbsolutePath());
													completeName = parser.parse().getPackageName() + "." + className;
													
													// Deleting the copy of the class
													boolean success = f.delete();
												    if (!success)
												      throw new IllegalArgumentException("Delete: deletion failed");
									        		
									        		break;
								        	    }
								        	}
								        }
									}
								}
								
								if(found) break;
							}
							
							if(found) {
								
								if (getClientPlatform(0).equalsIgnoreCase("F"))
									file = new File(fileToBeMigrated);
								else
									file = new File(fileToBeMigrated.replace(".jar", ".dex"));

								System.out.println("SERVER: sending file name = "
										+ file.getName());
								System.out
										.println("SERVER: sending FM main class name = "
												+ className);
								System.out
										.println("SERVER: sending FM main class complete name = "
												+ completeName);
	
								OutputStream outputStream = cs.getOutputStream();
								ObjectOutputStream oos = new ObjectOutputStream(
										outputStream);
								to = new BundleDescriptor(file.getName(),
										className, completeName);
								oos.writeObject(to);
	
								System.out.println("SERVER: sending file...");
	
								byte[] myBytearray = new byte[(int) file.length()];
								FileInputStream fis = new FileInputStream(file);
								BufferedInputStream bis = new BufferedInputStream(
										fis);
								bis.read(myBytearray, 0, myBytearray.length);
								OutputStream outStream = cs.getOutputStream();
								outStream.write(myBytearray, 0, myBytearray.length);
								outStream.flush();
	
								System.out.println("SERVER: done sending");
	
								bis.close();
								oos.close();
							
							}
							else {
								System.out.println("SERVER: requested FM is not available");
								
								// Informing the client that the FM could not be found
								OutputStream outputStream = cs.getOutputStream();
								ObjectOutputStream oos = new ObjectOutputStream(
										outputStream);
								to = new BundleDescriptor("notAvailable",
										"", "");
								oos.writeObject(to);
								oos.close();
							}
						}

						else if (line.equalsIgnoreCase("SERVICE")) {

							System.out.println("SERVER: client asked for a service");

							line = new String(is.readLine());

							System.out.println("SERVER: client requested \""
									+ line + "\"");

							// Data of the file to be migrated
							String className = line;
							String completeName = null;
							String fileToBeMigrated = "";
							File file = null;
							
							for (int i = 0; i < listOfFiles.length; i++) {
 								
								if (listOfFiles[i].isFile() && listOfFiles[i].getName().endsWith(".java")) {
									filename = listOfFiles[i].getAbsolutePath();
									
									System.out.println("SERVER: checking file " + filename);
									
									String[] currentClassName = filename.split("/");
									String justClassName = currentClassName[currentClassName.length - 1].replace(".java", "");
									
									/* Since NAME.java file contain the class NAME, if the name of the current file
									 * is equal to the required service's name, then current file is the correct one
									 */
									if(line.equalsIgnoreCase(justClassName)) {
										fileToBeMigrated = listOfFiles[i].getAbsolutePath();
										
										FileInputStream fis = new FileInputStream(listOfFiles[i]);

										String fileContent = "";
										int oneByte;
										while ((oneByte = fis.read()) != -1) {
											// System.out.write(oneByte);
											// System.out.print((char)oneByte); // could also do this
											char l = (char)oneByte;
											fileContent += l;
										}
										System.out.flush();
										fis.close();
										
										Pattern p = Pattern.compile("package.*;");  
					                    Matcher m = p.matcher(fileContent);  
					                    if(m.find()) {
					                        int occurrenceStart = m.start();
					                        int occurrenceEnd = m.end();
					                        
					                        completeName = fileContent.substring(occurrenceStart, occurrenceEnd).replace("package ", "").replace(";", "") + "." + className;
					                        
					                        found = true;
					                    }
										
										break;
									}
								}
							}
							
							if(found) {
								
								if (getClientPlatform(0).equalsIgnoreCase("F"))
									file = new File(fileToBeMigrated);
								else
									file = new File(fileToBeMigrated.replace(".java", ".dex"));
								
								System.out.println("SERVER: sending file name = "
										+ file.getName());
								System.out
										.println("SERVER: sending service class name = "
												+ className);
								System.out
										.println("SERVER: sending service class complete name = "
												+ completeName);
	
								OutputStream outputStream = cs.getOutputStream();
								ObjectOutputStream oos = new ObjectOutputStream(
										outputStream);
								to = new BundleDescriptor(file.getName(),
										className, completeName);
								oos.writeObject(to);
	
								System.out.println("SERVER: sending file...");
	
								byte[] myBytearray = new byte[(int) file.length()];
								FileInputStream fis = new FileInputStream(file);
								BufferedInputStream bis = new BufferedInputStream(
										fis);
								bis.read(myBytearray, 0, myBytearray.length);
								OutputStream outStream = cs.getOutputStream();
								outStream.write(myBytearray, 0, myBytearray.length);
								outStream.flush();
	
								System.out.println("SERVER: done sending");
	
								bis.close();
								oos.close();
							}
							else {
								System.out.println("SERVER: requested FM is not available");
								
								// Informing the client that the FM could not be found
								OutputStream outputStream = cs.getOutputStream();
								ObjectOutputStream oos = new ObjectOutputStream(
										outputStream);
								to = new BundleDescriptor("notAvailable",
										"", "");
								oos.writeObject(to);
								oos.close();
							}
						}
						os.close();
						is.close();
						cs.close();
					} catch (Exception e) {
						System.out.println("SERVER: error: " + e);
					}
				}
			}
		};
		poolForMigration.run(runner);

		// } // Bracket closing the for which creates the pool threads
	}

	/**
	 * Returns a reference to the required Functional Module.
	 * 
	 * @param fName
	 *            the name of the functional module or of the service to migrate
	 * @param clientType
	 *            (A: an Android node ; F: a desktop node)
	 * @param fType
	 *            a String representing the type of the item to migrate -
	 *            SERVICE or FM (Functional Module)
	 * */
	private Object findRemote(String fName, String clientType, String fType) {

		setClientPlatform(clientType, 0);

		Socket s = null;

		BufferedReader is = null;
		PrintStream os = null;

		Object obj = null;

		int bytesRead;
		int current = 0;
		int filesize = 6022386; // Temporary hardcoded filesize

		try {
			s = new Socket(serverAddress, serverPort[0]);
			is = new BufferedReader(new InputStreamReader(s.getInputStream()));
			os = new PrintStream(new BufferedOutputStream(s.getOutputStream()));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		System.out.println("CLIENT: created socket " + s);

		os.println(clientType);
		os.flush();

		os.println(fType);
		os.flush();

		os.println(fName);
		os.flush();

		System.out.println("CLIENT: waiting for " + fType + " descriptor...");

		String fileName = "";
		String className = "";
		String completeClassName = "";

		try {

			InputStream inputS = s.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(inputS);
			to = (BundleDescriptor) ois.readObject();
			fileName = to.getFileName();
			className = to.getMainClassName();
			completeClassName = to.getCompleteName();

			if(!fileName.equalsIgnoreCase("notAvailable")) { // Checking if the node to which the request was issued could find the resource
			
				System.out.println("CLIENT: file name received from server = "
						+ fileName);
				System.out
						.println("CLIENT: main class name received from server = "
								+ className);
				System.out
						.println("CLIENT: complete class name received from server = "
								+ completeClassName);
				System.out.println("CLIENT: waiting for " + fType + " file "
						+ fileName);
	
				/* Writing the received file */
	
				byte[] mybytearray = new byte[filesize];
				InputStream inputStr = s.getInputStream();
				String receivedFilename = locationToSaveReceivedFile + fileName;
				FileOutputStream fos = new FileOutputStream(receivedFilename);
				BufferedOutputStream bos = new BufferedOutputStream(fos);
				bytesRead = inputStr.read(mybytearray, 0, mybytearray.length);
				current = bytesRead;
	
				bos.write(mybytearray, 0, current);
				bos.flush();
				bos.close();
				fos.close();
	
				/* End of writing */
	
				is.close();
				os.close();
				s.close();
	
				s.close();
	
				File f = new File(receivedFilename);
				if (f.exists()) {
					System.out
							.println("CLIENT: " + f.toURI().toURL() + " received");
	
					obj = addFileToClassPath(receivedFilename, completeClassName,
							fType);
				} else {
					System.out.println("CLIENT: file not received");
				}

			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return obj;
	}

	/**
	 * Returns a reference to the required Functional Module.
	 * 
	 * @param functionalModule
	 *            the name of the FM to transfer
	 * @param clientType
	 *            (A: an Android node ; F: a desktop node)
	 * */
	public FunctionalModule findRemoteFM(String functionalModule,
			String clientType) {
		FunctionalModule fm = (FunctionalModule) findRemote(functionalModule,
				clientType, "FM");
		return fm;
	}

	/**
	 * Returns a reference to the required Service.
	 * 
	 * @param service
	 *            the name of the Service to transfer
	 * @param clientType
	 *            the type of the client (A: an Android node ; F: a desktop
	 *            node)
	 * */
	public Service findRemoteService(String service, String clientType) {
		Service serv = (Service) findRemote(service, clientType, "SERVICE");
		return serv;
	}



}