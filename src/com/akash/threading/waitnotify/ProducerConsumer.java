package com.akash.threading.waitnotify;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProducerConsumer {
	public static void main(String args[]) {
		Shared2 s = new Shared2();
		Producer2 p = new Producer2(s);
		Consumer2 c = new Consumer2(s);
		ExecutorService exec = Executors.newFixedThreadPool(5);
		exec.execute(c);
		exec.execute(p);
		exec.shutdown();
	}
}

class Shared2 {
	private volatile int data;
	private volatile boolean moveForward;
	
	public boolean isMoveForward() {
		return moveForward;
	}

	public void setMoveForward(boolean moveForward) {
		this.moveForward = moveForward;
	}

	public void setData(int data) {
		this.data = data;
	}

	public int getData() {
		return data;
	}
}

class Producer2 implements Runnable {
	private Shared2 s;

	public Producer2(Shared2 s) {
		this.s = s;
	}

	public void produce(int data) {
		synchronized (s) {
			s.setData(data);
			System.out.println("putting data in :"
					+ Thread.currentThread().getName()+" -> "+s.getData());
			s.setMoveForward(true);
			try {
				s.wait();
			} catch (InterruptedException e) {
				System.out.println(e);
			}
		}
	}

	public void run() {
		for (int counter = 1; counter <= 5; counter++) {
			produce(counter);
		}
		System.out.println("producer ended");
	}
}

class Consumer2 implements Runnable {
	private Shared2 s;

	public Consumer2(Shared2 s) {
		this.s = s;
	}

	public void consume() {
		while (!s.isMoveForward()) {
		}
		s.setMoveForward(false);
		synchronized (s) {
			System.out.println("Getting data from :"
					+ Thread.currentThread().getName() + " -> " + s.getData());
			s.notify();
		}
	}

	public void run() {
		for (int counter = 1; counter <= 5; counter++) {
			consume();
		}
		System.out.println("consumer ended");
	}
}