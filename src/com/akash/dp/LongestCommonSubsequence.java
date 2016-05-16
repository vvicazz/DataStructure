package com.akash.dp;

public class LongestCommonSubsequence {

	/**
	 * Recursive formula for LCS <br>
	 *
	 * if X[i]==Y[j] <br>
	 * &nbsp;&nbsp; C[i][j] = 1 + C[i-1][j-1] <br>
	 * else <br>
	 * &nbsp;&nbsp; C[i][j] = MAX( C[i][j-1] , C[j][i-1] ) <br>
	 * 
	 * 
	 * @param seq1
	 * @param seq2
	 * @param len1
	 * @param len2
	 * @param costArr
	 * @return
	 */

	public int findLCSLength(char[] seq1, char[] seq2, int len1, int len2, int[][] costArr) {

		if (len1 == -1 || len2 == -1) {
			return 0;
		}
		if (costArr[len1][len2] > -1) {
			return costArr[len1][len2];
		}
		if (seq1[len1] == seq2[len2]) {
			costArr[len1][len2] = 1 + findLCSLength(seq1, seq2, len1 - 1, len2 - 1, costArr);
		} else {
			costArr[len1][len2] = Math.max(findLCSLength(seq1, seq2, len1 - 1, len2, costArr),
					findLCSLength(seq1, seq2, len1, len2 - 1, costArr));
		}
		return costArr[len1][len2];
	}

	private void createCostArr(int[][] costArr) {

		for (int i = 0; i < costArr.length; i++) {
			for (int j = 0; j < costArr[0].length; j++) {
				costArr[i][j] = -1;
			}
		}
	}

	public static void main(String args[]) {

		LongestCommonSubsequence lcs = new LongestCommonSubsequence();
		char[] seq1 = { 'A', 'C', 'B' };
		char[] seq2 = { 'A', 'D', ' ' };
		int[][] costArr = new int[seq1.length][seq2.length];
		lcs.createCostArr(costArr);
		int lcsLength = lcs.findLCSLength(seq1, seq2, seq1.length - 1, seq2.length - 1, costArr);
		System.out.println(lcsLength);
	}
}