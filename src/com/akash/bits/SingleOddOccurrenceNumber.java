package com.akash.bits;

public class SingleOddOccurrenceNumber {

	static int getOddOccurrence(int ar[]) {
		int i;
		int res = 0;
		for (i = 0; i < ar.length; i++) {
			System.out.println(res);
			res = res ^ ar[i];
		}
		return res;
	}

	public static void main(String args[]) {
		//int ar[] = { 2, 3, 5, 4, 5, 2, 4, 3, 5, 2, 4, 4, 2 };
		int ar[] = {5,5,5,2,2};
		System.out.println(~0+2);
		System.out.println(getOddOccurrence(ar));
	}
}


/*
(Q)	XOR property
	0^n=n
	n^n=0
(Q)if in a list of numbers n1,n2,n3,n4,n4...
 	all numbers have even occurense except one,then to find that number
	n1 ^ n2 ^ n3 ^ n4
(Q)reverse sign of a number	eg: in=-10 , out=10 
 	~n+1
 
(Q) return 1 if 0, return 0 if 1
->	1-n
->	1^n
->	~n+1+1
*/