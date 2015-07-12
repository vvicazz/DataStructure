package com.akash.sorting.algo;

import java.util.Arrays;

public class MergeKSortedList {

	final int N;
	final int K;

	private final int arr[][];

	private int finalArr[];
	private int tempArr[];

	private MinHeap heap;

	public static void main(String args[]) {

		int arr[][] = { { 1, 2, 3, 4, 5, 6 }, { 10, 20, 30, 40, 50, 60 },
				{ 100, 200, 300, 400, 500, 600 } };
		MergeKSortedList mksl = new MergeKSortedList(arr);
		mksl.printSortedArray();
	}

	public void printSortedArray() {
		System.out.println(Arrays.toString(finalArr));
	}

	public MergeKSortedList(int arr[][]) {

		this.arr = arr;
		K = arr.length;
		N = arr[0].length;
		finalArr = new int[K * N];
		tempArr = new int[K];
		MinHeap mh = new MinHeap(K);
		heap = mh;
		for (int i = 0; i < arr.length; i++) {
			addArrayElementToHeap(arr, i, 0);
		}
		applyMainLogic();
	}

	private void addArrayElementToHeap(int arr[][], int arrNumber, int pos) {

		heap.addElement(new MergeNode(arr[arrNumber][pos], arrNumber));
		tempArr[arrNumber] = pos;
	}

	private void applyMainLogic() {

		int finalArrCurrentPos = 0;
		int arrNumber = -1;
		while (finalArrCurrentPos < K * N) {
			MergeNode node = heap.popElement();
			finalArr[finalArrCurrentPos++] = node.getValue();
			arrNumber = node.getArrayNumber();
			if (tempArr[arrNumber] < N - 1) {
				addArrayElementToHeap(arr, arrNumber, tempArr[arrNumber] + 1);
			} else {
				int randArrayNumber = 0;
				for (int arrayPos : tempArr) {
					if (arrayPos == N - 1) {
						randArrayNumber++;
					} else {
						addArrayElementToHeap(arr, randArrayNumber,
								tempArr[randArrayNumber] + 1);
						break;
					}
				}
			}
		}
	}
}

class MergeNode {

	public MergeNode(int value, int arrayNumber) {
		this.value = value;
		this.arrayNumber = arrayNumber;
	}

	private int value;
	private int arrayNumber;

	public int getValue() {
		return value;
	}

	public int getArrayNumber() {
		return arrayNumber;
	}
}

class MinHeap {

	private MergeNode[] arr;
	private int heapSize;

	public int getHeapSize() {
		return heapSize;
	}

	// creates empty heap
	public MinHeap(int arrLength) {
		arr = new MergeNode[arrLength];
		this.heapSize = 0;
	}

	public MergeNode popElement() {

		if (heapSize == 0)
			return null;
		else {
			MergeNode node = arr[0];
			arr[0] = arr[heapSize - 1];
			arr[heapSize - 1] = null;
			heapSize--;
			satisfyHeapForNode(arr, 0);
			return node;
		}
	}

	public void addElement(MergeNode node) {

		if (heapSize < arr.length) {
			heapSize++;
			int nodePos = heapSize - 1;
			arr[nodePos] = node;
			int parentPos = heapSize > 2 ? (nodePos - 1) / 2 : -1;
			if (parentPos == -1)
				return;
			MergeNode parent = arr[parentPos];
			while (parent.getValue() > node.getValue()) {
				MergeNode temp = arr[parentPos];
				arr[parentPos] = node;
				arr[nodePos] = temp;
				nodePos = parentPos;
				parentPos = heapSize > 2 ? (nodePos - 1) / 2 : -1;
				if (parentPos == -1)
					return;
				parent = arr[parentPos];
			}
		}
	}

	private void satisfyHeapForNode(MergeNode arr[], int nodePos) {

		if (nodePos > ((heapSize >> 1) - 1)) {
			return;
		}

		int leftPos = 2 * nodePos + 1;
		int rightPos = 2 * nodePos + 2;
		int smallerIndex = nodePos;
		if (leftPos < heapSize
				&& arr[nodePos].getValue() > arr[leftPos].getValue()) {
			smallerIndex = leftPos;
		}
		if (rightPos < heapSize
				&& arr[smallerIndex].getValue() > arr[rightPos].getValue()) {
			smallerIndex = rightPos;
		}
		if (smallerIndex != nodePos) {
			MergeNode temp = arr[smallerIndex];
			arr[smallerIndex] = arr[nodePos];
			arr[nodePos] = temp;
			satisfyHeapForNode(arr, smallerIndex);
		}
	}
}
