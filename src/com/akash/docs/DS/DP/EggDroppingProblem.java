package com.akash.docs.DS.DP;

public class EggDroppingProblem {

	public static void main(String args[]) {
		int worstRunningCases = recursiveEdp(2, 10);
		System.out.println(worstRunningCases);
		System.out.println(eggDoppingProblemMemoized(2, 10));
	}

	static int recursiveEdp(int eggs, int floor) {

		if (floor == 0 || floor == 1) {
			return floor;
		}
		if (eggs == 1) {
			return floor;
		}
		int result = Integer.MAX_VALUE;
		int tempResult = result;
		for (int counter = 1; counter <= floor; counter++) {
			tempResult = max(recursiveEdp(eggs - 1, counter - 1), recursiveEdp(eggs, floor - counter));
			if (tempResult < result) {
				result = tempResult;
			}
		}
		return result + 1;
	}

	private static int eggDoppingProblemMemoized(int eggs, int floors) {
		int memo[][] = new int[eggs+1][floors+1];
		for(int i=0; i<=floors; i++) {
			memo[0][i] = 0;
			memo[1][i] = i;
		}
		for(int i=0; i<=eggs; i++) {
			memo[i][0] = 0;
			memo[i][1] = 1;
		}
		int tempCost;
		for(int i=2; i<=eggs; i++) {
			for(int j=2; j<=floors; j++) {
				memo[i][j] = Integer.MAX_VALUE;
				for(int k=1; k<=j; k++) {
					tempCost = max(memo[i-1][k-1] , memo[i][j-k]) + 1;
					if(tempCost < memo[i][j]) {
						memo[i][j] = tempCost;
					}
				}
			}
		}
		print2DArray(memo);
		return memo[eggs][floors];
	}

	private static void print2DArray(int memo[][]) {
		for (int i = 0; i < memo.length; i++) {
			for (int j = 0; j < memo[0].length; j++) {
				System.out.print(memo[i][j] + " ");
			}
			System.out.println();
		}
	}

	static int max(int a, int b) {
		return (a > b) ? a : b;
	}
}