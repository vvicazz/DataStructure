package com.akash.threading;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


//http://www.journaldev.com/1090/java-callable-future-example

//Try to make Callable object as generic.
//here it is taken as String
//You can take any Pojo for returning an object
public class MyCallable implements Callable<String> {

	@Override
	public String call() throws Exception {

		// Returning this status as process end status
		String taskStatus = "success";

		Runtime rt = Runtime.getRuntime();
		StringBuilder sb = new StringBuilder();
		try {
			Process pr = rt.exec("notepad");

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					pr.getErrorStream()));
			String line = "";
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			String videoFFMpegInfo = sb.toString();
			System.out.println("output received is" + videoFFMpegInfo);
			pr.waitFor();
		} catch (IOException io) {
			taskStatus = "error:" + io;
			io.printStackTrace();
		} catch (InterruptedException ie) {
			taskStatus = "error:" + ie;
			ie.printStackTrace();
		}
		return taskStatus;
	}

	public static void main(String args[]) {

		executeSystemProcess();
	}

	static void executeSystemProcess() {

		// Get ExecutorService from Executors utility class, thread pool size is
		// 10
		// You can set this value from properties file
		ExecutorService executor = Executors.newFixedThreadPool(10);
		// create a list to hold the Future object associated with Callable
		List<Future<String>> list = new ArrayList<Future<String>>();
		// Create MyCallable instance
		Callable<String> callable = new MyCallable();
		for (int i = 0; i < 5; i++) {
			// submit Callable tasks to be executed by thread pool
			Future<String> future = executor.submit(callable);
			// add Future to the list, we can get return value using Future
			list.add(future);
		}
		for (Future<String> fut : list) {
			try {
				// print the return value of Future, notice the output delay in
				// console
				// because Future.get() waits for task to get completed
				System.out.println(new Date() + "::" + fut.get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		// shut down the executor service now
		executor.shutdown();
	}
}
