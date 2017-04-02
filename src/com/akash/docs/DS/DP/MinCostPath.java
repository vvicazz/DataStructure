package com.akash.docs.DS.DP;

public class MinCostPath {

	public static void main(String args[]) {
		int cost[][] = { { 1, 2, 3 }, { 4, 8, 2 }, { 1, 5, 3 } };
		int result = mcp(cost, 2, 2);
		System.out.println(result);
	}

	public static int mcp(int cost[][], int row, int col) {
		if (row < 0 || col < 0) {
			return 0;
		}
		if (row == 0 && col == 0) {
			return cost[row][col];
		}
		return min(mcp(cost, row - 1, col), mcp(cost, row, col - 1), mcp(cost, row - 1, col - 1)) + cost[row][col];
	}

	private static int min(int a, int b, int c) {
		if (a > b) {
			if (b > c)
				return c;
			else
				return b;
		} else {
			if (a > c)
				return c;
			else
				return a;
		}
	}
}