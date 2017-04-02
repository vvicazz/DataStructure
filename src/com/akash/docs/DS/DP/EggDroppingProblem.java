package com.akash.docs.DS.DP;

public class EggDroppingProblem {

	public static void main(String args[]) {
		int worstRunningCases = recursiveEdp(2,10);
		System.out.println(worstRunningCases);
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

	static int max(int a, int b) {
		return (a > b) ? a : b;
	}
}