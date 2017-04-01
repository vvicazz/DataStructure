package com.akash.docs.DS.DP;

public class LongestPalimdromeSubstring {

	public static void main(String args[]) {
		String content = "ARADARZ";
		lps(content.toCharArray(), content.length());
	}

	static void lps(char arr[], int length) {
		boolean memoizedArray[][] = new boolean[length][length];
		int maxPalimdromeLength = 1;
		int startIndex = 0;
		// length of 1 is palimdrome
		for (int i = 0; i < length; i++) {
			memoizedArray[i][i] = true;
		}
		// check for length of 2 is palimdrome or not
		for (int i = 0; i < length - 1; i++) {
			if (arr[i] == arr[i + 1]) {
				memoizedArray[i][i + 1] = true;
				maxPalimdromeLength = 2;
				startIndex = i;
			}
		}
		// check for length 3 or greater is palimdrome or not
		for (int k = 3; k <= length; k++) {
			for (int i = 0; i < length - k + 1; i++) {
				int j = i + k - 1;
				if (memoizedArray[i + 1][j - 1] == true && arr[i] == arr[j]) {
					memoizedArray[i][j] = true;
					maxPalimdromeLength = k;
					startIndex = i;
				}
			}
		}
		System.out.println(maxPalimdromeLength);
		System.out.println(startIndex);
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				System.out.print(memoizedArray[i][j] + " ");
			}
			System.out.println();
		}
	}
}