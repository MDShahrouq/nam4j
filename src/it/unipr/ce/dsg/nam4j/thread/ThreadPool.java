package it.unipr.ce.dsg.nam4j.thread;

import java.util.LinkedList;

public class ThreadPool {

	private LinkedList<Runnable> tasks = new LinkedList<Runnable>();
	
	/**
	 * Class constructor.
	 * 
	 * @param size an int representing the number of threads by which the pool is composed
	 */
	public ThreadPool(int size) {
		for (int i = 0; i < size; i++) {
			Thread thread = new ThreadTask(this);
			thread.start();
		}
	}

	/**
	 * Appends a thread to the pool and runs it.
	 * 
	 * @param task a Runnable interface representing the thread to be added to the pool
	 */
	public void run(Runnable task) {
		synchronized (tasks) {
			tasks.addLast(task);
			tasks.notify();
		}
	}

	/**
	 * Removes the first thread in the pool and returns a Runnable interface representing it.
	 * 
	 * @return a Runnable interface representing the first thread in the pool
	 */
	public Runnable getNext() {
		Runnable returnVal = null;
		synchronized (tasks) {
			while (tasks.isEmpty()) {
				try {
					tasks.wait();
				} catch (InterruptedException ex) {
					System.err.println("Thread interrupted");
				}
			}
			returnVal = (Runnable) tasks.removeFirst();
		}
		return returnVal;
	}
	
}
