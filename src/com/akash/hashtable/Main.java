package com.akash.hashtable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

public class Main
{
	public static void main(String args[])
	{
//		AbstractHashTable hashTable=new MyHashTable();
//		hashTable.put("abc", "1");
//		hashTable.put("bac", "2");
//		hashTable.put("cba", "3");
//		hashTable.put("bca", "4");
//		hashTable.put("cab", "5");
//		hashTable.put("acb", "6");
//		hashTable.remove("bca");
//		hashTable.put("akash", "coca cola");
//		hashTable.put("suraj", "pepsi");
//		hashTable.put("rahul", "limka");
//		System.out.println(hashTable.get("cab"));
		Hashtable t;
		HashMap hm=new HashMap();
		hm.put("1", "A");
		hm.put("2", "B");
		hm.put("3", "C");
		hm.put("4", "D");
		Iterator iterator=hm.entrySet().iterator();
		System.out.println("removing elements...");
//		hm.remove("1");			this will cause ConcurrentModificationException
//		hm.put("5", "E");		this will cause ConcurrentModificationException
		
		for(Object key : hm.entrySet())
		{
			System.out.println("Key : " + key.toString() + " Value : "+ hm.get(key));
			if(key.equals("1"))
				hm.remove("1");
		}
		
		if(iterator.hasNext())
		{
			System.out.println("iterator.next() : "+iterator.next());
			iterator.remove();
		}
		Map m;
		Set set;
		
		SortedSet sortedSet;
		TreeSet treeSet;
		LinkedList linkedList;
		ArrayList arrayList;
		Vector vector;
		LinkedHashSet linkedHashSet;
		Collections c;
		Set hset=new TreeSet();
		hset.add("1");
		hset.add("2");
		hset.add("3");
		hset.add("4");
		hset.add("5");
		System.out.println("hset : "+hset);
		String s="a";
		System.out.println("aaa ="+s.getClass().getSimpleName());
	}
}