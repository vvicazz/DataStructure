package com.akash.sorting.algo;

import java.util.Arrays;

public class MergeSort {

	private int arr[] = { 6, 5, 1, 6, 8, 7, 2, 4 };

	public static void main(String args[]) {

		MergeSort ms = new MergeSort();
		System.out.println(Arrays.toString(ms.arr));
		ms.sort(0, ms.arr.length - 1);
		System.out.println(Arrays.toString(ms.arr));
	}

	private void sort(int low, int high) {

		if (low < high) {
			int mid = (high + low) / 2;
			sort(low, mid);
			sort(mid + 1, high);
			merge(low, mid, high);
		}
	}

	private void merge(int low, int mid, int high) {

		
	}
}
