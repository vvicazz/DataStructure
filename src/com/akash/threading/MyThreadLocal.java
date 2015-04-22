package com.akash.threading;

public class MyThreadLocal {
	
//	http://stackoverflow.com/questions/1202444/how-is-javas-threadlocal-implemented-under-the-hood
//	http://java.dzone.com/articles/painless-introduction-javas-threadlocal-storage

	public static void main(String args[]) {
		MyThread3 t1 = new MyThread3("t1");
		MyThread3 t2 = new MyThread3("t2");
		MyThread3 t3 = new MyThread3("t3");
		
		t1.start();
		t2.start();
		t3.start();
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println(t1.getThreadLocalValue());		//null  ???
		System.out.println(t2.getThreadLocalValue());
		System.out.println(t3.getThreadLocalValue());
	}
}

class MyThread3 extends Thread {

	private ThreadLocal<String> threadLocal = new ThreadLocal<String>();
	
	public String getThreadLocalValue() {
		return threadLocal.get();
	}

	public MyThread3(String name) {
		super(name);
	}

	public void run() {
		threadLocal.set("Hi !!! "+getName());
		System.out.println(threadLocal.get());
		try {
			System.out.println("waiting "+getName());
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("waiting finished "+getName());
	}
}