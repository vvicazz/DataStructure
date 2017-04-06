package com.akash.serialization;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * instanceOne values =3-3-5-2-12-20-32-42 <br>
 * instanceTwo values =3-3-0-0-12-20-0-40 <br>
 * <br>
 * 
 * Rules of assigning values to variables during deserialization : <br><br>
 * ->instance variable of class (final or non final) initialized via serialization
 * process. <br>
 * ->transient values are always initialized as their default values and not the
 * values as declared at class level. <br>
 * ->static variables are initialized as declared at class level or as their
 * default values if not declared at class level. <br>
 * ->no constructor is called for final instance variable, non final instance
 * variable, transient or static fields during deserialization
 */
public class TestSer implements Serializable {

	private static final long serialVersionUID = 1L;

	int a;
	final int b;
	transient int c;
	static int d;
	int e = 10;
	final int f = 20;
	transient int g = 30;
	static int h = 40;

	TestSer() {
		b = -1;
		System.out.println("no args constructor...");
	}

	TestSer(int a, int b, int c) {
		System.out.println("constructor..");
		this.a = a + 1;
		this.b = b + 1;
		this.c = c + 1;
		d++;
		e++;
		g++;
		h++;
	}

	void change() {
		a++;
		c++;
		d++;
		e++;
		g++;
		h++;
	}

	@Override
	public String toString() {
		return a + "-" + b + "-" + c + "-" + d + "-" + e + "-" + f + "-" + g + "-" + h;
	}

	public static void main(String args[]) {
		// serialize();
		deserialize();
	}

	private static void serialize() {
		try {
			TestSer instanceOne = new TestSer(1, 2, 3);
			instanceOne.change();
			ObjectOutput out = new ObjectOutputStream(new FileOutputStream("F:\\filename.ser"));
			out.writeObject(instanceOne);
			out.close();
			System.out.println("instanceOne values =" + instanceOne);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void deserialize() {
		try {
			ObjectInput in = new ObjectInputStream(new FileInputStream("F:\\filename.ser"));
			TestSer instanceTwo = (TestSer) in.readObject();
			in.close();
			System.out.println("instanceTwo values =" + instanceTwo);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
}