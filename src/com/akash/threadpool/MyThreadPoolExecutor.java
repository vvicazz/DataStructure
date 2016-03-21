package com.akash.threadpool;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class MyThreadPoolExecutor {

	private int coreThreads;
	private BlockingQueue<Runnable> queue;

	public MyThreadPoolExecutor(int coreThreads, BlockingQueue<Runnable> queue) {

		this.coreThreads = coreThreads;
		this.queue = queue;
	}

	public void invokeAll(List<Runnable> runnableList) {

		new ThreadPoolFactory(runnableList, coreThreads, queue).start();
	}
}