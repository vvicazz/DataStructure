package com.akash.threading.waitnotify;

public class Producer extends Thread {

	private Shared shared;

	public Shared getShared() {
		return shared;
	}

	public void setShared(Shared shared) {
		this.shared = shared;
	}

	public Producer(Shared shared) {
		this.shared = shared;
	}

	public void run() {

		Shared shared = getShared();
		int initialValue = shared.getValue();
		while (initialValue <= MainThread.printValueEnd) {
			synchronized (shared) {

				// wait if value is produced but not consumed
				if (initialValue != shared.getValue()) {
					try {
						shared.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				shared.setValue(initialValue);
				System.out.println("shared produced :" + initialValue);
				initialValue++;

				// notify another thread and also using boolean variable for
				// missed notify
				shared.notify();
				shared.setHasNotify(true);
			}
		}
	}
}