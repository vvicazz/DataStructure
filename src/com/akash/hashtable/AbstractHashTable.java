package com.akash.hashtable;


public abstract class AbstractHashTable implements GenericHashTable
{
	private int tableSize;
	private Node[] hashArray;
	
	abstract protected int getHashValue(String key);
	
	protected AbstractHashTable(int tableSize)
	{
		setTableSize(tableSize);
		setHashArray(new Node[tableSize]);
		for(int count=0; count<tableSize; count++)
			getHashArray()[count]=new Node();
	}
	
	protected void setHashArray(Node[] hashArray) {
		this.hashArray = hashArray;
	}
	
	protected Node[] getHashArray() {
		return hashArray;
	}
	
	protected int getTableSize() {
		return tableSize;
	}
	
	protected void setTableSize(int tableSize) {
		this.tableSize = tableSize;
	}
	
	/**
	 * returns true if array index is occupied by an empty node.
	 * @param index
	 * @return
	 */
	protected boolean isIndexOccupied(int index)
	{
		boolean isIndexAvailable=false;
		if( index>=0 && index < getTableSize() )
		{
			Node node=hashArray[index];
			if(node.getName()==null)
				isIndexAvailable=true;
		}
		return isIndexAvailable;
	}
	
	protected class Node
	{
		private String name;
		private String favDrink;
		private Node next;
		
		protected Node(){
		}
		
		protected Node(String name, String favDrink)
		{
			this.name=name;
			this.favDrink=favDrink;
		}
		
		protected String getName() {
			return name;
		}
		protected void setName(String name) {
			this.name = name;
		}
		protected String getFavDrink() {
			return favDrink;
		}
		protected void setFavDrink(String favDrink) {
			this.favDrink = favDrink;
		}
		protected Node getNext() {
			return next;
		}
		protected void setNext(Node next) {
			this.next = next;
		}
	}

	protected Node getNodeForIndex(String name, int index)
	{
		Node node=getHashArray()[index];
		while(true)
		{
			if( name.equals(node.getName()) )
				return node;
			else
			{
				node=node.getNext();
				if(node==null)
					break;
			}
		}
		return null;
	}
	
	@Override
	public void put(String name, String favDrink)
	{
		int index=getHashValue(name);
		Node head=getHashArray()[index];
		Node newNode=new Node(name, favDrink);
		if( !isIndexOccupied(index) )
		{
			Node parentNode=head;
			Node childNode=parentNode.getNext();
			while(childNode!=null)
			{
				parentNode=childNode;
				childNode=childNode.getNext();
			}
			parentNode.setNext(newNode);
		}
		else
			getHashArray()[index]=newNode;
	}
	
	@Override
	public String get(String name)
	{
		int index=getHashValue(name);
		if( !isIndexOccupied(index) )
		{
			Node node=getNodeForIndex(name, index);
			if(node!=null)
				return node.getFavDrink();
			else
				return null;
		}
		else
			return null;
	}
	
	@Override
	public void remove(String name)
	{
		int index=getHashValue(name);
		if( !isIndexOccupied(index) )
		{
			Node node=getNodeForIndex(name, index);
			if(node!=null)
				removeNodeFromArray(node, index);
		}
	}
	
	private void removeNodeFromArray(Node node, int index)
	{
		Node head=getHashArray()[index];
		if(head==node)
		{
			node.setFavDrink(null);
			node.setName(null);
			if(node.getNext()!=null)
				node=node.getNext();
		}
		else
			removeNodeFromLinkedList(head, node);
	}
	
	private void removeNodeFromLinkedList(Node head, Node node)
	{
		Node parentNode=head;
		Node childNode=head.getNext();
		while(true)
		{
			if(childNode==node)
			{
				parentNode.setNext( childNode.getNext() );
				childNode.setFavDrink(null);
				childNode.setName(null);
				childNode.setNext(null);
				break;
			}
			else if(childNode==null)
				break;
			else
			{
				parentNode=childNode;
				childNode=childNode.getNext();
			}
		}
	}
}