package com.akash.sorting.algo;

import java.util.Arrays;

/**
 * Heap is a complete binary tree implemented as array. <br>
 * Two types of heap : min heap , max heap. <br>
 * max heap --> A[parent] >= A[child] <br>
 * min heap --> A[parent] <= A[child] <br>
 * <br>
 * max heap is used for heapsort. <br>
 * Height of node = no of edges on the longest path from given node to a leaf. <br>
 * <br>
 * In zero based indexing : <br>
 * parent(i) = (i-1) >> 1 <br>
 * child(i) = (i<<1) + 1 , (i<<1) + 2 <br>
 * 
 * <br><br><br>
 * 
 * Questions :<br>
 * 
 * (Q) Stack depth of quick sort ? <br>
 * (Q) Merge k sorted list in one sorted list. <br>
 * (Q) Find min element in a max heap ? <br>
 * (Q) Is an array that is sorted order a min heap ? <br>
 * 
 * (Q) D-ary heap ==> a heap having children in the range of 0 to d. <br>
 * (Q) Young Tableaus ==> m-n matrix where elements are sorted in rows and
 * columns. <br>
 * <br>
 * (Q) Fuzzy sorting in intervals. <br>
 * 
 * (Q)Show that, with the array representation for storing an n-element heap,
 * the leaves are the nodes indexed by 
 * <br>Ans : <br>
 * 
 * To satisfy the condition of a leaf node, for i as an index of a leaf nodes : <br>
 * n < 2*i + 1 <br>
 * 
 * (n-1)/2 < i <br>
 * 
 * hence range of leaf nodes in heap of n nodes : <br>
 * 
 * (n-1)/2 < i <= n <br>
 */
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
					swapArrayElements(arr, 2 * tempLocationInHeap + 2, tempLocationInHeap);
					tempLocationInHeap = 2 * tempLocationInHeap + 2;
				} else {
					swapArrayElements(arr, 2 * tempLocationInHeap + 1, tempLocationInHeap);
					tempLocationInHeap = 2 * tempLocationInHeap + 1;
				}
			}
		}
	}

	private static void createMaxHeap(int arr[]) {
		for (int counter = 1; counter <= arr.length - 1; counter++) {
			int valueForHeap = counter;
			while (arr[valueForHeap] > arr[(valueForHeap - 1) / 2] && valueForHeap > 0) {
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




/**

public class HeapSort {

	private int arr[];
	private int heapSize;

	HeapSort(int arr[], int heapSize) {

		this.arr = arr;
		this.heapSize = heapSize;
	}

	private int parent(int i) {
		if (i == 0) {
			throw new RuntimeException("no parent for position " + i + " found in array");
		}
		return (i - 1) >> 1;
	}

	private int left(int i) {
		return (i << 1) + 1;
	}

	private int right(int i) {
		return (i << 1) + 2;
	}

	
	 // heapify element at position i in the heap 
	 // @param arr
	 // @param pos
	 
	private void maxHeapify(int arr[], int pos) {

		int left = left(pos);
		int right = right(pos);
		int largestPos = pos;
		if (left < heapSize && arr[pos] < arr[left]) {
			largestPos = left;
		}
		if (right < heapSize && arr[largestPos] < arr[right]) {
			largestPos = right;
		}
		if (largestPos != pos) {
			int temp = arr[pos];
			arr[pos] = arr[largestPos];
			arr[largestPos] = temp;
			maxHeapify(arr, largestPos);
		}
	}

	public void buildMaxHeap(int arr[]) {

		for (int counter = (heapSize-1) / 2; counter >= 0; counter--) {
			maxHeapify(arr, counter);
		}
	}

	public void sortHeap(int arr[]) {

		buildMaxHeap(arr);
		for (int counter = arr.length - 1; counter > 0; counter--) {

			int temp = arr[counter];
			arr[counter] = arr[0];
			arr[0] = temp;
			heapSize--;
			maxHeapify(arr, 0);
		}
	}

	public int[] getHeap() {
		return Arrays.copyOf(this.arr, this.arr.length);
	}

	public static void main(String args[]) {

		int arr[] = { 4, 1, 8, 12, 3, 20 };
		HeapSort hs = new HeapSort(arr, arr.length);
		hs.sortHeap(arr);
		System.out.println(Arrays.toString(hs.getHeap()));
	}
}

 */
