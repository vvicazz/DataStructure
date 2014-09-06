package com.akash.hashtable;

public class MyHashTable extends AbstractHashTable
{
	private static final int defaultTableSize=10;
	
	public MyHashTable() {
		super(defaultTableSize);
	}
	
	public MyHashTable(int tableSize){
		super(tableSize);
	}
	
	protected int getHashValue(String key)
	{
		int hashValue=0;
		byte[] byteArr=key.getBytes();
		for(int count=0; count<byteArr.length; count++)
			hashValue=hashValue+byteArr[count];
		hashValue = hashValue - ( hashValue/getTableSize() ) * getTableSize();
		return hashValue;
	}
}