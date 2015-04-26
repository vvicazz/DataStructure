package com.akash.threading;

public class MyThreadLocal {

	// http://stackoverflow.com/questions/1202444/how-is-javas-threadlocal-implemented-under-the-hood
	// http://java.dzone.com/articles/java-thread-local-%E2%80%93-how-use
	// http://java.dzone.com/articles/painless-introduction-javas-threadlocal-storage

	public static void main(String args[]) {
		Shared shared = new Shared();
		MyThread3 t1 = new MyThread3("t1", shared);
		MyThread3 t2 = new MyThread3("t2", shared);
		MyThread3 t3 = new MyThread3("t3", shared);

		t1.start();
		t2.start();
		t3.start();

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println(t1.getThreadLocalValue()); // null ???
		System.out.println(t2.getThreadLocalValue()); // Values of threadLocal
														// are accessed
		System.out.println(t3.getThreadLocalValue()); // Main thread trying to
														// access child thread
														// threadlocal values
	}
}

class MyThread3 extends Thread {

	private static ThreadLocal<String> threadLocal = new ThreadLocal<String>();
	private Shared shared;

	public String getThreadLocalValue() {
		return threadLocal.get(); // this evaluates the threadlocal value for
									// Thread.CurrentThread
	}

	public MyThread3(String name, Shared shared) {
		super(name);
		this.shared = shared;
	}

	public void run() {
		threadLocal.set("Hi !!! " + getName());
		shared.printValue(this);
		System.out.println(threadLocal.get());
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class Shared {

	public void printValue(MyThread3 thread) {
		System.out.println("current thread:" + Thread.currentThread().getName()
				+ " ,threadlocal:" + thread.getThreadLocalValue());
	}
}