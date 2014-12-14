package com.akash.sorting.algo;

import java.util.Arrays;

public class QuickSort {

//	6,5,3,1,8,7,2,4
//	8,3,6,4,2,11,1,5
//	3,7,8,5,2,1,9,5,4
	
	/**
	 *  time = O(n)
	 * 
	 *	pivot = 5
	 *
	 *	(8)		3		6		4		2		11		1		(5)
	 *
	 *	1		3		(6)		4		2		11		(5)		8
	 *
	 *	1		3		(11)	4		2		(5)		6		8
	 *
	 *	1		3		2		4		(5)		11		6		8
	 *
	 *
	 *	1		3		2		4
	 *
	 *	11		6		8
	 */
	
	private int arr[]={3,7,8,5,2,1,9,5,4};
	
	public static void main(String[] args) {
		QuickSort qs = new QuickSort();
    	System.out.println(Arrays.toString(qs.arr));
    	qs.quickSort(0, qs.arr.length-1);
    	System.out.println(Arrays.toString(qs.arr));
	}
	
	private void quickSort(int low, int high) {
		if(low < high) {
			int p = partition(low, high);
			quickSort(low, p-1);
			quickSort(p+1, high);
		}
	}
	
	private int partition(int low, int high) {
		int pivotIndex = high;
		for(int i=low; i<pivotIndex; ) {
			
			if(arr[i] > arr[pivotIndex]) {
				int temp = arr[i];
				arr[i] = arr[pivotIndex-1];
				arr[pivotIndex-1] = arr[pivotIndex];
				arr[pivotIndex] = temp;
				pivotIndex--;
			}
			
			
			else {
				i++;
			}
		}
		return pivotIndex;
	}
}