package com.akash.serialization;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Main {

	public static void main(String args[]) {

//		ser();
		deser();
	}

	private static void ser() {
		FileOutputStream fout = null;
		ObjectOutputStream oos = null;
		Abc2 ob = new Abc2();
		ob.setA("aaa");
		ob.setB("bbb");
		ob.setC("ccc");
		ob.setD("dddd");
		try {
			fout = new FileOutputStream("E:/serialize.ser");
			oos = new ObjectOutputStream(fout);
			oos.writeObject(ob);
		} catch (Exception e) {
		} finally {
			if (fout != null)
				try {
					fout.close();
				} catch (IOException e) {
				}
			if (oos != null)
				try {
					oos.close();
				} catch (IOException e) {
				}
		}
	}

	private static void deser() {

		FileInputStream fout = null;
		ObjectInputStream oos = null;
		Abc2 ob = null;
		try {
			fout = new FileInputStream("E:/serialize.ser");
			oos = new ObjectInputStream(fout);
			ob = (Abc2) oos.readObject();
			System.out.println("A :" + ob.getA());
			System.out.println("B :" + ob.getB());
			System.out.println("C :" + ob.getC());
			System.out.println("D :" + ob.getD());
//			System.out.println("E :" + ob.e);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (fout != null)
				try {
					fout.close();
				} catch (IOException e) {
				}
			if (oos != null)
				try {
					oos.close();
				} catch (IOException e) {
				}
		}
	}
}