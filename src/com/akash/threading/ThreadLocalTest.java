package com.akash.threading;

public class ThreadLocalTest {

	public static void main(String args[]) {
		Holder holder = new Holder(123);
		Runnable r1 = new MyRunnable(holder, 2);
		Runnable r2 = new MyRunnable(holder, 5);
		Thread t1 = new Thread(r1);
		Thread t2 = new Thread(r2);
		t1.start();
		t2.start();
	}
}

class MyRunnable implements Runnable {

	private Holder holder;
	private int factor;

	public MyRunnable(Holder holder, int factor) {
		this.holder = holder;
		this.factor = factor;
	}

	public void run() {
		int value = holder.get();
		System.out.println(Thread.currentThread().getName() + " " + value);
		holder.put(value * factor);
		value = holder.get();
		System.out.println(Thread.currentThread().getName() + " " + value);
	}
}

class Holder {

	private ThreadLocal<Integer> holder = null;

	Holder(final int init) {
		holder = new ThreadLocal<Integer>() {
			public Integer initialValue() {
				return init;
			}
		};
	}

	public int get() {
		return holder.get();
	}

	public void put(Integer val) {
		holder.set(val);
	}
}
