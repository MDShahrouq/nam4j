package it.unipr.ce.dsg.nam4j.thread;

public class ThreadTask extends Thread {
	
	private ThreadPool pool;

	/**
	 * Class constructor.
	 * 
	 * @param thePool a reference to a thread pool
	 */
	public ThreadTask(ThreadPool thePool) {
		pool = thePool;
	}

	/**
	 * Runs the threads of the pool.
	 */
	public void run() {
		while (true) {
			// Blocks until job
			Runnable job = pool.getNext();
			try {
				job.run();
			} catch (Exception e) {
				// Ignore exceptions thrown from jobs
				System.err.println("Job exception: " + e);
			}
		}
	}
	
}
