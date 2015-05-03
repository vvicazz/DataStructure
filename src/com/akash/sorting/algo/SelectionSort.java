package com.akash.sorting.algo;

import java.util.Arrays;

public class SelectionSort {

	private int arr[] = { 6, 5, 1, 6, 8, 7, 2, 4 };

	public static void main(String args[]) {

		SelectionSort ss = new SelectionSort();
		System.out.println(Arrays.toString(ss.arr));
		ss.sort();
		System.out.println(Arrays.toString(ss.arr));
	}

	private void sort() {

		int arr[] = this.arr;
		int length = arr.length;
		int minPos, tempPos, min;
		for (int outer = 0; outer < length - 1; outer++) {
			minPos = outer;
			min = arr[minPos];
			tempPos = minPos;
			for (int inner = outer + 1; inner < length; inner++) {
				if (arr[inner] < min) {
					tempPos = inner;
					min = arr[inner];
				}
			}
			if (tempPos > minPos) {
				arr[tempPos] = arr[tempPos] + arr[minPos];
				arr[minPos] = arr[tempPos] - arr[minPos];
				arr[tempPos] = arr[tempPos] - arr[minPos];
			}
		}
	}
}