package com.akash.array;

import java.util.Arrays;

public class MyQueue {

	public static void main(String args[]) {
		MyQueue mq = new MyQueue(3);
		mq.enqueue(1);
		mq.enqueue(2);
		mq.enqueue(3);
		mq.enqueue(4);
		mq.dequeue();
		System.out.println(mq);
		mq.enqueue(5);
		mq.dequeue();
		mq.enqueue(6);
		System.out.println(mq);
	}

	private int queueSize;
	private int arrSize;
	private Integer[] queue;

	private int rear;
	private int front;

	public MyQueue(int arrSize) {
		queue = new Integer[arrSize];
		this.arrSize = arrSize;
		queueSize = front = rear = 0;
	}

	public void enqueue(Integer value) {

		if (rear == front && queueSize == arrSize) {
			System.out.println("queue is overflow");
			return;
		} else {
			queue[rear] = value;
			incrementRear();
		}
	}

	public Integer dequeue() {

		Integer value = null;
		if (rear == front && queueSize == 0) {
			System.out.println("queue is underflow");
		} else {
			value = queue[front];
			queue[front] = null;
			incrementFront();
		}
		return value;
	}

	private void incrementRear() {
		if (rear == arrSize - 1)
			rear = 0;
		else
			rear++;
		queueSize++;
	}

	private void incrementFront() {
		if (front == arrSize - 1)
			front = 0;
		else
			front++;
		queueSize--;
	}

	public String toString() {

		String output = Arrays.toString(queue);
		output = output + " | rear=" + rear + " | front=" + front
				+ " | queueSize=" + queueSize;
		return output;
	}
}