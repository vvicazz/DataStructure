package com.akash.sorting.algo;

public class SmallestSubArrWithSumGreaterThanVal
{
	public static void main(String args[])
	{
		int arr[]={1,4,5,6,0,19};
		int x=51;
		smallestSubArrWithSumGreaterThanVal(arr, x);
	}
	
	private static int[] smallestSubArrWithSumGreaterThanVal(int arr[], int x)
	{
		boolean substringFound=false;
		int i=0,j=1,minSubstringStartPoint=0, length=arr.length;
		int minLengthOfSubstring=length;
		while(i<length-1 && j<length)
		{
			if(getSumOfSubstring(arr,i,j)>x)
			{
				if(j-i<minLengthOfSubstring )
				{
					substringFound=true;
					minLengthOfSubstring=j-i+1;
					minSubstringStartPoint=i;
				}
				i++;
			}
			else
				j++;
		}
		if(!substringFound)
			minLengthOfSubstring=-1;
		int resultArr[]={minLengthOfSubstring,minSubstringStartPoint};
		return resultArr;
	}
	
	private static int getSumOfSubstring(int arr[], int startPos, int endPos)
	{
		int sum=0;
		for(int i=startPos;i<=endPos;i++)
			sum=sum+arr[i];
		return sum;
	}
}