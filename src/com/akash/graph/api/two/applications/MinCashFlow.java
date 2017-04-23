package com.akash.graph.api.two.applications;

public class MinCashFlow {

	/**
	 * P1 -> p2 : +ve, p1 has to give p2 <br>
	 * P1 -> p2 : -ve, p1 will get from p2 <br>
	 * 
	 * { { 0, 1000, 2000 }, { 0, 0, 5000 }, { 0, 0, 0 } } <br>
	 * 
	 * outstandingAmount = [3000, 4000, -7000] <br>
	 * 
	 */
	public static void main(String args[]) {
		int[][] amountTransferGraph = { { 0, 1000, 2000 }, { 0, 0, 5000 }, { 0, 0, 0 } };
		minCashFlow(amountTransferGraph);
	}

	static void minCashFlow(int[][] amountTransferGraph) {
		int outstandingAmount[] = new int[amountTransferGraph.length];
		for (int personNum = 0; personNum < amountTransferGraph.length; personNum++) {
			for (int otherPerson = 0; otherPerson < amountTransferGraph[personNum].length; otherPerson++) {
				outstandingAmount[personNum] += amountTransferGraph[personNum][otherPerson]
						- amountTransferGraph[otherPerson][personNum];
			}
		}
		minCashFlowRecursive(outstandingAmount);
	}

	static void minCashFlowRecursive(int[] outstandingAmount) {

		int minValueIndex = getMinValueIndex(outstandingAmount);
		int maxValueIndex = getMaxValueIndex(outstandingAmount);
		if (outstandingAmount[minValueIndex] == 0 && outstandingAmount[maxValueIndex] == 0) {
			return;
		}
		int min = min(outstandingAmount[maxValueIndex], -outstandingAmount[minValueIndex]);
		outstandingAmount[maxValueIndex] = outstandingAmount[maxValueIndex] - min;
		outstandingAmount[minValueIndex] = outstandingAmount[minValueIndex] + min;
		System.out.println("P" + maxValueIndex + " will pay " + min + " to P" + minValueIndex);
		minCashFlowRecursive(outstandingAmount);
	}

	private static int getMinValueIndex(int[] outstandingAmount) {
		int minIndex = 0;
		for (int i = 1; i < outstandingAmount.length; i++) {
			if (outstandingAmount[i] < outstandingAmount[minIndex]) {
				minIndex = i;
			}
		}
		return minIndex;
	}

	private static int getMaxValueIndex(int[] outstandingAmount) {
		int maxIndex = 0;
		for (int i = 1; i < outstandingAmount.length; i++) {
			if (outstandingAmount[i] > outstandingAmount[maxIndex]) {
				maxIndex = i;
			}
		}
		return maxIndex;
	}

	static int min(int a, int b) {
		return a > b ? b : a;
	}
}