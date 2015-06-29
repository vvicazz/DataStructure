package com.akash.hashtable;

import java.util.HashSet;
import java.util.Set;

public class EqualsHashcode {

	int a;
	String str;
	
	EqualsHashcode(int a, String str) {
		this.a=a;
		this.str=str;
	}
	
	@Override
	public int hashCode() {
		return 1;
	}
	
	public static void main(String args[]) {
		Set<EqualsHashcode> set = new HashSet<EqualsHashcode>();
		set.add(new EqualsHashcode(1,"A"));
		set.add(new EqualsHashcode(2,"B"));
		set.add(new EqualsHashcode(1,"A"));
		System.out.println(set.size());
	}
}