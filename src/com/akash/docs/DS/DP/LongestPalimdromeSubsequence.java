package com.akash.docs.DS.DP;

public class LongestPalimdromeSubsequence {

	public static void main(String args[]) {
		String content = "BBABCBCAB";
		int i = 0;
		int j = content.length() - 1;
		int count = lps(content.toCharArray(), content.length(), i, j);
		System.out.println(count);
	}

	public static int lps(char arr[], int length, int i, int j) {
		if (i > j) {
			return 0;
		}
		if (i == j) {
			return 1;
		} else if (arr[i] == arr[j]) {
			return lps(arr, length, i + 1, j - 1) + 2;
		} else {
			return max(lps(arr, length, i, j - 1), lps(arr, length, i + 1, j));
		}
	}

	private static int max(int a, int b) {
		if (a < b)
			return b;
		return a;
	}
}