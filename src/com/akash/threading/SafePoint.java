package com.akash.threading;

public class SafePoint {

	private int x, y;

	public SafePoint(int x, int y) {
		this.x = x;
		this.y = y;
		get();
		synchronized(this) {
			f2();
			f1();
		}
	}
	
	void f2() {}
	
	static void f1() {}

	public SafePoint(SafePoint safePoint) {
		this(safePoint.x, safePoint.y);
	}

	public synchronized int[] get() {
		return new int[] { x, y };
	}

	public synchronized void set(int x, int y) {
		this.x = x;
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.y = y;
	}

	public static void main(String[] args) throws Exception {
		final SafePoint originalSafePoint = new SafePoint(1, 1);

		// One Thread is trying to change this SafePoint
		new Thread(new Runnable() {
			@Override
			public void run() {
				originalSafePoint.set(2, 2);
				System.out.println("Original : " + originalSafePoint.toString());
			}
		}).start();

		// The other Thread is trying to create a copy. The copy, depending on
		// the JVM, MUST be either (1,1) or (2,2)
		// depending on which Thread starts first, but it can not be (1,2) or
		// (2,1) for example.
		new Thread(new Runnable() {
			@Override
			public void run() {
				SafePoint copySafePoint = new SafePoint(originalSafePoint);
				System.out.println("Copy : " + copySafePoint.toString());
			}
		}).start();
	}

	public String toString() {
		return "X=" + x + " , Y=" + y;
	}
}