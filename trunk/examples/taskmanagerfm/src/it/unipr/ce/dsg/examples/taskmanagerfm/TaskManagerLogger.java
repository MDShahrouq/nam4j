package it.unipr.ce.dsg.examples.taskmanagerfm;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;

public class TaskManagerLogger {
	
	private String logFolderPath = null;
	private FileWriter fstream = null;
	private BufferedWriter out = null;

	public TaskManagerLogger(String logFolderPath) {
		this.logFolderPath = logFolderPath;
		try {
			this.fstream = new FileWriter(this.logFolderPath + "TaskManagerLogs.txt");
			this.out = new BufferedWriter(fstream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void log(Object logMessage) {
		Gson gson = new Gson();	
		try{			
			String gsonString = gson.toJson(logMessage);
			this.out.append(gsonString+"\n");
			this.out.flush();
		}catch (Exception e) {
		}
	}
	
	public void closeLogFile() {
		try {
			this.out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}