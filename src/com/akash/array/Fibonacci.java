package com.akash.array;

import java.util.Arrays;

public class Fibonacci {

	public static void main(String args[]) {
		printFibonacci1(15);
		printFibonacci2(15);
	}

	private static void printFibonacci1(int n) {
		if (n == 0 || n < 0) {
			return;
		} else if (n == 1) {
			System.out.println(0);
			return;
		} else {
			int a = 0;
			int b = 1;
			int c = 0;
			int count = 1;
			System.out.println(a);
			while (count < n) {
				System.out.println(b);
				c = a + b;
				count++;
				a = b;
				b = c;
			}
		}
	}

	private static void printFibonacci2(int n) {
		if (n == 0 || n < 0) {
			return;
		} else if (n == 1) {
			System.out.println(0);
			return;
		} else {
			int fib[] = new int[n];
			fib[0] = 0;
			fib[1] = 1;
			int counter = 2;
			while (counter < n) {
				fib[counter] = fib[counter - 1] + fib[counter - 2];
				counter++;
			}
			System.out.println(Arrays.toString(fib));
		}
	}
}