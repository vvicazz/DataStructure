package com.akash.docs.DS.DP;

public class MinCostPath {

	public static void main(String args[]) {
		int cost[][] = { { 1, 2, 3 }, { 4, 8, 2 }, { 1, 5, 3 } };
		int result = minCostPath(cost, 2, 2);
		System.out.println(result);
		System.out.println(minCostPathMemoized(cost));
	}

	private static int minCostPath(int arr[][], int n, int m) {
		if (n == 0 && m == 0) {
			return arr[n][m];
		}
		if (n == 0) {
			return minCostPath(arr, n, m - 1) + arr[n][m];
		}
		if (m == 0) {
			return minCostPath(arr, n - 1, m) + arr[n][m];
		}
		return min(minCostPath(arr, n - 1, m), minCostPath(arr, n, m - 1), minCostPath(arr, n - 1, m - 1)) + arr[n][m];
	}

	private static int minCostPathMemoized(int arr[][]) {
		int memoArr[][] = new int[arr.length][arr[0].length];
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				if (i == 0 && j == 0) {
					memoArr[i][j] = arr[i][j];
				} else if (i == 0) {
					memoArr[i][j] = memoArr[i][j - 1] + arr[i][j];
				} else if (j == 0) {
					memoArr[i][j] = memoArr[i - 1][j] + arr[i][j];
				} else {
					memoArr[i][j] = min(memoArr[i - 1][j], memoArr[i][j - 1], memoArr[i - 1][j - 1]) + arr[i][j];
				}
			}
		}
		return memoArr[arr.length - 1][arr[0].length - 1];
	}

	private static int min(int a, int b, int c) {
		if (a > b) {
			if (b > c)
				return c;
			else
				return b;
		} else {
			if (a > c)
				return c;
			else
				return a;
		}
	}
}