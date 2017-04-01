package com.akash.array;

public class RotatedPalimdrome {

	public static void main(String args[]) {

		String content = "RADAR";
		boolean result = isPalimdrome(content.toCharArray(), 0, content.length() - 1);
		System.out.println(result);
		System.out.println(isRotatedPalimdrome("DARRA".toCharArray()));
		System.out.println(longestPalimdromSubstringDp("DARRA".toCharArray()));
	}

	private static boolean isRotatedPalimdrome(char arr[]) {
		char tempArr[] = new char[arr.length * 2];
		for (int i = 0; i < arr.length; i++) {
			tempArr[i] = arr[i];
			tempArr[i + arr.length] = arr[i];
		}
		for (int i = 0; i < tempArr.length / 2; i++) {
			if (isPalimdrome(tempArr, i, i + arr.length)) {
				return true;
			}
		}
		return false;
	}

	private static boolean isPalimdrome(char arr[], int start, int end) {
		boolean isPalimdrome = true;
		for (int i = start; i < (start + end) / 2; i++) {
			if (arr[i] != arr[start + end - i]) {
				isPalimdrome = false;
				break;
			}
		}
		return isPalimdrome;
	}

	static boolean longestPalimdromSubstring(char arr[]) {
		char tempArr[] = new char[arr.length * 2];
		for (int i = 0; i < arr.length; i++) {
			tempArr[i] = arr[i];
			tempArr[i + arr.length] = arr[i];
		}
		int sizeOfLongestPalimdrome = traverse(tempArr, 0, tempArr.length - 1);
		if (sizeOfLongestPalimdrome >= arr.length) {
			return true;
		}
		return false;
	}

	static int traverse(char arr[], int start, int end) {
		if (start == end) {
			return 1;
		} else if (arr[start] != arr[end]) {
			return max(traverse(arr, start + 1, end), traverse(arr, start, end - 1));
		} else if (arr[start] == arr[end] && end == start + 1) {
			return 2;
		} else {
			return traverse(arr, start + 1, end - 1) + 2;
		}
	}

	static boolean longestPalimdromSubstringDp(char arr[]) {
		char tempArr[] = new char[arr.length * 2];
		for (int i = 0; i < arr.length; i++) {
			tempArr[i] = arr[i];
			tempArr[i + arr.length] = arr[i];
		}
		int[][] cache = new int[tempArr.length][tempArr.length];
		for (int i = 0; i < tempArr.length; i++) {
			for (int j = 0; j < tempArr.length; j++) {
				cache[i][j] = -1;
			}
		}
		for (int i = 0; i < tempArr.length; i++) {
			cache[i][i] = 1;
		}
		int sizeOfLongestPalimdrome = traverseDp(tempArr, 0, tempArr.length - 1, cache);
		if (sizeOfLongestPalimdrome >= arr.length) {
			return true;
		}
		return false;
	}

	static int traverseDp(char arr[], int start, int end, int[][] cache) {
		int value = cache[start][end];
		if (value > -1) {
			return value;
		}
		if (arr[start] != arr[end]) {
			cache[start][end] = max(traverse(arr, start + 1, end), traverse(arr, start, end - 1));
		} else if (arr[start] == arr[end] && end == start + 1) {
			cache[start][end] = 2;
		} else {
			cache[start][end] = traverse(arr, start + 1, end - 1) + 2;
		}
		return cache[start][end];
	}

	static int max(int a, int b) {
		return a > b ? a : b;
	}
}