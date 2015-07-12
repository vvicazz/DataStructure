package com.akash.sorting.algo;

import java.util.Arrays;

public class CountingSort {

	public static void main(String args[]) {

		CountingSort cs = new CountingSort();
		int arrA[] = new int[] { 2, 5, 3, 1, 2, 3, 1, 3 };
		int arrB[] = new int[arrA.length];
		cs.countSort(arrA, arrB, 6);
		System.out.println(Arrays.toString(arrB));
	}

	// Considering that arrC will include numbers from 0 to k-1
	public void countSort(int arrA[], int arrB[], int k) {

		int arrC[] = new int[k];
		for (int i = 0; i < arrA.length; i++) {
			arrC[arrA[i]]++;
		}
		for (int i = 1; i < arrC.length; i++) {
			arrC[i] = arrC[i] + arrC[i - 1];
		}
		for (int i = arrB.length - 1; i >= 0; i--) {
			arrB[arrC[arrA[i]] - 1] = arrA[i];
			arrC[arrA[i]]--;
		}
	}
}


/*
(1)NOTE : It is a stable sort.


(2)
value at C[i] denotes total no. of elemets less than equal to i .
eg: 
C[2] = 4 , there are 4 elements between 0 and 2 .


(3)
Some algorithms share an interesting property: the sorted order they determine
is based only on comparisons between the input elements. We call such sorting
algorithms comparison sorts.


 */

