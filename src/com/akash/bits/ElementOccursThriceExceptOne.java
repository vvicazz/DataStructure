package com.akash.bits;

import java.util.Arrays;

public class ElementOccursThriceExceptOne {

	private static final int INT_SIZE = 32;

	public static void main(String args[]) {
		int arr[] = { 12, 1, 12, 3, 12, 1, 1, 2, 3, 2, 2, 3, 7 };
		printElementOccursThriceExceptOne(arr);
	}

	private static void printElementOccursThriceExceptOne(int arr[]) {
		int bitsLocation[] = new int[INT_SIZE];
		for (int number : arr) {
			int xorOperand = 1;
			for (int shift = 0; shift < INT_SIZE; shift++) {
				xorOperand = 1 << shift;
				bitsLocation[INT_SIZE - shift - 1] = bitsLocation[INT_SIZE - shift - 1] + (number & xorOperand)
						/ xorOperand;
			}
		}
		for (int shift = 0; shift < INT_SIZE; shift++) {
			bitsLocation[INT_SIZE - shift - 1] = bitsLocation[INT_SIZE - shift - 1] % 3;
		}
		int requiredNumber = 0;
		for (int shift = 0; shift < 32; shift++) {
			if (bitsLocation[INT_SIZE - shift - 1] == 1) {
				requiredNumber = (int) (requiredNumber + Math.pow((double) 2, (double)shift));
			}
		}
		System.out.println(requiredNumber);
	}
}