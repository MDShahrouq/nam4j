package it.unipr.ce.dsg.examples.taskmanagerfm;

import java.util.ArrayList;

import com.google.gson.Gson;

import it.unipr.ce.dsg.nam4j.impl.FunctionalModule;
import it.unipr.ce.dsg.nam4j.impl.NetworkedAutonomicMachine;
import it.unipr.ce.dsg.nam4j.impl.task.TaskDescriptor;

public class TaskManagerFunctionalModule extends FunctionalModule {

	private ArrayList<TaskDescriptor> tasks = null;
	
	public ArrayList<TaskDescriptor> getTasks() {
		return tasks;
	}

	public TaskManagerFunctionalModule(NetworkedAutonomicMachine nam) {
		super(nam);
		this.setId("tmfm");
		this.setName("TaskManagerFunctionalModule");
		System.out.println("I am " + this.getId() + " and I own to " + nam.getId());
		tasks = new ArrayList<TaskDescriptor>();
	}

	public void addTaskDescriptor(TaskDescriptor td) {
		if (!tasks.contains(td))
			tasks.add(td);
	}
	
	public void removeTaskDescriptor(TaskDescriptor td) {
		if (tasks.contains(td))
			tasks.remove(td);
	}
	
	public String convertTaskDescriptorToJSON(TaskDescriptor td) {
		Gson gson = new Gson();
		String json = gson.toJson(td);
		System.out.println("Task descriptor in JSON format = " + json);
		return json;
	}
	
	public void startTaskManagement() {
		Thread t = new Thread(new ManageTasksRunnable(this), "Perform task management");
		t.start();
	}
}
