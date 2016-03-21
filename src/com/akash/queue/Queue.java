package com.akash.queue;

public interface Queue<T> {

	/**
	 * insert into queue
	 * 
	 * @param t
	 */
	void enQueue(T t) throws InterruptedException;

	/**
	 * delete from queue
	 * 
	 * @return
	 */
	T deQueue() throws InterruptedException;

	int size();
}