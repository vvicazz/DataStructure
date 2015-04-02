package com.akash.threading;

public class Thread1 {

	public static Thread mainThread = null;

	public static void main(String args[]) {

		System.out.println("main thread started..");
		MyThread myThread = new MyThread();
		Thread thread = new Thread(myThread);
		mainThread = Thread.currentThread();
		thread.start();
		try {
			Thread.sleep(400);
			thread.interrupt();
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.out.println("main thread interrupted :" + e);
			e.printStackTrace();
		}
	}
}

class MyThread implements Runnable {

	public void run() {
		System.out.println("separate thread started...");
		try {
			Thread.sleep(2000);
			System.out.println("thread ended successfully");
		} catch (InterruptedException e) {
			System.out.println("separate thread InterruptedException occured :"
					+ e);
			Thread mainThread = Thread1.mainThread;
			if (mainThread != null) {
				mainThread.interrupt();
			}
			e.printStackTrace();
		}
	}
}
