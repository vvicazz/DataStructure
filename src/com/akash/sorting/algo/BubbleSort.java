package com.akash.sorting.algo;

import java.util.Arrays;

public class BubbleSort {

	private int arr[] = { 6, 5, 1, 6, 8, 7, 2, 4 };

	public static void main(String args[]) {

		BubbleSort bs = new BubbleSort();
		System.out.println(Arrays.toString(bs.arr));
		bs.sort();
		System.out.println(Arrays.toString(bs.arr));
	}

	private void sort() {

		int arr[] = this.arr;
		int length = arr.length;
		boolean isAnySwapped;
		for (int outer = 0; outer < length - 1; outer++) {
			isAnySwapped = false;
			for (int inner = 0; inner < length - outer - 1; inner++) {

				// swap elements
				if (arr[inner] > arr[inner + 1]) {
					isAnySwapped = true;
					arr[inner] = arr[inner] + arr[inner + 1];
					arr[inner + 1] = arr[inner] - arr[inner + 1];
					arr[inner] = arr[inner] - arr[inner + 1];
				}
			}

			// if no element is swapped, then list is sorted
			if (!isAnySwapped) {
				break;
			}
		}
	}
}

/**
 * Best case : O(n) Worst case : O(n2)
 * 
 * If any inner iteration in an outer iteration, does not have any swap
 * operation then, list is considered to be sorted.
 * 
 */
