package com.akash.threading.waitnotify;

public class MainThread {

	public static int printValueEnd = 9;
	public static int printValueStart = 5;

	public static void main(String args[]) {

		Shared shared = new Shared();
		shared.setValue(printValueStart);
		Producer prod = new Producer(shared);
		Consumer con = new Consumer(shared);
		prod.start();
		con.start();
	}
}