package com.akash.queue;

public class QueueImpl {

	public static void main(String args[]) throws InterruptedException {
		
		Queue<Integer> queue = new BlockingQueue<Integer>(3);
		queue.enQueue(1);
		queue.enQueue(2);
		queue.enQueue(3);
		System.out.println(queue.deQueue());
		queue.enQueue(4);
		System.out.println(queue.deQueue());
		queue.enQueue(5);
		System.out.println(queue);
	}
}