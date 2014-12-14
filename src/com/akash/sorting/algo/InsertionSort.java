package com.akash.sorting.algo;

import java.util.Arrays;

public class InsertionSort
{
//	logic for sorting => Tash ke patte
	
	public static void main(String args[])
	{
		int arr[]={6,5,3,1,8,7,2,4};
    	System.out.println(Arrays.toString(arr));
    	insertionSort(arr);
    	System.out.println(Arrays.toString(arr));
	}
	
	static void insertionSort(int arr[])
    {
    	int arrLength=arr.length;
    	for(int i=1;i<arrLength;i++)
    	{
    		int counterVar=arr[i];
    		int counterPos=i;
    		for(int j=i-1;j>=0;j--)
    		{
    			if(counterVar < arr[j])
    			{
    				arr[j+1]=arr[j];
    				counterPos=j;
    			}
    		}
    		arr[counterPos]=counterVar;
    	}
    }
}