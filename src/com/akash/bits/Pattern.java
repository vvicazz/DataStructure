package com.akash.bits;

public class Pattern {

	public static void main(String args[]) {
		printPattern1(4);
		printPattern2(5);
		floydTriangle(5);
	}

	
	/**

	for n=4

	   A
	  AAA
	 AAAAA
	AAAAAAA
	 AAAAA
	  AAA
	   A

	 */
	static void printPattern1(int n) {
		for (int rows = 1; rows <= 2 * n - 1; rows++) {
			// for spaces
			int spaceCount = n - rows;
			if (spaceCount < 0) {
				spaceCount = spaceCount * -1;
			}
			// for stars
			int starCount = (rows <= n) ? (2 * rows - 1)
					: (4 * n - 2 * rows - 1);
			for (int i = 0; i < spaceCount; i++) {
				System.out.print(" ");
			}
			for (int i = 0; i < starCount; i++) {
				System.out.print("A");
			}
			System.out.println();
		}
	}
	
	/**
	 n=5
	 
	 1
	 22
	 333
	 4444
	 55555
	 
	 * @param n
	 */
	private static void printPattern2(int n) {
		for(int i = 1; i <= n; i++) {
			for(int j = 1; j <= i; j++) {
				System.out.print(i);
			}
			System.out.println();
		}
	}
	
	/**
	 	n=5
	 
		1
		2	3
		4	5	6
		7	8	9	10
		11	12	13	14	15

	  
	 * @param n
	 */
	private static void floydTriangle(int n) {
		
	}
	
	/**
	 	n=5
	 	
	      1
	     1 1
	    1 2 1
	   1 3 3 1
	  1 4 6 4 1
	 1 5 10 10 5 1
	 
	 * @param n
	 */
	private static void pascalTriangle(int n) {
		
	}
}