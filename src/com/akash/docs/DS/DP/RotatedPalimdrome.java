package com.akash.docs.DS.DP;

public class RotatedPalimdrome {

	public static void main(String args[]) {
		String content = "DARRA";
		String newContent = content + content;
		boolean result = lps(newContent.toCharArray(), newContent.length());
		System.out.println(result);
	}

	static boolean lps(char arr[], int length) {
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
				if (maxPalimdromeLength == length / 2) {
					return true;
				}
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
					if (maxPalimdromeLength == length / 2) {
						return true;
					}
				}
			}
		}
		return false;
	}
}