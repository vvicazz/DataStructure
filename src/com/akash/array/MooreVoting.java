package com.akash.array;

public class MooreVoting {

	public static void main(String[] args) {

		int[] arr = new int[] { 4, 4, 4, 4, 3, 3, 3, 3, 3 };
		int index = majorityNumberIndex(arr);
		System.out.println(arr[index]);
	}

	static int majorityNumberIndex(int arr[]) {
		int max_value_count = 1;
		int max_value_index = 0;
		for (int counter = 1; counter < arr.length; counter++) {
			if (arr[counter] == arr[max_value_index]) {
				max_value_count++;
			} else {
				max_value_count--;
			}
			if (max_value_count == 0) {
				max_value_index = counter;
				max_value_count = 1;
			}
		}
		if (isMajority(arr, max_value_index)) {
			return max_value_index;
		} else {
			return -1;
		}
	}

	static boolean isMajority(int[] arr, int maxIndex) {
		int count = 0;
		for (int counter = 0; counter < arr.length; counter++) {
			if (arr[counter] == arr[maxIndex]) {
				count++;
			}
		}
		if (count > arr.length / 2) {
			return true;
		} else {
			return false;
		}
	}
}