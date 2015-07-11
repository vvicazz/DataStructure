package com.akash.generics;

import java.util.Collection;

public class RawGeneric {

	public static void main(String args[]) {
		Box<String> box1 = new Box<>();
		
		Box box2 = box1;
		//Box is a raw type. References to generic type Box<T> should be parameterized
		
		box2.setT("hello");
		//Type safety: The method setT(Object) belongs to the raw type Box. References to generic type Box<T> should be parameterized
		
		System.out.println(box2);
		box2.setT(3);
		//Type safety: The method setT(Object) belongs to the raw type Box. References to generic type Box<T> should be parameterized
		
		System.out.println(box2);
		System.out.println(box1);
		
		String s = box1.getT();		//Error : Integer cannot be cast to String
	}
}

class Box<T> {
	
	private T t;

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}
	
	public String toString() {
		return "class:"+t.getClass().getName()+" , "+t.toString();
	}
}