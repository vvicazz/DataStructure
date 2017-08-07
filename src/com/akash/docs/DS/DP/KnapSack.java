package com.akash.docs.DS.DP;

public class KnapSack {

	/**
	 * There are two types of KnapSack problems: <br>
	 * Case 1: We can split the weight <br>
	 * Case 2: We cannot split the weight <br>
	 * 
	 * In Case 2, we can use DP <br>
	 * In case 1, we can greedy approach. <br>
	 * By taking val/weight ratio for each and taking highest ratio first. <br>
	 */
	public static void main(String args[]) {
		int weights[] = { 1, 3, 4, 5 };
		int values[] = { 1, 4, 5, 7 };
		int N = 10;
		int maxValue1 = zeroOneKnapSack(weights, values, N);
		int maxValue2 = zeroOneKnapSackUsingDp(weights, values, N);
		System.out.println(maxValue1);
		System.out.println(maxValue2);
	}

	// recursive approach
	private static int zeroOneKnapSack(int weights[], int values[], int N) {
		if (N == 0) {
			return 0;
		}
		int maxValue = 0;
		for (int i = 0; i < weights.length; i++) {
			int tempWeight = N - weights[i];
			if (tempWeight >= 0) {
				maxValue = max(maxValue, zeroOneKnapSack(weights, values, tempWeight) + values[i]);
			}
		}
		return maxValue;
	}

	private static int max(int a, int b) {
		return a > b ? a : b;
	}

	private static int zeroOneKnapSackUsingDp(int weights[], int values[], int N) {
		int memo[][] = new int[values.length + 1][N + 1];
		for (int i = 0; i < memo.length; i++) {
			memo[i][0] = 0;
		}
		for (int i = 0; i <= N; i++) {
			memo[0][i] = 0;
		}
		for (int number = 1; number <= N; number++) {
			for (int j = 1; j <= weights.length; j++) {
				int tempWeight = number - weights[j-1];
				if (tempWeight >= 0) {
					memo[j][number] = max(memo[j][tempWeight] + values[j-1], memo[j - 1][number]);
				} else {
					memo[j][number] = memo[j - 1][number];
				}
			}
		}
		return memo[values.length][N];
	}
}
