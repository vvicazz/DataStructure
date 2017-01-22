package com.akash.docs.DS.DP;

/**
 * Given amount and a list of denominations as input <br>
 * find number of different permutations of denominations to make this sum <br>
 * 
 * http://algorithms.tutorialhorizon.com/dynamic-programming-coin-change-problem
 */
public class CoinChangePermutationCountProblem {

	public static void main(String args[]) {
		int amount = 10;
		int denominations[] = new int[] { 2, 3, 5, 6 };
		waysToCountChangeUsingDP(amount, denominations);
	}

	private static void waysToCountChangeUsingRecursion(int amount, int denominations[]) {
		// TODO
	}

	private static void waysToCountChangeUsingDP(int amount, int denominations[]) {

		int[][] coinSumMatrix = new int[denominations.length + 1][amount + 1];
		// initialize: for amount=0, ways will be 1
		for (int i = 0; i < coinSumMatrix.length; i++) {
			coinSumMatrix[i][0] = 1;
		}
		// initialize: for coin=0, ways will be 0
		for (int i = 1; i <= amount; i++) {
			coinSumMatrix[0][i] = 0;
		}

		// if coin is greater than sum,ignore that coin
		for (int coinRow = 1; coinRow <= denominations.length; coinRow++) {
			int coinValue = denominations[coinRow - 1];
			for (int sum = 1; sum <= amount; sum++) {
				if (sum - coinValue < 0) {
					coinSumMatrix[coinRow][sum] = coinSumMatrix[coinRow - 1][sum];
				} else {
					coinSumMatrix[coinRow][sum] = coinSumMatrix[coinRow - 1][sum]
							+ coinSumMatrix[coinRow][sum - coinValue];
				}
			}
		}
		for (int i = 0; i < coinSumMatrix.length; i++) {
			for (int j = 0; j < coinSumMatrix[i].length; j++) {
				System.out.print(coinSumMatrix[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println(coinSumMatrix[denominations.length][amount]);
	}
}