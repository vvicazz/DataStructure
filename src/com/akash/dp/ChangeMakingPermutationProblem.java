package com.akash.dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


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
	 * C(5) is 2 because {2,3} and {3,2} are same.	<br>
	 * 
	 * The Set in memoizedList contains list of integers.	<br>
	 * The first list contains cost value of the key with a dummy number which is zero.	<br>
	 * The rest of the list contains the actual combination of coins.	<br>
	 * This dummy number is added to make it different from coin combination.	<br>
	 * e.g:		<br>
	 * C(5)=2	<br>
	 * key=5	<br>
	 * value=[{2,0} , {2,3} , {5}]	<br>
	 */
	private static Map<Integer,Set<List<Integer>>> memoizedList = new HashMap<Integer, Set<List<Integer>>>();
	
	private static final int num=10;
	private static final List<Integer> coinList=new ArrayList(){{add(2);add(5);add(3);add(6);}};
	
	public static void main(String args[]) {
		addListToSetOfMemoizedList(0, new int[]{1,0});
		int minValueInCoinList=coinList.get(0);
		for(int counter=1;counter<minValueInCoinList;counter++) {
			addListToSetOfMemoizedList(counter, new int[]{0,0});
		}
		int costForNum=getCountForCoins(num, coinList);
		System.out.println(costForNum);
		Set<List<Integer>> listOfCoins=memoizedList.get(num);
		Iterator it=listOfCoins.iterator();
		if(it.hasNext()) {
			List<Integer> list=(List<Integer>)it.next();
			System.out.println(Arrays.toString(list.toArray()));
		}
	}
	
	private static int getCountForCoins(int number,List<Integer> listOfCoin) {
		int costOfNumber=0;
		if(number >= 0) {
			Set<List<Integer>> finalCostSet = new HashSet<List<Integer>>();
			for(int counter=0; counter<listOfCoin.size(); counter++) {
				Set<List<Integer>> cloneSetForCostOfSubNumber = null;
				int subNumber = number - listOfCoin.get(counter);
				Set setForCostOfSubNumber = memoizedList.get(subNumber);
				Integer costOfSubNumber = 0;
				if(setForCostOfSubNumber != null) {
					costOfSubNumber = (Integer)fetchOrRemoveFirstListFromSetOfList(setForCostOfSubNumber, true).get(0);
				}
				else {
					costOfSubNumber = getCountForCoins(subNumber, listOfCoin);
				}
				costOfNumber = costOfNumber + costOfSubNumber;
				if(costOfSubNumber > 0) {
					setForCostOfSubNumber = memoizedList.get(subNumber);
					cloneSetForCostOfSubNumber = new HashSet<List<Integer>>();
					cloneSetForCostOfSubNumber.addAll(setForCostOfSubNumber);
					fetchOrRemoveFirstListFromSetOfList(cloneSetForCostOfSubNumber, false);
					calculateFinalCostSet(cloneSetForCostOfSubNumber, finalCostSet, subNumber, number);
				}
			}
			addListToSetOfMemoizedList(number, new int[]{finalCostSet.size(),0});
		}
		return costOfNumber;
	}
	
	/**
	 * If fetch=true , return first list that contains 0
	 * else delete first list that contains 0
	 * @param setOfListOfInt
	 * @param fetch
	 * @return
	 */
	private static List<Integer> fetchOrRemoveFirstListFromSetOfList(Set<List<Integer>> setOfListOfInt , boolean fetch) {
		List<Integer> firstList=null;
		Iterator it=(Iterator) setOfListOfInt.iterator();
		while( it.hasNext() ) {
			ArrayList<Integer> listOfCoins= (ArrayList<Integer>) it.next();
			if( listOfCoins.contains(0) ) {
				if(fetch)
					firstList = listOfCoins;
				else
					it.remove();
				break;
			}
		}
		return firstList;
	}
	
	private static void calculateFinalCostSet(Set<List<Integer>> cloneSetForCostOfSubNumber, Set<List<Integer>> finalCostSet, int subNumber, int number) {
		for(List<Integer> list : cloneSetForCostOfSubNumber) {
			list.add(subNumber);
			finalCostSet.add(list);
		}
		if(cloneSetForCostOfSubNumber.size()==0 && subNumber==0) {
			//
		}
	}
	
	private static void addListToSetOfMemoizedList(int key, int[] arr) {
		List<Integer> list=new ArrayList<Integer>();
		for(int num : arr) {
			list.add(num);
		}
		Set<List<Integer>> setOfList=memoizedList.get(key);
		if(setOfList==null) {
			setOfList=new HashSet<List<Integer>>();
		}
		setOfList.add(list);
		memoizedList.put(key, setOfList);
	}
}
