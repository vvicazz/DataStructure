package com.akash.dp.coindenomination;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Akash
 * http://www3.cs.stonybrook.edu/~algorith/video-lectures/
 * https://www.hackerrank.com/contests/programming-interview-questions/challenges/coin-change
 * 
 * How many different ways can you make change for an amount, given a list of coins
 * a list of coins c1, c2, c3, ..
 * and an amount n
 * Prints out how many different ways you can make change from the coins
 * 
 * For N = 4 and C = {1,2,3} there are four solutions: {1,1,1,1},{1,1,2},{2,2},{1,3}
 * output is 4
 * 
 * C(N) = sum of all { C(N-Vi) }
 * 
 * where i goes from 1 to n
 * V1 < V2 < V3 < ... < Vn
 */
public class ChangeMakingPermutationProblem {

	/**
	 * C(<0)=0	<br>
	 * C(0)=1	<br>
	 * C(1)=0	<br>
	 * C(2)=C(0)+C(-3)+C(-1)+C(-4)=1+0+0+0=1	<br>
	 * C(3)=C(1)+C(-2)+C(0)+C(-3)=0+1+0+0=1		<br>
	 * C(4)=C(2)+C(-1)+C(1)+C(-2)=1+0+0+0=1		<br>
	 * C(5)=C(3)+C(0)+C(2)+C(-1)=1+1+1+0=2		<br>
	 * C(5) is 2 because {2,3} and {3,2} are same.
	 */
	private static Map<Integer,List<List<Integer>>> memoizedList = new HashMap<Integer, List<List<Integer>>>();
	
	private static final int num=10;
	private static final List<Integer> coinList=new ArrayList(){{add(2);add(5);add(3);add(6);}};
	
	public static void main(String args[]) {
		memoizedList.put(0, new ArrayList(){{add( new ArrayList(){{add(1);}} );}});
		int minValueInCoinList=coinList.get(0);
		for(int counter=1;counter<minValueInCoinList;counter++) {
			memoizedList.put(counter, new ArrayList(){{add( new ArrayList(){{add(0);}} );}});
		}
		getCountForCoins(num, coinList);
	}
	
	private static int getCountForCoins(int number,List<Integer> listOfCoin) {
		if(number<0)
			return 0;
		else
		{
			
		}
		return 0;
	}
}
