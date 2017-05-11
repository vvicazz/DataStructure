package com.akash.array;

// get max sum without including any adjacent elements
public class MaximumSum {

	public static void main(String args[]) {
		int arr[] = { -2, -3, 4, -1, -2, 1, 4, -3 };
		System.out.println(findMaxSum(arr));
		System.out.println(maxSubArraySum(arr));
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
}