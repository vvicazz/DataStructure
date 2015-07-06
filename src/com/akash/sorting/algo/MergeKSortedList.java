package com.akash.sorting.algo;

import java.util.Arrays;

public class MergeKSortedList {

	static final int N = 3;
	static final int K = 4;

	private MinHeap heap;

	int arr[][] = { { 1, 2, 3, 4 }, { 10, 20, 30, 40 }, { 100, 200, 300, 400 } };

	int finalArr[] = new int[K * N];

	void addArrayElementToHeap(int arr[][], int arrNumber, int pos) {

		heap.addElement(new MergeNode(arr[arrNumber][pos], arrNumber, pos));
	}

	public static void main(String args[]) {

		MinHeap mh = new MinHeap(K);
		MergeKSortedList mksl = new MergeKSortedList();
		mksl.heap = mh;
		for (int i = 0; i < mksl.arr.length; i++) {
			mksl.addArrayElementToHeap(mksl.arr, i, 0);
		}
		mksl.applyMainLogic();
		Arrays.toString(mksl.finalArr);
	}

	void applyMainLogic() {

		int finalArrCurrentPos = 0;
		int arrNumber, posNumber;
		while (finalArrCurrentPos < K * N) {
			MergeNode node = heap.popElement();
			finalArr[finalArrCurrentPos++] = node.getValue();
			arrNumber = node.getArrayNumber();
			posNumber = node.getPos();
			if (posNumber < K - 1) {
				addArrayElementToHeap(arr, arrNumber, posNumber + 1);
			} else {
				// find some other array
			}
		}
	}
}

class MergeNode {

	public MergeNode(int value, int arrayNumber, int pos) {
		this.value = value;
		this.arrayNumber = arrayNumber;
		this.pos = pos;
	}

	private int value;
	private int arrayNumber;
	private int pos;

	public int getValue() {
		return value;
	}

	public int getArrayNumber() {
		return arrayNumber;
	}

	public int getPos() {
		return pos;
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

	public void createHeap(MergeNode arr[]) {

		int startCounter = heapSize >> 1;
		for (int counter = startCounter - 1; counter >= 0; counter--) {
			satisfyHeapForNode(arr, counter);
		}
	}

	private void satisfyHeapForNode(MergeNode arr[], int nodePos) {

		if (nodePos > ((heapSize >> 1) - 1)) {
			return;
		}

		int leftPos = 2 * nodePos + 1;
		int rightPos = 2 * nodePos + 2;
		int smallerIndex = nodePos;
		if (arr[nodePos].getValue() > arr[leftPos].getValue()
				&& leftPos < heapSize) {
			smallerIndex = leftPos;
		}
		if (arr[smallerIndex].getValue() > arr[rightPos].getValue()
				&& rightPos < heapSize) {
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
