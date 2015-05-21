package com.akash.recursion;

import java.util.Arrays;

public class PrintAnagram {

	public static void main(String args[]) {
		char input[] = {'A','B','C','D'};
		int fixedLength = 0;
		int totalLength = input.length;
		printOutput(input, fixedLength, totalLength);
	}

	private static void printOutput(char[] input, int fixedLength,
			int totalLength) {

		if (fixedLength == totalLength-1) {
			System.out.println(Arrays.toString(input));
			return;
		}
		for (int counter = fixedLength; counter < totalLength; counter++) {
			char arr[] = input.clone();
			char temp = arr[counter];
			arr[counter] = arr[fixedLength];
			arr[fixedLength] = temp;
			printOutput(arr, fixedLength + 1, totalLength);
		}
	}
}