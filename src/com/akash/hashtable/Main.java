package com.akash.hashtable;

import java.util.Hashtable;

public class Main
{
	public static void main(String args[])
	{
		AbstractHashTable hashTable=new MyHashTable();
		hashTable.put("abc", "1");
		hashTable.put("bac", "2");
		hashTable.put("cba", "3");
		hashTable.put("bca", "4");
		hashTable.put("cab", "5");
		hashTable.put("acb", "6");
		hashTable.remove("bca");
		hashTable.put("akash", "coca cola");
		hashTable.put("suraj", "pepsi");
		hashTable.put("rahul", "limka");
		System.out.println(hashTable.get("cab"));
		Hashtable t;
	}
}