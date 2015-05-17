package com.akash.serialization;

public class Abc1 {

	private String c;
	private transient String d = "d1";

	Abc1() {
		c = "c";
//		System.out.println(d);
//		d = "d2";
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	public String getD() {
		return d;
	}

	public void setD(String d) {
		this.d = d;
	}
}