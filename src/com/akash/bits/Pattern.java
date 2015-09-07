package com.akash.bits;

public class Pattern {

	public static void main(String args[]) {
		printPattern1(4);
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
}