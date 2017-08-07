package com.akash.docs.DS.DP;

import java.util.Stack;

public class MaxHistogramArea {

	// https://www.youtube.com/watch?v=ZmnqCZp9bBs
	// https://www.youtube.com/watch?v=g8bSdXCG-lA
	// https://www.youtube.com/watch?v=UzeL2GcLx3Y
	public static void main(String args[]) {
		int hist[] = { 6, 2, 5, 4, 5, 1, 6 };
		System.out.println("Maximum area is " + maxHistogramArea(hist));
	}

	public static int maxHistogramArea(int arr[]) {
		Stack<Integer> stack = new Stack<Integer>();
		stack.push(0);
		int area = 0;
		int maxArea = 0;
		for (int i = 1; i < arr.length; i++) {
			if (stack.isEmpty() || arr[stack.peek()] <= arr[i]) {
				stack.push(i);
			} else {
				while (!(stack.isEmpty() || arr[stack.peek()] < arr[i])) {
					int temp = arr[stack.pop()];
					if (stack.size() == 0) {
						area = i * temp;
					} else {
						area = temp * (i - stack.peek() - 1);
					}
					if (area > maxArea) {
						maxArea = area;
					}
				}
				stack.push(i);
			}
		}
		return maxArea;
	}
}

/**
 * 1. if array element is larger than stack top, push index of array element on
 * stack 2. if array element is smaller than stack top, pop element from stack
 * until a smaller or equal element comes in stack or stack is empty 3. for
 * every pop operation, calculate area if(stack.isEmpty()) { area = input[top] *
 * i; } else { area = input[top] * (i-stackTop-1) }
 */
