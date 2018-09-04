package com.akash.array;

public class PrintAllPermutations {

	public static void main(String args[]) {
		String str = "ABCD";
		printAllPermutations(str.toCharArray(), 0, str.length() - 1);
	}

	static void printAllPermutations(char arr[], int start, int end) {
		if (start > end) {
			return;
		}
		if (start == end) {
			print(arr);
			return;
		}
		for (int i = start; i <= end; i++) {
			swap(arr, start, i);
			printAllPermutations(arr, start + 1, end);
			swap(arr, start, i);
		}
	}

	static void print(char arr[]) {
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i]);
		}
		System.out.println();
	}

	static void swap(char arr[], int pos1, int pos2) {
		char temp = arr[pos1];
		arr[pos1] = arr[pos2];
		arr[pos2] = temp;
	}
}
