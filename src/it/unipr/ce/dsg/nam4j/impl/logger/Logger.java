package it.unipr.ce.dsg.nam4j.impl.logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;

public class Logger {

	private String logFolderPath = null;
	private String logFileName = null;
	private FileWriter fstream = null;
	private BufferedWriter out = null;
	
	public Logger(String logFolderPath, String logFileName) {
		this.logFolderPath = logFolderPath;
		this.logFileName = logFileName;
		try {
			this.fstream = new FileWriter(this.logFolderPath + this.logFileName);
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
