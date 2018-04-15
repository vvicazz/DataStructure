package com.akash.sorting.algo;

import java.util.Arrays;

public class MergeSort {

	private int arr[] = { 6, 5, 1, 6, 8, 7, 2, 4 };
//	private int arr[] = { 100, 20, 13, 84, 55, 27, 81 };

	public static void main(String args[]) {

		MergeSort ms = new MergeSort();
		System.out.println(Arrays.toString(ms.arr));
		//ms.recursiveMergeSort(ms.arr, 0, ms.arr.length - 1);
		ms.iterativeMergeSort(ms.arr);
		System.out.println(Arrays.toString(ms.arr));
	}
	
	private void iterativeMergeSort(int arr[]) {
		int sizeOfArrayToSort = 1;
		int nearestPowerOfTwo = getNearestPowerOfTwo(arr.length);
		while (sizeOfArrayToSort < arr.length) {
			int outerIterationSize = nearestPowerOfTwo / (sizeOfArrayToSort * 2);
			int index = 0;
			while (outerIterationSize > 0) {
				mergeArrayWithBoundaries(arr, sizeOfArrayToSort, index);
				index = index + sizeOfArrayToSort * 2;
				outerIterationSize--;
			}
			sizeOfArrayToSort = sizeOfArrayToSort * 2;
		}
	}

	private void mergeArrayWithBoundaries(int arr[], int sizeOfArrayToSort, int start) {
		int mid = start + sizeOfArrayToSort - 1;
		int end = start + sizeOfArrayToSort * 2 - 1;
		if (end >= arr.length) {
			end = arr.length - 1;
		}
		merge(arr, start, mid, end);
	}

	private int getNearestPowerOfTwo(int num) {
		int counter = 1;
		while (counter < num) {
			counter = counter << 1;
		}
		return counter;
	}

	private void recursiveMergeSort(int arr[], int low, int high) {
		if (low < high) {
			int mid = (high + low) / 2;
			recursiveMergeSort(arr, low, mid);
			recursiveMergeSort(arr, mid + 1, high);
			merge(arr, low, mid, high);
		}
	}

	private void merge(int arr[], int low, int mid, int high) {
		
		int length = high - low + 1;
		int[] tempArr = new int[length];
		int i, j, counter = 0;

		// compare each element in two list
		for (i = low, j = mid + 1; (i <= mid) && (j <= high); ) {
			if (arr[i] < arr[j]) {
				tempArr[counter++] = arr[i++];
			} else {
				tempArr[counter++] = arr[j++];
			}
		}

		// if any list ends for comparison,fill rest of the elements for other
		// list
		while (j <= high) {
			tempArr[counter++] = arr[j++];
		}
		while (i <= mid) {
			tempArr[counter++] = arr[i++];
		}

		// put sorted list in original list
		for (int temp = 0; temp < length; temp++) {
			arr[low + temp] = tempArr[temp];
		}
	}
}


/*
 
complexity : n*log(n)

let number of elements be 8.
m_s   represents  merge_sort()  method.
The indentation shows the stack of recursion.

m_s(0,7)
    m_s(0,3)
         m_s(0,1)
                low=0,high=1,mid=0      merge(0,0,1)
         m_s(2,3)
                low=2,high=3,mid=2      merge(2,2,3)

         low=0,high=3,mid=1             merge(0,1,3)

    m_s(4,7)
         m_s(4,5)
               low=4,high=5,mid=4      merge(4,4,5)
         m_s(6,7)
               low=6,high=7,mid=6      merge(6,6,7)

         low=4,high=7,mid=5            merge(4,5,7)

    low=0,high=7,mid=3                 merge(0,3,7)


eg:
array={8,7,6,5,4,3,2,1}
The part of  underlined  list shows that  part of list is sorted  using  merge()  method.

8 7 6 5 4 3 2 1
 
7 8 6 5 4 3 2 1
---

7 8 5 6 4 3 2 1
    ---

5 6 7 8 4 3 2 1
-------

5 6 7 8 3 4 2 1
        ---

5 6 7 8 3 4 1 2
            ---

5 6 7 8 1 2 3 4
        -------

1 2 3 4 5 6 7 8
---------------

*/
