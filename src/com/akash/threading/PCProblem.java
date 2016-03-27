package com.akash.threading;

import java.util.concurrent.atomic.AtomicInteger;

public class PCProblem {

	public static void main(String args[]) {

		Counter count = new Counter();
		new Thread(new EvenPrinter(count), "Even").start();
		new Thread(new OddPrinter(count), "Odd").start();
	}
}

class EvenPrinter implements Runnable {

	private Counter counter;

	EvenPrinter(Counter counter) {
		this.counter = counter;
	}

	public void run() {

		synchronized (counter) {
			while(counter.getCount()<=20) {
				if (counter.getCount() % 2 != 0) {
					try {
						counter.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					System.out.println(Thread.currentThread().getName() + " -> "
							+ counter.getCount());
					counter.incCount();
					counter.notify();
				}
			}
		}
	}
}

class OddPrinter implements Runnable {

	private Counter counter;

	OddPrinter(Counter counter) {
		this.counter = counter;
	}

	public void run() {

		synchronized (counter) {
			while(counter.getCount()<=20) {
				if (counter.getCount() % 2 == 0) {
					try {
						counter.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					System.out.println(Thread.currentThread().getName() + " -> "
							+ counter.getCount());
					counter.incCount();
					counter.notify();
				}
			}
		}
	}
}

class Counter {

	private AtomicInteger count;

	Counter() {
		count = new AtomicInteger(1);
	}

	public int getCount() {
		return count.get();
	}

	public void incCount() {
		count.getAndIncrement();
	}
}