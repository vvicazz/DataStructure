package com.akash.docs.DS.DP;

/**
 * if( i>j && arr[i]>=arr[j] ) <br>
 * 		lis(i) = lis(j) + 1 <br>
 * else <br>
 * 		lis(i) = 1; <br>
 *
 */
public class LongestIncreasingSubsequence {

	public static void main(String args[]) {
		int arr[] = { 10, 22, 9, 33, 21, 50, 41, 60 };
		System.out.println(longestIncreasingSubsequence(arr, arr.length - 1));
		System.out.println(longestIncreasingSubsequenceMemoized(arr));
	}

	private static int longestIncreasingSubsequence(int arr[], int n) {
		if (n == 0) {
			return 1;
		}
		int tempResult, result = 1;
		for (int i = 0; i < n; i++) {
			if (arr[n] >= arr[i]) {
				tempResult = longestIncreasingSubsequence(arr, i) + 1;
				if (tempResult > result) {
					result = tempResult;
				}
			}
		}
		return result;
	}

	private static int longestIncreasingSubsequenceMemoized(int arr[]) {
		int memo[] = new int[arr.length];
		for (int i = 0; i < memo.length; i++) {
			memo[i] = 1;
		}
		for (int i = 1; i < arr.length; i++) {
			for (int j = 0; j < i; j++) {
				if (arr[j] <= arr[i] && memo[j] >= memo[i]) {
					memo[i] = memo[j] + 1;
				}
			}
		}
		return memo[arr.length - 1];
	}
}