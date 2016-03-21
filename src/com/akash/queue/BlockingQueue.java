package com.akash.queue;

public class BlockingQueue<T> implements Queue<T> {

	private static final int DEFAULT_SIZE = 10;
	private Object arr[];
	private int arrSize;

	// Actual queue size
	private int size = 0;

	private int rear;
	private int front;

	BlockingQueue() {
		this(DEFAULT_SIZE);
	}

	BlockingQueue(int initialSize) {

		arrSize = initialSize;
		arr = new Object[initialSize];
		rear = front = 0;
	}

	@Override
	public synchronized void enQueue(Object t) throws InterruptedException {

		if (t == null) {
			return;
		}
		if (rear == front) {
			if (size == arr.length) {
				wait();
				enQueue(t);
			} else if (size == 0) {
				front = 0;
				rear = 0;
			}
		}
		size++;
		arr[rear] = t;
		rear = (rear + 1) % arrSize;
		
		if (size == 1) {
			notify();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public synchronized T deQueue() throws InterruptedException {

		if (size == 0 && rear == front) {
			wait();
			return deQueue();
		}
		T val = (T) arr[front];
		size--;
		front = (front + 1) % arrSize;
		
		if (size == arrSize - 1) {
			notify();
		}
		return val;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public String toString() {

		if (size > 0) {
			StringBuilder sb = new StringBuilder("[");
			int counter = front;
			if (front < rear) {
				while (counter >= front && counter < rear) {
					sb.append((T) arr[counter]).append(",");
					counter++;
				}
			} else {
				int tempCounter = 0;
				while ((counter >= front || counter < rear)
						&& tempCounter < size) {
					tempCounter++;
					sb.append((T) arr[counter]).append(",");
					counter = (counter + 1) % arrSize;
				}
			}
			sb.append("]");
			return sb.toString();
		}
		return "[]";
	}

}