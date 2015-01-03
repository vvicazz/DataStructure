package com.akash.array;

/**
 * arr[] = {1, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1} <br>
 * Index 9
 * 
 * arr[] = {1, 1, 1, 1, 0} <br>
 * Index 4
 * 
 * My algo takes O(n) time complexity with constant space.Both index of zero and
 * number of 1's are calculated. <br>
 *
 * Logic => Whenever a zero comes in the array, replace it to 1 and then count
 * the maximum consecutive ones that can be formed. <br>
 * The first zero is taken as separate case.
 * 
 */
public class FindIndexZeroReplacedOneGetLongestContinuousSequenceOnesBinaryArray {

	public static void main(String[] args) {
		// int arr[] = new int[] { 1, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1};
		int arr[] = new int[] { 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 };
		getIndexForZero(arr);
	}

	private static int getIndexForZero(int arr[]) {

		int maxLengthOfOne = 0;
		int positionOfZeroForMaxOne = -1;

		int positionOfPreviousZero = -1;
		int numOfConsecutiveOnesTillNow = 0;
		boolean isFirstZeroOccured = false;
		boolean isPreviousCharOne = false;

		for (int counter = 0; counter < arr.length; counter++) {
			if (arr[counter] == 0) {

				// first zero in the array
				if (!isFirstZeroOccured) {
					numOfConsecutiveOnesTillNow++;
					isFirstZeroOccured = true;
				}

				// zeros come after the first zero
				// case 1 => ...,1,1,0,(0)
				// case 2 => ...,1,0,1,1,(0)
				// In both cases,we have to break the counting of ones and
				// consider current zero as one
				else {
					if (maxLengthOfOne < numOfConsecutiveOnesTillNow) {
						maxLengthOfOne = numOfConsecutiveOnesTillNow;
						positionOfZeroForMaxOne = positionOfPreviousZero;
					}

					// case 2
					if (isPreviousCharOne) {
						numOfConsecutiveOnesTillNow = counter
								- positionOfPreviousZero;
					}

					// case 1
					else {
						numOfConsecutiveOnesTillNow = 1;
					}
				}
				isPreviousCharOne = false;
				positionOfPreviousZero = counter;
			} else {
				numOfConsecutiveOnesTillNow++;
				isPreviousCharOne = true;
			}
		}

		// if last character in array is one OR first zero is at last index
		if (isPreviousCharOne
				|| (positionOfPreviousZero >= 0 && positionOfZeroForMaxOne == -1)) {
			if (maxLengthOfOne < numOfConsecutiveOnesTillNow) {
				maxLengthOfOne = numOfConsecutiveOnesTillNow;
				positionOfZeroForMaxOne = positionOfPreviousZero;
			}
		}

		System.out.println("index of zero to replace="
				+ positionOfZeroForMaxOne + " with max length="
				+ maxLengthOfOne);
		return positionOfZeroForMaxOne;
	}
}