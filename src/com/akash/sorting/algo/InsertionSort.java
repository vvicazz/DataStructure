package com.akash.sorting.algo;

import java.util.Arrays;

public class InsertionSort {
	// logic for sorting => Tash ke patte

	public static void main(String args[]) {
		int arr[] = { 6, 5, 3, 1, 8, 7, 2, 4 };
		System.out.println(Arrays.toString(arr));
		insertionSort(arr);
		// recursiveInsertionSort(arr);
		System.out.println(Arrays.toString(arr));
	}

	static void insertionSort(int arr[]) {
		int arrLength = arr.length;
		for (int i = 1; i < arrLength; i++) {
			int counterVar = arr[i];
			int counterPos = i;
			for (int j = i - 1; j >= 0; j--) {
				if (counterVar < arr[j]) {
					arr[j + 1] = arr[j];
					counterPos = j;
				} else {
					break;
				}
			}
			arr[counterPos] = counterVar;
		}
	}

	private static void recursiveInsertionSort(int arr[]) {
		recursiveInsertion(arr, 0, arr.length - 2);
		fixInsertionLogic(arr, arr.length - 1);
	}

	private static void recursiveInsertion(int arr[], int start, int end) {
		if (start < end) {
			recursiveInsertion(arr, start, end - 1);
			fixInsertionLogic(arr, end);
		}
	}

	private static void fixInsertionLogic(int arr[], int pos) {
		int temp = arr[pos];
		int i = pos - 1;
		while (i >= 0) {
			if (temp < arr[i]) {
				arr[i + 1] = arr[i];
				i--;
			} else {
				break;
			}
		}
		if (i + 1 < pos) {
			arr[i + 1] = temp;
		}
	}
}