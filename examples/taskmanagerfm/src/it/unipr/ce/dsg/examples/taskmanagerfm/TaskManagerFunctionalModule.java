package it.unipr.ce.dsg.examples.taskmanagerfm;

import java.util.ArrayList;

import it.unipr.ce.dsg.nam4j.impl.FunctionalModule;
import it.unipr.ce.dsg.nam4j.impl.NetworkedAutonomicMachine;
import it.unipr.ce.dsg.nam4j.impl.task.TaskDescriptor;

public class TaskManagerFunctionalModule extends FunctionalModule {

	ArrayList<TaskDescriptor> tasks = null;
	
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
	
	public void startTaskManagement() {
		// TODO detach a thread
		System.out.println("TaskManagerFunctionalModule: " + tasks.get(0).getName());
	}
}
