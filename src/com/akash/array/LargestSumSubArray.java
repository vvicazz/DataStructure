package com.akash.array;

public class LargestSumSubArray {
	public static void main(String[] args) {
		int[] arr = new int[] { -2, -3, 4, -1, -2, 1, 5, -3 };
		System.out.println(largestSumSubArray(arr));
	}

	static int largestSumSubArray(int arr[]) {
		int max_ending_here = 0, max_so_far = 0;
		for (int counter = 0; counter < arr.length; counter++) {
			max_ending_here = max_ending_here + arr[counter];
			if (max_ending_here < 0) {
				max_ending_here = 0;
			}
			if (max_ending_here > max_so_far) {
				max_so_far = max_ending_here;
			}
		}
		return max_so_far;
	}
}