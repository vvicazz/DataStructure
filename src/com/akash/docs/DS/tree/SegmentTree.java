package com.akash.docs.DS.tree;

public class SegmentTree {

	// it is a complete binary tree
	// similar to heap
	// every level, except possibly the last, is completely filled, and all nodes
	// are as far left as possible

	int[] elem;
	int numberOfElem;

	// segment tree for sum
	public void constructSumTree(int[] input, int n) {
		// Height of segment tree
		int x = (int) (Math.ceil(Math.log(n) / Math.log(2)));
		// Maximum size of segment tree
		int max_size = 2 * (int) Math.pow(2, x) - 1;
		elem = new int[max_size]; // allocate memory
		costructSumUtil(input, 0, n - 1, 0);

	}

	private int costructSumUtil(int[] input, int start, int end, int pos) {
		if (start == end) {
			elem[pos] = input[start];
			return elem[pos];
		}
		int mid = start + (end - start) / 2;
		elem[pos] = costructSumUtil(input, start, mid, 2 * pos + 1) + costructSumUtil(input, mid + 1, end, 2 * pos + 2);
		return elem[pos];
	}

	public int getSumNumberInRange(int[] input, int qlow, int qHigh) {
		int len = input.length;
		if (qlow < 0 || qlow > len || qHigh < qlow) {
			return Integer.MAX_VALUE;
		}
		return getRangeSumNumber(elem, qlow, qHigh, 0, len - 1, 0);
	}

	private int getRangeSumNumber(int[] segmentTree, int qlow, int qhigh, int low, int high, int pos) {
		if (qlow <= low && qhigh >= high) {
			return segmentTree[pos];
		}
		if (qlow > high || qhigh < low) {
			return 0;
		}
		int mid = (low + high) / 2;
		return getRangeSumNumber(segmentTree, qlow, qhigh, low, mid, 2 * pos + 1)
				+ getRangeSumNumber(segmentTree, qlow, qhigh, mid + 1, high, 2 * pos + 2);
	}

	/////////////////////////////////////////////////
	// sum segment tree over, min segment tree starts
	////////////////////////////////////////////////

	// segment tree for min
	public void constructMinTree(int[] input, int n) {
		// Height of segment tree
		int x = (int) (Math.ceil(Math.log(n) / Math.log(2)));
		// Maximum size of segment tree
		int max_size = 2 * (int) Math.pow(2, x) - 1;
		elem = new int[max_size]; // allocate memory
		costructMinUtil(input, 0, n - 1, 0);
	}

	private int costructMinUtil(int[] input, int start, int end, int pos) {
		if (start == end) {
			elem[pos] = input[start];
			return elem[pos];
		}
		int mid = start + (end - start) / 2;
		elem[pos] = Math.min(costructMinUtil(input, start, mid, 2 * pos + 1),
				costructMinUtil(input, mid + 1, end, 2 * pos + 2));
		return elem[pos];

	}

	public int getMinNumberInRange(int[] input, int qlow, int qHigh) {
		int len = input.length;
		if (qlow < 0 || qlow > len || qHigh < qlow) {
			return Integer.MAX_VALUE;
		}
		return getRangeMinNumber(elem, qlow, qHigh, 0, len - 1, 0);
	}

	private int getRangeMinNumber(int[] segmentTree, int qlow, int qhigh, int low, int high, int pos) {
		if (qlow <= low && qhigh >= high) {
			return segmentTree[pos];
		}
		if (qlow > high || qhigh < low) {
			return 0;
		}
		int mid = (low + high) / 2;
		return getRangeMinNumber(segmentTree, qlow, qhigh, low, mid, 2 * pos + 1)
				+ getRangeMinNumber(segmentTree, qlow, qhigh, mid + 1, high, 2 * pos + 2);
	}

	public static void main(String[] args) {
		int input[] = { 1, 3, 5, 7, 9, 11 };
		int n = input.length;
		SegmentTree tree = new SegmentTree();
		tree.constructMinTree(input, n);
		System.out.println(tree.getMinNumberInRange(input, 4, 5));
		System.out.println(tree.getMinNumberInRange(input, 0, 1));
		System.out.println(tree.getMinNumberInRange(input, 1, 1));
		System.out.println(tree.getMinNumberInRange(input, 1, 3));
		System.out.println(tree.getMinNumberInRange(input, 2, 5));
		System.out.println(tree.getMinNumberInRange(input, 4, 5));
	}
}