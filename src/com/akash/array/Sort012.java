package com.akash.array;

import java.util.Arrays;

public class Sort012 {

	public static void main(String args[]) {
		int arr[] = { 1, 0, 2, 1, 0, 2, 1, 2, 0, 2, 0 };
		sort012(arr);
		System.out.println(Arrays.toString(arr));
	}

	private static void sort01(int arr[]) {
		int i = -1;
		int j = 0;
		while (j < arr.length) {
			if (arr[j] == 0) {
				i++;
				int temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
			}
			j++;
		}
	}

	private static void sort012(int arr[]) {
		int i = -1;
		int j = 0;
		while (j < arr.length) {
			if (arr[j] == 0) {
				i++;
				int temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
			}
			j++;
		}
		int k = i;
		int l = i + 1;
		while (l < arr.length) {
			if (arr[l] == 1) {
				k++;
				int temp = arr[k];
				arr[k] = arr[l];
				arr[l] = temp;
			}
			l++;
		}
	}
}