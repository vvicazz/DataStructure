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
		int minCost1 = matrixChainRecursive(arr, 0, arr.length - 1);
		int minCost2 = matrixChainDp(arr);
		System.out.println(minCost1);
		System.out.println(minCost2);
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
	
	private static int matrixChainDp(int arr[]) {
		int memo[][] = new int[arr.length][arr.length];
		int arrayBreakSol[][] = new int[arr.length][arr.length];
		for (int i = 0; i < memo.length; i++) {
			memo[i][i] = 0;
		}
		for (int i = 0; i < memo.length - 1; i++) {
			memo[i][i + 1] = 0;
		}
		int tempCost;
		for (int i = 2; i < memo.length; i++) {
			for (int j = 0; j + i < memo.length; j++) {
				int minCost = Integer.MAX_VALUE;
				for (int k = j + 1; k < j + i; k++) {
					tempCost = memo[j][k] + memo[k][j + i] + arr[j] * arr[k] * arr[j + i];
					if (minCost > tempCost) {
						minCost = tempCost;
						arrayBreakSol[j][j + i] = k;
					}
				}
				memo[j][j + i] = minCost;
			}
		}
		return memo[0][memo.length - 1];
	}
}