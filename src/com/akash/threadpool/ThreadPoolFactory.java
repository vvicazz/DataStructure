package com.akash.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class ThreadPoolFactory extends Thread {

	private List<Runnable> runnableList;
	private int coreThreads;
	private BlockingQueue<Runnable> queue;
	private List<ThreadWorker> threadWorkerCompleted = new ArrayList<ThreadWorker>();

	public ThreadPoolFactory(List<Runnable> runnableList, int coreThreads,
			BlockingQueue<Runnable> queue) {

		this.coreThreads = coreThreads;
		this.runnableList = runnableList;
		this.queue = queue;
	}

	@Override
	public void run() {

		startThreadsInPool(runnableList);
	}

	// must be executed in parallel to startThreadsInPool()
	private void updateThreadStatus() {

		// update threadWorkerCompleted for all tw.isAlive()==false
		
		//get from queue and create new ThreadWorker
	}

	private void startThreadsInPool(List<Runnable> runnableList) {

		int numOfThreadsStarted = 0;
		while (numOfThreadsStarted < coreThreads
				&& runnableList.size() > numOfThreadsStarted) {
			Runnable runnable = runnableList.get(numOfThreadsStarted);
			if (runnable != null) {
				numOfThreadsStarted++;
				ThreadWorker tw = new ThreadWorker(runnable);
				tw.start();
			}
		}

		int numOfThreadsMonitored = numOfThreadsStarted;
		while (runnableList.size() > numOfThreadsMonitored) {

			numOfThreadsMonitored++;
			Runnable runnable = runnableList.get(numOfThreadsMonitored);
			if (runnable != null) {
				try {
					queue.put(runnable);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		// execute all threads in queue
	}

	private static class ThreadWorker extends Thread {

		private Runnable runnable;

		public ThreadWorker(Runnable runnable) {
			this.runnable = runnable;
		}

		@Override
		public void run() {

			runnable.run();
		}
	}
}