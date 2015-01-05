package com.akash.sorting.algo;

import java.util.Arrays;

public class HeapSort {
	
	public static void main(String[] args) {
		int arr[]={3,7,8,5,2,1,9,5,4};
    	System.out.println(Arrays.toString(arr));
	}
}

/**
* first create a heap in array implementation (either max or min heap)
* a[n] <= a[(n-1)/2]  --  Max heap
* a[n] >= a[(n-1)/2]  --  Min heap 
* 
* 
* 	Max heap
* 
* 	3		(7)		8		5		2		1		9		5		4
* 	7		3		(8)		5		2		1		9		5		4
* 	8		3		7		(5)		2		1		9		5		4
* 	8		5		7		3		(2)		1		9		5		4
* 	8		5		7		3		2		(1)		9		5		4
* 	8		5		7		3		2		1		(9)		5		4
* 	9		5		8		3		2		1		7		(5)		4
* 	9		5		8		5		2		1		7		3		(4)
* 	9		5		8		5		2		1		7		3		4
* 
* 
* delete element from heap and replace it with last element in heap and then balance the heap
* ie. replace 9 and 4.
* 
* 	(9)		5		8		5		2		1		7		3		(4)
* 	(4)		5		(8)		5		2		1		7		3		9
* 	8		5		(4)		5		2		1		(7)		3		9
* 	8		5		7		5		2		1		4		3		9
* 
* 	replace 8 and 3
* 
* 	(8)		5		7		5		2		1		4		(3)		9
* 	(3)		5		(7)		5		2		1		4		8		9
* 	7		5		(3)		5		2		1		(4)		8		9
*	7		5		4		5		2		1		3		8		9
* 
* 
* 	worst case complexity = N*(logN)
* 	each insertion in heap(min or max) takes logN comparisons at max
* 	each deletion from heap(min or max) takes logN comparisons at max
* 
*/