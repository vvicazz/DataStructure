package com.akash.threadpool;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FixedArrayBlockingQueue<T> {
	private int size;
	private int bufferSize;
	private T queue[];
	private int head;
	private int tail;

	private Lock lock = new ReentrantLock();
	private Condition canPut = lock.newCondition();
	private Condition canGet = lock.newCondition();

	@SuppressWarnings("unchecked")
	FixedArrayBlockingQueue(int size) {
		this.size = size;
		queue = (T[]) new Object[size];
		bufferSize = 0;
		head = 0;
		tail = 0;
	}

	public void put(T t) throws InterruptedException {
		lock.lock();
		try {
			while (bufferSize == size) {
				canPut.await();
			}
			queue[tail++] = t;
			if (tail == size) {
				tail = 0;
			}
			bufferSize++;
			canGet.signal();
		} finally {
			lock.unlock();
		}
	}

	public T get() throws InterruptedException {
		lock.lock();
		try {
			if (bufferSize == 0) {
				canGet.await();
			}
			T t = (T) queue[head];
			queue[head] = null;
			if (++head == size) {
				head = 0;
			}
			bufferSize--;
			canPut.signal();
			return t;
		} finally {
			lock.unlock();
		}
	}
}