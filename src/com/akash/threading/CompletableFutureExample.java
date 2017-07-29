package com.akash.threading;

import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureExample {

	public static void main(String args[]) throws InterruptedException, ExecutionException {
		CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
			System.out.println("supplyAsync thread : " + Thread.currentThread().getName());
			sleep(1000);
			return "hello ";
		});
		future.thenAccept((String str) -> {
			System.out.println("thenAccept : " + Thread.currentThread().getName());
			System.out.println(str + new Date());
		});
		System.out.println("main thread : " + Thread.currentThread().getName());
		System.out.println("before completableFuture : " + new Date());
		sleep(5000);
		future.get();
		System.out.println("after completableFuture : " + new Date());
	}

	private static void sleep(int ms) {
		try {
			Thread.sleep(ms);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

/**
 * A new thread is created for completableFuture object. <br>
 * completableFuture.get() will block current thread. <br>
 * Callback methods can be executed in main thread via threadPoolExecutor thread <br>
 * Thread execution and its callback are separate in thread. <br>
 * So, get() method can be called in the end of all independent thread
 * executions. <br>
 */
