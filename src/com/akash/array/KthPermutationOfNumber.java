package com.akash.array;

import java.util.ArrayList;
import java.util.List;

public class KthPermutationOfNumber {

	public static void main(String args[]) {
		printKthPermutation(3, 6);
	}

	static void printKthPermutation(int n, int k) {
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 1; i <= n; i++) {
			list.add(i);
		}
		recursiveKthPermutation(n, list, k - 1);
	}

	static private void recursiveKthPermutation(int n, List<Integer> list, int k) {
		if (k == 0) {
			for (int i = 0; i < list.size(); i++) {
				System.out.print(list.get(i));
			}
			return;
		}
		int fact = fact(n - 1);
		int div = k / fact;
		System.out.print(list.get(div));
		list.remove(div);
		recursiveKthPermutation(n - 1, list, (k % fact));
	}

	static private int fact(int n) {
		if (n <= 1) {
			return 1;
		}
		return n * fact(n - 1);
	}
}