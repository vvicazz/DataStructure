package com.akash.docs.DS.DP;

/**
 * Palimdrome is checked for first and last end characters. <br>
 * If both are same then length of longest palimdrome subsequqnce = 2 +
 * lps(first+1, end-1) <br>
 * 
 * If both are not same the length = max of { lps(first+1, end), lps(first,
 * end-1) } <br>
 * <br>
 * For memoized solution we need to check for every length of subsequence <br>
 * length =1, 2, 3, .. n-1 <br>
 * 
 * because length of string n, will use solution from n-1 and n-2 <br>
 * <br>
 * Similar kind of solution can be provided for longest palimdrome Substring
 */
public class LongestPalimdromeSubsequence {

	public static void main(String args[]) {
		String content = "BBABCBCAB";
		int i = 0;
		int j = content.length() - 1;
		int count = lps(content.toCharArray(), content.length(), i, j);
		System.out.println(count);
		System.out.println(longestPalindromeSubsequenceMemoized(content.toCharArray()));
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

	private static int longestPalindromeSubsequenceMemoized(char arr[]) {
		int memo[][] = new int[arr.length][arr.length];
		for (int i = 0; i < arr.length; i++) {
			memo[i][i] = 1;
		}
		int start, end;
		for (int i = 1; i < arr.length; i++) {
			for (int j = 0; j < arr.length - i; j++) {
				start = j;
				end = j + i;
				if (arr[start] == arr[end]) {
					memo[start][end] = getMemoArrayValue(memo, start + 1, end - 1) + 2;
				} else {
					memo[start][end] = max(getMemoArrayValue(memo, start, end - 1),
							getMemoArrayValue(memo, start + 1, end));
				}
			}
		}
		print2DArray(memo);
		return memo[0][arr.length - 1];
	}

	private static void print2DArray(int memo[][]) {
		for (int i = 0; i < memo.length; i++) {
			for (int j = 0; j < memo[0].length; j++) {
				System.out.print(memo[i][j] + " ");
			}
			System.out.println();
		}
	}

	private static int getMemoArrayValue(int memo[][], int row, int column) {
		if (row >= memo.length || column >= memo[0].length || row < 0 || column < 0) {
			return 0;
		} else {
			return memo[row][column];
		}
	}

	private static int max(int a, int b) {
		if (a < b)
			return b;
		return a;
	}
}