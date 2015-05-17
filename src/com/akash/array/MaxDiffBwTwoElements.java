package com.akash.array;

import java.util.Calendar;


public class MaxDiffBwTwoElements {

	private int arr[] = { -2, -3, 4, -2, 8, 9, -3 };

	// { 2, 3, 10, 6, 100, 8, 1 }

	public static void main(String args[]) {

		String s = null;
		Integer i;
		Calendar c = Calendar.getInstance();
		c.set(2012, Calendar.APRIL, 12);
		System.out.println(s instanceof Object);
//		MaxDiffBwTwoElements ob = new MaxDiffBwTwoElements();
//		System.out.println(ob.findMaxDiff());
//		System.out.println(ob.maxSubArraySum(ob.arr));
	}

	/*
	 * time -- O(n) space -- O(1) This algo tries to find 1) position of min
	 * number 2) position of max number
	 * 
	 * various ways to solve this algo: (1)use any sorting algo which find
	 * min/max in single iteration heap,selection,bubble.
	 * 1st method is time consuming if array is in descending order. 
	 * (2)get difference of consecutive numbers and save it in an array of (n-1) 
	 * now find max sum subArray for this array (3)Use the below logic
	 */
	private int findMaxDiff() {
		int arr[] = this.arr;
		int minimumNumFound = arr[0];
		int maximumSumFound = arr[1] - arr[0];
		for (int counter = 1; counter < arr.length; counter++) {
			if (arr[counter] - minimumNumFound > maximumSumFound) {
				maximumSumFound = arr[counter] - minimumNumFound;
			}
			if (arr[counter] < minimumNumFound) {
				minimumNumFound = arr[counter];
			}
		}
		return maximumSumFound;
	}

	/*
	 * { -2, -3, 4, -1, -2, 1, 5, -3 }-->7 { -2, -3, 4, -1, -20, 1, 5, -3 }-->6
	 * {-2, -3, 4, -1, -20, 1, 1, -3 }-->4
	 */
	private int maxSubArraySum(int arr[]) {
		int max_so_far = 0, max_ending_here = 0;
		int previous_max_ending_here = 0;
		int minPos = -1;
		int maxPos = -1;
		for (int counter = 0; counter < arr.length; counter++) {
			max_ending_here = max_ending_here + arr[counter];
			if (max_ending_here < 0) {
				max_ending_here = 0;
			}
			if (max_so_far < max_ending_here) {
				if (previous_max_ending_here == 0) {
					minPos = counter;
				} else {
					maxPos = counter;
				}
				max_so_far = max_ending_here;
			}
			previous_max_ending_here = max_ending_here;
		}
		System.out.println("min :" + minPos);
		System.out.println("max :" + maxPos);
		return max_so_far;
	}
}

/*
 * Given an array arr[] of integers, find out the difference between any two
 * elements such that larger element appears after the smaller number in arr[].
 * 
 * input : 2, 3, 10, 6, 4, 8, 1 <=> output : 8 (10-2)
 * 
 * input : 7, 9, 5, 6, 3, 2 <=> output : 2 (9-7)
 * 
 * input : 2, 3, 10, 6, 100, 8, 1 <=> output : 98
 */