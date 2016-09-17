package com.akash.unsafe;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

public class UnsafeExample {

	public static void main(String args[]) throws NoSuchFieldException, SecurityException, IllegalArgumentException,
			IllegalAccessException {

		Field f = Unsafe.class.getDeclaredField("theUnsafe");
		f.setAccessible(true);
		Unsafe U = (Unsafe) f.get(null);

		Node<String, String>[] node = new Node[2];
		Class<?> ak = node.getClass();
		int ABASE = U.arrayBaseOffset(ak); // base offset of array
		int scale = U.arrayIndexScale(ak); // size of element
		int ASHIFT = 31 - Integer.numberOfLeadingZeros(scale);
		System.out.println(ABASE);
		System.out.println(scale);
		System.out.println(ASHIFT);

	}

	static class Node<K, V> {
		int hash;
		K key;
		volatile V val;
		volatile Node<K, V> next;
	}
}
