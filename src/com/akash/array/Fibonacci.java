package com.akash.array;

public class Fibonacci {

	public static void main(String[] args) {
		int n = 9;
		System.out.println(fibonacci1(n));
		System.out.println(fibonacci2(n));
		System.out.println(fibonacci3(n));
	}

	static int fibonacci1(int n) {
		if (n <= 1)
			return n;
		return fibonacci1(n - 1) + fibonacci1(n - 2);
	}

	static int fibonacci3(int n) {
		int[] mem = new int[n + 1];
		mem[0] = 0;
		mem[1] = 1;
		for (int i = 2; i <= n; i++) {
			mem[i] = mem[i - 1] + mem[i - 2];
		}
		return mem[n];
	}

	static int fibonacci2(int n) {
		int a = 0, b = 1, c = b;
		if (n <= 0)
			return a;
		for (int i = 2; i <= n; i++) {
			c = a + b;
			a = b;
			b = c;
		}
		return c;
	}
}