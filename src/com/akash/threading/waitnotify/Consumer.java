package com.akash.threading.waitnotify;

public class Consumer extends Thread {

	private Shared shared;

	public Shared getShared() {
		return shared;
	}

	public void setShared(Shared shared) {
		this.shared = shared;
	}

	public Consumer(Shared shared) {
		this.shared = shared;
	}

	public void run() {

		Shared shared = getShared();
		while (shared.getValue() <= MainThread.printValueEnd - 1) {
			synchronized (shared) {

				// wait if value is consumed and not produced
				// also check notify was missed using boolean
				if (!shared.isHasNotify()) {
					try {
						shared.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println("shared consumed :" + shared.getValue());

				// notify another thread
				shared.setHasNotify(false);
				shared.notify();
			}
		}
	}
}