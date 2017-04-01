package com.akash.docs.DS.DP;

//http://www.geeksforgeeks.org/dynamic-programming-set-12-longest-palindromic-subsequence/
public class LongestCommonSubsequence {

	public static void main(String args[]) {
		String s1 = "AGGTAB";
		String s2 = "GXTXAYB";
		int count = recursiveLcs(s1.toCharArray(), s2.toCharArray(), s1.length(), s2.length());
		int memoizedCount = memoizedLcs(s1.toCharArray(), s2.toCharArray(), s1.length(), s2.length());
		System.out.println(count);
		System.out.println(memoizedCount);
	}

	private static int recursiveLcs(char[] s1, char[] s2, int l1, int l2) {
		if (l1 == 0 || l2 == 0) {
			return 0;
		}
		if (s1[l1 - 1] == s2[l2 - 1]) {
			return recursiveLcs(s1, s2, l1 - 1, l2 - 1) + 1;
		} else {
			return max(recursiveLcs(s1, s2, l1, l2 - 1), recursiveLcs(s1, s2, l1 - 1, l2));
		}
	}

	private static int memoizedLcs(char[] s1, char[] s2, int l1, int l2) {
		int memoizedArray[][] = new int[l1 + 1][l2 + 1];
		for (int i = 0; i <= l1; i++) {
			for (int j = 0; j <= l2; j++) {
				if (i == 0 || j == 0) {
					memoizedArray[i][j] = 0;
				} else if (s1[i - 1] == s2[j - 1]) {
					memoizedArray[i][j] = memoizedArray[i - 1][j - 1] + 1;
				} else {
					memoizedArray[i][j] = max(memoizedArray[i][j - 1], memoizedArray[i - 1][j]);
				}
			}
		}
		return memoizedArray[l1][l2];
	}

	private static int max(int a, int b) {
		if (a < b)
			return b;
		return a;
	}
}