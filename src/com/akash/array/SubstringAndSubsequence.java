package com.akash.array;

public class SubstringAndSubsequence {

	public static void main(String args[]) {
		int arr[] = { 1, 2, 3, 4 };
		// printSubstring(arr);
		printSubsequence(arr);
	}

	// n*(n+1)/2
	private static void printSubstring(int arr[]) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = i; j < arr.length; j++) {
				printArray(arr, i, j);
			}
		}
	}

	private static void printArray(int arr[], int start, int end) {
		for (int i = start; i <= end && end < arr.length; i++) {
			System.out.print(arr[i]);
		}
		System.out.println();
	}

	// 2**n-1
	private static void printSubsequence(int arr[]) {
		double opsize = Math.pow(arr.length, 2);
		for (int counter = 1; counter < opsize; counter++) {
			for (int i = 0; i < arr.length; i++) {
				if ((counter & (1 << i)) > 0) {
					System.out.print(arr[i]);
				}
			}
			System.out.println();
		}
	}
}