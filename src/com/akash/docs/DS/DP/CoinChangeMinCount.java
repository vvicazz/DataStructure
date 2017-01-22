package com.akash.docs.DS.DP;

/**
 * Given amount and a list of denominations in increasing order as input <br>
 * find count of minimum number of denominations to make this sum <br>
 * 
 * If V == 0, then 0 coins required. <br>
 * If V > 0 <br>
 * minCoin(coins[0..m-1], V) = min {1 + minCoins(V-coin[i])} <br>
 * where i varies from 0 to m-1 and coin[i] <= V
 */
public class CoinChangeMinCount {

	public static void main(String args[]) {
		int amount = 10;
		int denominations[] = new int[] { 2, 3, 5, 6 };
		int[] memory = new int[amount];
		// int count = minCountChangeUsingRecursion(amount, denominations);
		int count = minCountChangeUsingDP(amount, denominations);
		System.out.println(count);
	}

	private static int minCountChangeUsingRecursion(int amount, int denominations[]) {
		if (amount == 0) {
			return 0;
		}
		int result = Integer.MAX_VALUE;
		for (int coin : denominations) {
			if (amount - coin >= 0) {
				int subResult = minCountChangeUsingRecursion(amount - coin, denominations);
				if (subResult != Integer.MAX_VALUE && subResult + 1 < result) {
					result = subResult + 1;
				}
			}
		}
		return result;
	}

	private static int minCountChangeUsingDP(int amount, int denominations[]) {
		int table[] = new int[amount + 1];
		table[0] = 0;
		for (int sum = 1; sum <= amount; sum++) {
			table[sum] = Integer.MAX_VALUE;
		}
		for (int sum = 1; sum <= amount; sum++) {
			for (int coinIndex = 0; coinIndex < denominations.length; coinIndex++) {
				if (sum - denominations[coinIndex] >= 0) {
					int tempResult = table[sum - denominations[coinIndex]];
					if (tempResult != Integer.MAX_VALUE && tempResult + 1 < table[sum]) {
						table[sum] = tempResult + 1;
					}
				}
			}
		}
		return table[amount];
	}
}