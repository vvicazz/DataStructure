package com.akash.threading.waitnotify;

public class Shared {

	private int value;
	private boolean hasNotify;
	
	public boolean isHasNotify() {
		return hasNotify;
	}

	public void setHasNotify(boolean hasNotify) {
		this.hasNotify = hasNotify;
	}

	public synchronized int getValue() {
		return value;
	}

	public synchronized void setValue(int value) {
		this.value = value;
	}
}