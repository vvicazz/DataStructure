package com.akash.maths;

public class PrimeNumber {

	public static void main(String[] args) {
		boolean result=checkIfPrime(1457);
		System.out.println(result);
	}
	
	private static boolean checkIfPrime(double num) {
		if(num<=1) {
			return false;
		}
		else if(num==2 || num==3 || num==5) {
			return true;
		}
		double rootOfNum = Math.sqrt(num);
		rootOfNum = Math.round(rootOfNum);
		for(double counter= rootOfNum; counter>=1;counter--) {
			if(checkIfPrime(counter)==true) {
				if(num % counter == 0) {
					return false;
				}
			}
		}
		return false;
	}
}



/**
* For square root of a number
* 
* http://stackoverflow.com/questions/19611198/finding-square-root-without-using-sqrt-function
* http://www.cplusplus.com/reference/cmath/frexp/
* http://stackoverflow.com/questions/15685181/how-to-get-the-sign-mantissa-and-exponent-of-a-floating-point-number
* 
* 
*/