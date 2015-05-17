package com.akash.serialization;

import java.io.Serializable;

public class Abc2 extends Abc1 implements Serializable {

	private static final long serialVersionUID = 123L;
	private String a;
	private transient String b="sss";
//	public String e;

	Abc2() {
		b = "hello";
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}
}