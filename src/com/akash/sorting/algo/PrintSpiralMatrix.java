package com.akash.sorting.algo;

import java.util.Arrays;

public class PrintSpiralMatrix
{
	public static void main(String args[])
	{
		printSpiralMatrix(8,8);
	}
	
	private static void printSpiralMatrix(int rows,int columns)
	{
		String arr[][]=new String[rows][columns];
		int k=0,m=0;
		while(k<rows/2 && m<columns/2)
		{
			for(int i=k;i<columns-k;i++)
			{
				if(k%2==0)	arr[k][i]="X";
				else		arr[k][i]="O";
			}
			printArr(arr);
			for(int i=m;i<rows-m;i++)
			{
				if(m%2==0)	arr[i][columns-m-1]="X";
				else		arr[i][columns-m-1]="O";
			}
			printArr(arr);
			for(int i=columns-k-1;i>=k;i--)
			{
				if(k%2==0)	arr[rows-k-1][i]="X";
				else		arr[rows-k-1][i]="O";
			}
			printArr(arr);
			for(int i=rows-m-1;i>=m;i--)
			{
				if(m%2==0)	arr[i][m]="X";
				else		arr[i][m]="O";
			}
			printArr(arr);
			k++;
			m++;
		}
	}
	
	private static void printArr(String arr[][])
	{
		for(int i=0;i<arr.length;i++)
		{
			System.out.println(Arrays.toString(arr[i])+" ");
		}
		System.out.println("<------------------>");
	}
}