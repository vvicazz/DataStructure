package com.akash.sorting.algo;

import java.util.Arrays;

public class QuickSort {

	// 6,5,3,1,8,7,2,4
	// 8,3,6,4,2,11,1,5
	// 3,7,8,5,2,1,9,5,4

	/**
	 * time = O(n)
	 * 
	 * pivot = 5
	 *
	 * (8) 3 6 4 2 11 1 (5)
	 *
	 * 1 3 (6) 4 2 11 (5) 8
	 *
	 * 1 3 (11) 4 2 (5) 6 8
	 *
	 * 1 3 2 4 (5) 11 6 8
	 *
	 *
	 * 1 3 2 4
	 *
	 * 11 6 8
	 */

	private int arr[] = { 3, 7, 8, 5, 2, 1, 9, 5, 4 };

	public static void main(String[] args) {
		QuickSort qs = new QuickSort();
		System.out.println(Arrays.toString(qs.arr));
		// qs.quickSort(0, qs.arr.length - 1);
		qs.iterativeQuickSort(0, qs.arr.length - 1);
		System.out.println(Arrays.toString(qs.arr));
	}

	// Recursive quick sort
	private void quickSort(int low, int high) {
		if (low < high) {
			int p = partition2(low, high);
			quickSort(low, p - 1);
			quickSort(p + 1, high);
		}
	}

	private void iterativeQuickSort(int low, int high) {

		int stack[] = new int[high - low + 1];
		int top = -1;
		stack[++top] = low;
		stack[++top] = high;
		while (top >= 0) {
			int end = stack[top--];
			int start = stack[top--];
			int pivot = partition2(start, end);
			if (pivot > start + 1) {
				stack[++top] = start;
				stack[++top] = pivot - 1;
			}
			if (pivot < end - 1) {
				stack[++top] = pivot + 1;
				stack[++top] = end;
			}
		}
	}

	private int partition(int low, int high) {
		int pivotIndex = high;
		for (int i = low; i < pivotIndex;) {

			if (arr[i] > arr[pivotIndex]) {
				int temp = arr[i];
				arr[i] = arr[pivotIndex - 1];
				arr[pivotIndex - 1] = arr[pivotIndex];
				arr[pivotIndex] = temp;
				pivotIndex--;
			}

			else {
				i++;
			}
		}
		return pivotIndex;
	}

	// Actual quick sort implementation
	private int partition2(int low, int high) {

		int pivotNum = arr[high];
		int i = low - 1;
		for (int j = low; j < high; j++) {
			if (pivotNum >= arr[j]) {
				i++;
				int temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
			}
		}
		int temp = arr[i + 1];
		arr[i + 1] = arr[high];
		arr[high] = temp;
		return i + 1;
	}
}