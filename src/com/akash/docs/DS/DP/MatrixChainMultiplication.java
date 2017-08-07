package com.akash.docs.DS.DP;

/**
 * 	Find min cost of n matrices multiplication.	<br>
 * 
 *	{40, 20, 30, 10, 30}						<br>
 *	(40x20), (20x30), (30x10), (10x30)			<br>
 * 		A		B		C		 D				<br>
 *	
 *	Sub-problems : A(BCD) , (AB)(CD) , (ABC)D	<br>
 * 	
 * 	n elements in array		<br>
 * (n-1) matrices			<br>
 * (n-2) sub problems		<br>
 * 
 * base case : no recursion is required for array of length 2.	<br>
 */
public class MatrixChainMultiplication {

	public static void main(String args[]) {
		int arr[] = { 40, 20, 30, 10, 30 };
		int minCost = matrixChainRecursive(arr, 0, arr.length - 1);
		System.out.println(minCost);
	}

	private static int matrixChainRecursive(int arr[], int start, int end) {
		if (start + 1 == end) {
			return 0;
		}
		int minCost = Integer.MAX_VALUE;
		for (int k = start + 1; k < end; k++) {
			int tempCost = matrixChainRecursive(arr, start, k) + matrixChainRecursive(arr, k, end) + arr[start]
					* arr[k] * arr[end];
			if (minCost > tempCost) {
				minCost = tempCost;
			}
		}
		return minCost;
	}
}
