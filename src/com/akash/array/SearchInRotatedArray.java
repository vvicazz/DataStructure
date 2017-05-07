package com.akash.array;

public class SearchInRotatedArray {

	public static void main(String args[]) {
		int arr[] = { 6, 7, 1, 2, 3, 4, 5 };
		int key = 5;
		int searchedIndex = -1;
		int pivot = findPivot(arr, 0, arr.length - 1);
		if (pivot == -1) {
			searchedIndex = binarySearch(arr, 0, arr.length - 1, key);
		} else {
			if (arr[arr.length - 1] >= key) {
				searchedIndex = binarySearch(arr, pivot, arr.length - 1, key);
			} else {
				searchedIndex = binarySearch(arr, 0, pivot - 1, key);
			}
		}
		System.out.println("searched Index : " + searchedIndex);
	}

	private static int binarySearch(int arr[], int low, int high, int key) {
		if (low <= high) {
			int mid = low + (high - low) / 2;
			if (arr[mid] == key) {
				return mid;
			} else if (arr[mid] > key) {
				return binarySearch(arr, low, mid - 1, key);
			} else {
				return binarySearch(arr, mid + 1, high, key);
			}
		}
		return -1;
	}

	private static int findPivot(int arr[], int left, int right) {
		if (left > right) {
			return -1;
		}
		int mid = left + (right - left) / 2;
		if (arr[mid] > arr[mid + 1]) {
			return mid + 1;
		} else if (arr[mid - 1] > arr[mid]) {
			return mid;
		}
		if (arr[left] > arr[mid]) {
			return findPivot(arr, left, mid - 1);
		}
		return findPivot(arr, mid + 1, right);
	}
}