package com.akash.array;

// get max sum without including any adjacent elements
public class MaximumSum {

	public static void main(String args[]) {
		int arr[] = { -2, -3, 4, -1, -2, 1, 4, -3 };
		System.out.println(findMaxSum(arr));
		System.out.println(maxSubArraySum(arr));
		int productArr[] = { 1, 3, -5, 4, 2, 1 };
		System.out.println("--------");
		System.out.println(maxSubArrayProduct(productArr));
	}

	// original
	private static int findMaxSum(int arr[]) {
		int maxEndingHere = arr[0];
		int maxSoFar = arr[0];
		for (int i = 1; i < arr.length; i++) {
			maxEndingHere = max(arr[i], arr[i] + maxEndingHere);
			maxSoFar = max(maxSoFar, maxEndingHere);
		}
		return maxSoFar;
	}

	// modified
	private static int maxSubArraySum(int arr[]) {
		int maxSoFar = arr[0];
		int maxEndingHere = arr[0];
		int start = 0, end = 0, tempStart = 0;
		for (int i = 1; i < arr.length; i++) {
			maxEndingHere = maxEndingHere + arr[i];
			if (maxEndingHere < 0) {
				maxEndingHere = 0;
				tempStart = i + 1;
			}
			if (maxSoFar < maxEndingHere) {
				maxSoFar = maxEndingHere;
				end = i;
				start = tempStart;
			}
		}
		System.out.println("start:" + start);
		System.out.println("end:" + end);
		return maxSoFar;
	}

	private static int max(int a, int b) {
		return a > b ? a : b;
	}

	private static int max(int a, int b, int c) {
		return a > b ? (a > c ? a : c) : (b > c ? b : c);
	}

	private static int maxSubArrayProduct(int arr[]) {
		int maxEndingHerePos = arr[0];
		int maxEndingHereNeg = Integer.MIN_VALUE;
		int maxTillNow = arr[0];
		int temp1, temp2;
		for (int i = 1; i < arr.length; i++) {

			if (arr[i] == 0) {
				maxEndingHerePos = 0;
				maxEndingHereNeg = 0;
				continue;
			}
			temp1 = maxEndingHerePos * arr[i];
			if (maxEndingHereNeg != Integer.MIN_VALUE) {
				temp2 = maxEndingHereNeg * arr[i];
				maxEndingHerePos = max(temp1, temp2, arr[i]);
				maxEndingHereNeg = max(-temp1, -temp2, -arr[i]);
			} else if (maxEndingHerePos * arr[i] < 0) {
				maxEndingHereNeg = maxEndingHerePos * arr[i];
				maxEndingHerePos = arr[i];
			} else if (arr[i] <= maxEndingHerePos * arr[i]) {
				maxEndingHerePos = maxEndingHerePos * arr[i];
			}
			if (maxTillNow < maxEndingHerePos) {
				maxTillNow = maxEndingHerePos;
			}
		}
		return maxTillNow;
	}
	
	/**
	 * arr  : 1		3		-5		4		2		1	<br>
	 * MEHN : -INF	-INF	-15		-60		-120	-8	<br>
	 * MEHP : 1		3		-5		4		8		120	<br>
	 * MTN	: 1		3		3		4		8		120	<br>
	 */
	
	
	
	// 5 3 1 4 2 
	// 5 6 1 4 2 
	public int maxSumSubArrayWithNoConsecutive(int arr[]) {

		int maxTillPre = arr[0];
		int maxTillNow = max(arr[0], arr[1]);
		int max = maxTillNow;
		for (int i = 2; i < arr.length; i++) {
			maxTillPre = maxTillPre + arr[i];
			if (maxTillPre > max) {
				max = maxTillPre;
			}
			// swap
			int temp = maxTillPre;
			maxTillPre = maxTillNow;
			maxTillNow = temp;
		}
		return max;
	}
}