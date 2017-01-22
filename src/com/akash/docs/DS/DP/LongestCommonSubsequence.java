package com.akash.docs.DS.DP;

//http://www.geeksforgeeks.org/dynamic-programming-set-12-longest-palindromic-subsequence/
public class LongestCommonSubsequence {

	public static void main(String args[]) {
		String s1 = "AGGTAB";
		String s2 = "GXTXAYB";
		int count = recursiveLcs(s1.toCharArray(), s2.toCharArray(), s1.length(), s2.length());
		System.out.println(count);
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

	private static int max(int a, int b) {
		if (a < b)
			return b;
		return a;
	}
}