package com.akash.array;

public class MaxDiffBwTwoElements {

	private int arr[] = { 2, 3, 10, 6, 100, 8, 1 };

	public static void main(String args[]) {

		MaxDiffBwTwoElements ob = new MaxDiffBwTwoElements();
		System.out.println(ob.findMaxDiff());
		System.out.println(ob.maxSubArraySum(ob.arr));
	}

	/*
	 * time -- O(n) space -- O(1)
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
	 * { -2, -3, 4, -1, -2, 1, 5, -3 }  -- 7
	 * { -2, -3, 4, -1, -20, 1, 5, -3 } -- 6
	 * { -2, -3, 4, -1, -20, 1, 1, -3 } -- 4
	 */
	private int maxSubArraySum(int arr[])
	{
	   int max_so_far = 0, max_ending_here = 0;
	   int i;
	   for(i = 0; i < arr.length; i++)
	   {
	     max_ending_here = max_ending_here + arr[i];
	     if(max_ending_here < 0)
	        max_ending_here = 0;
	     if(max_so_far < max_ending_here)
	        max_so_far = max_ending_here;
	    }
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