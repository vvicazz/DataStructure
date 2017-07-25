package com.akash.bits;

public class BitOperation {

	public static void main(String args[]) { 
		
		//get negative of a number:
		//1. find its one's complement by reversing all digits
		//2. find its two's complement by adding 1 to it.
		
		int x=-1;
		System.out.println(Integer.toBinaryString(x<<5));
		System.out.println(x<<5);	//-1
		System.out.println(x<<31);	//-2147483648
		System.out.println(x<<33);	//-1
		//left shift do not hold previous rotated bit from right end to left end
		//right shift holds previous rotated bit from left end to right end
		
		int y=1;
		System.out.println(y>>1); 	//0
		System.out.println(y>>3); 	//0
		System.out.println(y<<30);	//1073741824
		System.out.println(Integer.toBinaryString(y<<30));	 	//1000000000000000000000000000000
		System.out.println(Integer.toBinaryString(2147483647)); 	//1111111111111111111111111111111	2^32-1
		System.out.println(y<<31);	//-2147483648
		System.out.println(y<<32);	//1
		
		//triple right shift operator has advantage only for negative numbers
		int z=-1;
		System.out.println(z>>>1);	//2147483647
		System.out.println(Integer.toBinaryString(z)); 			//11111111111111111111111111111111
		System.out.println(Integer.toBinaryString(-2147483648)); 	//10000000000000000000000000000000
	}
}

/**
 * case 1 :
 * 10111111111111111111111111111111  >> 2 = 11101111111111111111111111111111
 * right shift holds right most bit value
 * hence, it can be used for division for both negative and positive
 * 
 * case 2 :
 * 11111111111111111111111111111111 << 2 = 11111111111111111111111111111100
 * left shift does not hold left most bit value
 * 
 * case 3 :
 * 10111111111111111111111111111111  >>> 2 = 00101111111111111111111111111111
 * triple right shift does not hold right most bit value
 */
