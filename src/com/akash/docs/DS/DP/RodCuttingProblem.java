package com.akash.docs.DS.DP;

/**
 * Let cutRoad(n) be the required (best possible price) value for a rod of
 * length n. cutRod(n) can be written as following. <br>
 * cutRod(n) = max(price[i] + cutRod(n-i-1)) for all i in {0, 1 .. n-1}
 *
 */
public class RodCuttingProblem {
	public static void main(String args[]) {
		int[] priceArr = new int[] { 1, 5, 8, 9, 10, 17, 17, 20 };
		int[] cutLengthArr = new int[] { 1, 2, 3, 4, 5, 6, 7, 8 };
		int maxPrice = rcp(8, priceArr, cutLengthArr);
		int memoizedMaxPrice = memoizedRcp(8, priceArr, cutLengthArr);
		System.out.println(memoizedMaxPrice);
	}

	static int rcp(int rodLength, int[] priceArr, int[] cutLengthArr) {
		if (rodLength == 0) {
			return 0;
		}
		int maxPrice = 0;
		for (int counter = 0; counter < cutLengthArr.length; counter++) {
			int tempLength = rodLength - cutLengthArr[counter];
			if (tempLength >= 0) {
				maxPrice = max(rcp(tempLength, priceArr, cutLengthArr) + priceArr[counter], maxPrice);
			}
		}
		return maxPrice;
	}

	static int memoizedRcp(int rodLength, int[] priceArr, int[] cutLengthArr) {
		int rcpCache[] = new int[rodLength + 1];
		for (int counterLength = 0; counterLength <= rodLength; counterLength++) {
			int maxPrice = 0;
			int cutCounterUsed = -1;
			for (int cutCounter = 0; cutCounter < cutLengthArr.length; cutCounter++) {
				int tempLength = counterLength - cutLengthArr[cutCounter];
				if (tempLength >= 0) {
					if (maxPrice < rcpCache[tempLength] + priceArr[cutCounter]) {
						maxPrice = rcpCache[tempLength] + priceArr[cutCounter];
						cutCounterUsed = cutCounter;
					}
				} else {
					break;
				}
			}
			System.out.println("rod of length: " + counterLength + " used cut counter: " + (cutCounterUsed+1));
			rcpCache[counterLength] = maxPrice;
		}
		return rcpCache[rodLength];
	}

	private static int max(int a, int b) {
		return (a > b) ? a : b;
	}
}