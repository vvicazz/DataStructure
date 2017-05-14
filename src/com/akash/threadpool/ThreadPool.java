package com.akash.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

// How to implement our own ThreadPoolExecutor
// https://caffinc.github.io/2016/03/simple-threadpool/

class ThreadPool implements Runnable {

	private BlockingQueue<Runnable> runnables;
	private AtomicBoolean shouldExecute;

	ThreadPool(int size) {
		this.runnables = new ArrayBlockingQueue<>(size);
		shouldExecute = new AtomicBoolean(true);
	}

	public void run() {
		Runnable runnable = null;
		while (shouldExecute.get() || !runnables.isEmpty()) {
			runnable = runnables.poll();
			if (runnable != null) {
				new Thread(new Worker(runnable)).start();
			} else {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	static class Worker implements Runnable {
		private Runnable runnable;

		Worker(Runnable runnable) {
			this.runnable = runnable;
		}

		@Override
		public void run() {
			try {
				runnable.run();
			} finally {

			}
		}
	}

	public void execute(Runnable runnable) {
		if (shouldExecute.get()) {
			runnables.offer(runnable);
		}
	}

	public void terminate() {
		shouldExecute.set(false);
	}
}