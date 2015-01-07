package com.akash.sorting.algo;

import java.util.Arrays;

public class HeapSort {

	public static void main(String[] args) {
		int arr[] = { 3, 7, 8, 5, 2, 1, 9, 5, 4 };
		heapSort(arr);
		System.out.println(Arrays.toString(arr));
	}

	private static void heapSort(int arr[]) {
		createMaxHeap(arr);
		deleteFromHeap(arr);
	}

	private static void deleteFromHeap(int arr[]) {
		for (int counter = arr.length - 1; counter > 0; counter--) {
			int tempLocationInHeap = 0;
			swapArrayElements(arr, counter, tempLocationInHeap);
			while (((2 * tempLocationInHeap + 1) < counter)
					&& (arr[tempLocationInHeap] < arr[2 * tempLocationInHeap + 1])
					|| ((2 * tempLocationInHeap + 2) < counter)
					&& (arr[tempLocationInHeap] < arr[2 * tempLocationInHeap + 2])) {

				if (((2 * tempLocationInHeap + 2) < counter)
						&& (arr[2 * tempLocationInHeap + 2] > arr[2 * tempLocationInHeap + 1])) {
					swapArrayElements(arr, 2 * tempLocationInHeap + 2,
							tempLocationInHeap);
					tempLocationInHeap = 2 * tempLocationInHeap + 2;
				} else {
					swapArrayElements(arr, 2 * tempLocationInHeap + 1,
							tempLocationInHeap);
					tempLocationInHeap = 2 * tempLocationInHeap + 1;
				}
			}
		}
	}

	private static void createMaxHeap(int arr[]) {
		for (int counter = 1; counter <= arr.length - 1; counter++) {
			int valueForHeap = counter;
			while (arr[valueForHeap] > arr[(valueForHeap - 1) / 2]
					&& valueForHeap > 0) {
				swapArrayElements(arr, valueForHeap, ((valueForHeap - 1) / 2));
				valueForHeap = (valueForHeap - 1) / 2;
			}
		}
	}

	private static void swapArrayElements(int arr[], int pos1, int pos2) {
		int temp = arr[pos1];
		arr[pos1] = arr[pos2];
		arr[pos2] = temp;
	}
}

/**
 * first create a heap in array implementation (either max or min heap) <br>
 * a[n] <= a[(n-1)/2] -- Max heap <br>
 * a[n] >= a[(n-1)/2] -- Min heap <br>
 * 
 * 
 * Max heap <br>
 * 
 * 3 (7) 8 5 2 1 9 5 4 <br>
 * 7 3 (8) 5 2 1 9 5 4 <br>
 * 8 3 7 (5) 2 1 9 5 4 <br>
 * 8 5 7 3 (2) 1 9 5 4 <br>
 * 8 5 7 3 2 (1) 9 5 4 <br>
 * 8 5 7 3 2 1 (9) 5 4 <br>
 * 9 5 8 3 2 1 7 (5) 4 <br>
 * 9 5 8 5 2 1 7 3 (4) <br>
 * 9 5 8 5 2 1 7 3 4 <br>
 * 
 * 
 * delete element from heap and replace it with last element in heap and then
 * balance the heap ie. replace 9 and 4.
 * 
 * (9) 5 8 5 2 1 7 3 (4) <br>
 * (4) 5 (8) 5 2 1 7 3 9 <br>
 * 8 5 (4) 5 2 1 (7) 3 9 <br>
 * 8 5 7 5 2 1 4 3 9 <br>
 * 
 * replace 8 and 3 <br>
 * 
 * (8) 5 7 5 2 1 4 (3) 9 <br>
 * (3) 5 (7) 5 2 1 4 8 9 <br>
 * 7 5 (3) 5 2 1 (4) 8 9 <br>
 * 7 5 4 5 2 1 3 8 9 <br>
 * 
 * 
 * worst case complexity = N*(logN) <br>
 * each insertion in heap(min or max) takes logN comparisons at max <br>
 * each deletion from heap(min or max) takes logN comparisons at max <br>
 * 
 * 1 2 3 4 5 5 7 8 9
 */
