
/**
Ê* 
Ê* This is the main class of nam4j.
Ê* 
Ê* @author Michele Amoretti (michele.amoretti@unipr.it)
 * @author Marco Muro
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
import it.unipr.ce.dsg.nam4j.interfaces.INetworkedAutonomicMachine;
import it.unipr.ce.dsg.nam4j.thread.ThreadPool;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
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
import java.util.HashMap;

public abstract class NetworkedAutonomicMachine implements INetworkedAutonomicMachine {

	String id = "networkedAutonomicMachine";
	String name = "Networked Autonomic Machine";
	
	HashMap<String,FunctionalModule> functionalModules = new HashMap<String,FunctionalModule>();
	HashMap<String,ResourceDescriptor> resourceDescriptors = new HashMap<String,ResourceDescriptor>();

	int poolSize = 10;
	ThreadPool poolForMigration;
	private static final Class<?>[] parameters = new Class[]{URL.class};
	String serverAddress = "localhost";
	int serverPort = 11111;
	
	public void setServerAddress(String addr) {
		this.serverAddress = addr;
	}

	public String getServerAddress() {
		return serverAddress;
	}
	
	public void setServerPort(int p) {
		this.serverPort = p;
	}
	
	public int getServerPort(){
		return serverPort;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public void setPoolSize(int size) {
		this.poolSize = size;
	}
	
	public int getPoolSize(){
		return poolSize;
	}
	
	public void addFunctionalModule(FunctionalModule functionalModule) {
		functionalModules.put(functionalModule.getId(),functionalModule);
	}

	public void removeFunctionalModule(String id) {
		functionalModules.remove(id);
	}

	public HashMap<String,FunctionalModule> getFunctionalModules() {
		return functionalModules;
	}

	public FunctionalModule getFunctionalModule(String id) {
		return functionalModules.get(id);
	}

	public HashMap<String,ResourceDescriptor> getResources() {
		return resourceDescriptors;
	}

	public ResourceDescriptor getResource(String id) {
		return resourceDescriptors.get(id);
	}
	
	public void removeResource(String id) {
		resourceDescriptors.remove(id);
	}
	
	public ThreadPool createThreadPoolForMigration() {
		poolForMigration = new ThreadPool(poolSize);
		return poolForMigration;
	}
	
	/**
     * Adds a file to the classpath.
     * @param s a String pointing to the file
     * @throws IOException
     */
	public static void addFile(String s) throws IOException {
        File f = new File(s);
        addFile(f);
    }

	/**
     * Adds a file to the classpath
     * @param f the file to be added
     * @throws IOException
     */
    public static void addFile(File f) throws IOException {
        addURL(f.toURI().toURL());
    }
    
    /**
     * Adds the content pointed by the URL to the classpath.
     * @param u the URL pointing to the content to be added
     * @throws IOException
     */
    public static void addURL(URL u) throws IOException {
    	URLClassLoader sysloader = (URLClassLoader)ClassLoader.getSystemClassLoader();
        Class<?> sysclass = URLClassLoader.class;
        try {
            Method method = sysclass.getDeclaredMethod("addURL",parameters);
            method.setAccessible(true);
            method.invoke(sysloader,new Object[]{ u }); 
        } catch (Throwable t) {
            t.printStackTrace();
            throw new IOException("Error, could not add URL to system classloader");
        }
    }
    
    public FunctionalModule getFunctionalModuleFromJar(String fmName) {
		
		FunctionalModule fm = null;
		Constructor<?> cs;
		try {
			cs = ClassLoader.getSystemClassLoader().loadClass(fmName).getConstructor(NetworkedAutonomicMachine.class);
			fm = (FunctionalModule)cs.newInstance(this);
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
		
		return fm;
	}
	
	public FunctionalModule addJarToClassPath(String receivedFilename, String className) {
    	
		try {
			addFile(receivedFilename);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
    	FunctionalModule cfm = getFunctionalModuleFromJar("it.unipr.ce.dsg.examples.chordfm." + className);
		
		return cfm;
		
	}
	
	public void activateMigration() {
		
		//int poolSize = getPoolSize();
		
		// Creating runnables to handle more requests at the same time
		
		//for (int i = 0; i < poolSize; i++) {

			Runnable runner = new Runnable() {
				
				public void run() {
					
					Socket cs = null;
					ServerSocket ss = null;
					
					BufferedReader is = null;
					PrintStream os = null;
					
					try {
						ss = new ServerSocket(serverPort);	
					}
					catch (IOException e) {
						System.err.println(e.getMessage());
					}
					System.out.println("SERVER: created server socket: " + ss);
					
					while(true) {
							
						System.out.println("\nSERVER: waiting for connection...");
						
						try {
							cs = ss.accept();
						}
						catch (IOException e) {
							System.err.println("SERVER: connection failed");
						}
						
						System.out.println("SERVER: connection from " + cs);
						
						try {
							BufferedInputStream ib = new BufferedInputStream(cs.getInputStream());
							is = new BufferedReader(new InputStreamReader(ib));
						    BufferedOutputStream ob = new BufferedOutputStream(cs.getOutputStream());
						    os = new PrintStream(ob,false);
			
						    String line;
						    line = new String(is.readLine());
						    
						    String className = "ChordFunctionalModule";
			
						    System.out.println("SERVER: message received from client = \"" + line + "\"");
						    
						    File file = new File ("lib/chordfm.jar");
						    
						    System.out.println("SERVER: sending file name = " + file.getName());
						    System.out.println("SERVER: sending class name = " + className);
						    
						    OutputStream outputStream = cs.getOutputStream();  
						    ObjectOutputStream oos = new ObjectOutputStream(outputStream);  
						    FMDescriptor to = new FMDescriptor(file.getName(), className);  
						    oos.writeObject(to);
						    
						    System.out.println("SERVER: sending file...");
						    
						    byte [] mybytearray  = new byte [(int)file.length()];
						    OutputStream outputStr = cs.getOutputStream();
						    outputStr.write(mybytearray,0,mybytearray.length);
						    outputStr.flush();
						    
						    System.out.println("SERVER: done sending");
						    
						    os.close();
						    oos.close();
						    is.close();
						    cs.close();
			
						}
						catch (Exception e) {
							System.out.println("SERVER: error: " + e);
						}
					}
				}
			};
			poolForMigration.run(runner);
		//}
	}
	
	public FunctionalModule findRemoteFM() {
		
		Socket s = null;
		
		BufferedReader is = null;
		PrintStream os = null;
		
		FunctionalModule fm = null;
		
		int bytesRead;
	    int current = 0;
	    int filesize = 6022386; // Temporary hardcoded filesize
		
		try {
			s = new Socket(serverAddress, serverPort);
			is = new BufferedReader(new InputStreamReader(s.getInputStream()));
			os = new PrintStream(new BufferedOutputStream(s.getOutputStream()));
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println("CLIENT: created socket " + s);
		
		os.println("Requesting FM");
		os.flush();
		
		System.out.println("CLIENT: waiting for FM descriptor...");
		
		String fileName = "";
		String className = "";
		
		try {
			
			InputStream inputS = s.getInputStream();  
			ObjectInputStream ois = new ObjectInputStream(inputS);  
			FMDescriptor to = (FMDescriptor)ois.readObject();
			fileName = to.getFileName();
			className = to.getMainClassName();
			
			System.out.println("CLIENT: file name received from server = " + fileName);
			System.out.println("CLIENT: main class name received from server = " + className);
			System.out.println("CLIENT: waiting for FM jar " + fileName);
			
			/* Writing the received file */
			
			byte [] mybytearray  = new byte [filesize];
		    InputStream inputStr = s.getInputStream();
		    String receivedFilename = "lib/copy" + fileName;
		    FileOutputStream fos = new FileOutputStream(receivedFilename);
		    BufferedOutputStream bos = new BufferedOutputStream(fos);
		    bytesRead = inputStr.read(mybytearray,0,mybytearray.length);
		    current = bytesRead;

		    bos.write(mybytearray, 0 , current);
		    bos.flush();
		    bos.close();
		    fos.close();

		    /* End of writing */
		    
		    is.close();
			os.close();
			s.close();
			
			s.close();
  
		    File f = new File(receivedFilename);
		    if(f.exists()) {
		    	System.out.println("CLIENT: "+ f.toURI().toURL() + " received");
		    	fm = addJarToClassPath(receivedFilename, className);
		    }
		    else {
		    	System.out.println("CLIENT: file not received");
		    }
			
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return fm;
	}
}
