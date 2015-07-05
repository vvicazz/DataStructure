package com.akash.sorting.algo;

import java.util.Arrays;

public class ImprovedHeapSort {

	private int arr[] = { 3, 7, 8, 5, 2, 1, 9, 5, 4 };
	private int heapSize = arr.length;
	private int arraySize = arr.length;

	public static void main(String[] args) {
		ImprovedHeapSort hs = new ImprovedHeapSort();
		System.out.println(Arrays.toString(hs.arr));
		hs.createHeap(hs.arr);
		System.out.println(Arrays.toString(hs.arr));
		hs.sortHeap(hs.arr);
		System.out.println(Arrays.toString(hs.arr));
	}

	void createHeap(int arr[]) {

		int startCounter = heapSize >> 1;
		for (int counter = startCounter - 1; counter >= 0; counter--) {
			satisfyHeapForNode(arr, counter);
		}
	}

	/**
	 * This method puts an element at nodePos to its actual position in the
	 * heap, by satisfying MAX HEAP property
	 * 
	 * @param nodePos
	 */
	void satisfyHeapForNode(int arr[], int nodePos) {

		// this condition is used to check if a node is leaf,
		// then no need to check it further for heap property
		if (nodePos > ((heapSize >> 1) - 1)) {
			return;
		}

		// node is replaced by larger element between left and right node
		int leftPos = 2 * nodePos + 1;
		int rightPos = 2 * nodePos + 2;
		int largerIndex = nodePos;
		if (arr[nodePos] < arr[leftPos] && leftPos < heapSize) {
			largerIndex = leftPos;
		}
		if (arr[largerIndex] < arr[rightPos] && rightPos < heapSize) {
			largerIndex = rightPos;
		}
		if (largerIndex != nodePos) {
			int temp = arr[largerIndex];
			arr[largerIndex] = arr[nodePos];
			arr[nodePos] = temp;
			satisfyHeapForNode(arr, largerIndex);
		}
	}

	void sortHeap(int arr[]) {

		while (heapSize > 1) {
			int temp = arr[0];
			arr[0] = arr[heapSize - 1];
			arr[heapSize - 1] = temp;
			heapSize--;
			satisfyHeapForNode(arr, 0);
		}
	}
}

/**
 * (1) Viewing a heap as a tree, we define the height of a node in a heap to be
 * the number of edges on the longest simple downward path from the node to a
 * leaf, and we define the height of the heap to be the height of its root.
 *
 * (2) the elements in the heap A[floor(n/2)+1..n] are all leaves of the tree,
 * when indexing starts from 1.
 * 
 * (3) Not a stable sort
 * 
 * Max heap : A[parent(i)] >= A[i] Heap is always a complete tree 0 <=
 * A:heap-size <= A:length
 * 
 * (Q) Write an efficient MAX-HEAPIFY that uses an iterative control construct
 * (a loop) instead of recursion
 */
