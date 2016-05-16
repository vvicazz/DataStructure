package com.akash.dp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *   Problem :<br>
 *	 We have number of coins with their values v1 < v2 < v3 < ... < vn
 *   where v1=1
 *   We have a sum of amount P
 *   Now we have to make minimum no. of coin combination for this amount P.
 *   <p>
 *   Solution:<br>
 *   C(p) = min of { C(p-v1) , C(p-v2) , C(p-v3) , .. , (p-vn)} + 1
 *   where C(0)=0
 *   
 *   <br>Links<br>
 *   http://en.wikipedia.org/wiki/Change-making_problem
 *   <br>
 *   eg:
 *   sum=16
 *   coins={1,2,7,10}
 *   
 *   greedy solution = {10,2,2,2}
 *   optimal solution = {7,7,2}
 */
public class ChangeMakingProblem {

	/**
	 * It represent a map of key-value pair where key represent the sum and value represent a 
	 * solution of the problem for that sum.
	 * eg: if coins=1,2,3
	 * and key=6 , value={2,3,3} where 
	 * first value in the list represents number of coins and
	 * rest of the values represents coin value.
	 */
	private static Map<Integer,List<Integer>> memoizedCoinsList=new HashMap<Integer,List<Integer>>();
	
	private static int sum=16;
	private static int[] listOfCoins=new int[]{ 1, 2, 7, 10};
	
	public static void main(String args[]) {
		memoizedCoinsList.put(0, new ArrayList<Integer>(){{add(0);}});
		int numberOfCoins=getNumberOfCoins( sum, listOfCoins);
		Iterator<Entry<Integer,List<Integer>>> iterator=memoizedCoinsList.entrySet().iterator();
		while(iterator.hasNext()) {
			Map.Entry<Integer,List<Integer>> entry=(Entry<Integer,List<Integer>>) iterator.next();
			System.out.println(""+entry.getKey()+" : "+entry.getValue());
		}
		System.out.println("numberOfCoins "+numberOfCoins +" for sum "+sum);
	}
	
	private static int getNumberOfCoins(int sum, int[] coinList) {
		if(sum<0)
			return -1;
		else {
			int[] getMinimumCost=new int[coinList.length];
			for(int counter=0; counter<coinList.length; counter++) {
				List<Integer> minCostList;
				Integer minCost;
				minCostList=memoizedCoinsList.get(sum-coinList[counter]);
				if(minCostList==null)
					minCost=getNumberOfCoins(sum-coinList[counter], coinList);
				else
					minCost=minCostList.get(0);
				getMinimumCost[counter]=minCost;
			}
			int positionForMinCost=getMinValuePositionFromList(getMinimumCost);
			List<Integer> leastCostList=memoizedCoinsList.get(sum-coinList[positionForMinCost]);
			if(leastCostList==null)
				leastCostList=new ArrayList<Integer>(){{add(0);}};
			ArrayList<Integer> newLeastCostList=new ArrayList<Integer>();
			newLeastCostList.addAll(leastCostList);
			newLeastCostList.add(coinList[positionForMinCost]);
			newLeastCostList.set(0, Integer.valueOf(leastCostList.get(0)+1));
			memoizedCoinsList.put(sum,  newLeastCostList);
			return newLeastCostList.get(0);
		}
	}
	
	private static int getMinValuePositionFromList(int[] listOfCost) {
		int minValue=listOfCost[0];
		int pos=0;
		for(int counter=1; counter<listOfCost.length; counter++) {
			if( ( ( minValue < 0 && listOfCost[counter]==0 ) || minValue > listOfCost[counter])
					&& listOfCost[counter] >= 0 ) {
				minValue = listOfCost[counter];
				pos=counter;
			}
		}
		return pos;
	}
}