package com.akash.array;

public class FirstRepeatElementInArray {

	public static void main(String args[]) {
		System.out.println(find("ABBXAB"));
	}

	public static char find(String str) {

		int count[] = new int[26];
		for (int i = 0; i < 26; i++) {
			count[i] = -1;
		}
		char tempChar = 0;
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			if (count[ch - 65] > -1) {
				if (tempChar == 0) {
					tempChar = ch;
				} else if (count[ch - 65] < count[tempChar-65]) {
					tempChar = ch;
				}
			} else {
				count[ch - 65] = i;
			}
		}
		return tempChar;
	}
}