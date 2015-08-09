package com.akash.singleton;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;

class MySingleton implements Serializable, Cloneable {

	private static final long serialVersionUID = 123L;

	private static volatile MySingleton instance;

	private int age;

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	private MySingleton() {
		System.out.println("constructor is called");
	}

	public static MySingleton createInstance() {

		if (instance == null) {
			synchronized (MySingleton.class) {
				if (instance == null) {
					instance = new MySingleton();
				}
			}
		}
		return instance;
	}

	protected Object readResolve() throws ObjectStreamException {

		System.out.println("readResolve() is called");
		return createInstance();
	}

	public MySingleton clone() throws CloneNotSupportedException {

		System.out.println("clone is created");
		return createInstance();
	}
}

//lazy initialization
//creation of enum is thread safe, but its methods are not thread safe
//only have a single object in case of serializaion
//Enum objects cannot be cloned
enum MySingletonNew {
	INSTANCE;
	
	private int age;
	
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}


	MySingletonNew() {
		System.out.println("initializing singleton enum object");
	}
}

public class Abc {
	public static void main(String args[]) {

		testMySingleton();
		testMySingletonNew();
	}
	
	static void testMySingleton() {
		MySingleton ob = MySingleton.createInstance();
		ob.setAge(20);
		writeObjectToFile(ob);
		ob.setAge(22);
		MySingleton ob1 = readObjectFromFileClass();
		System.out.println("object read after ser in class : "+ob1.getAge());
		System.out.println("serializable objects in class : "+(ob == ob1));
		try {
			MySingleton ob2 = ob1.clone();
			System.out.println("clones equal in class : " + (ob2 == ob));
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
	
	static void testMySingletonNew() {
		MySingletonNew ob = MySingletonNew.INSTANCE;
		ob.setAge(20);
		writeObjectToFile(ob);
		ob.setAge(22);
		MySingletonNew ob1 = readObjectFromFileEnum();
		System.out.println("object read after ser in enum : "+ob1.getAge());
		System.out.println("serializable objects in class : "+(ob == ob1));
	}

	static void writeObjectToFile(MySingleton ob) {
		try {
			FileOutputStream fileOut = new FileOutputStream(
					"E:\\MySingleton.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(ob);
			out.close();
			fileOut.close();
			System.out
					.println("Serialized data is saved in E:\\MySingleton.ser");
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	static MySingleton readObjectFromFileClass() {
		MySingleton ob = null;
		try {
			FileInputStream fileIn = new FileInputStream("E:\\MySingleton.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			ob = (MySingleton) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
		} catch (ClassNotFoundException c) {
			System.out.println("MySingleton class not found");
			c.printStackTrace();
		}
		return ob;
	}
	
	static void writeObjectToFile(MySingletonNew ob) {
		try {
			FileOutputStream fileOut = new FileOutputStream(
					"E:\\MySingletonNew.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(ob);
			out.close();
			fileOut.close();
			System.out
					.println("Serialized data is saved in E:\\MySingletonNew.ser");
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	static MySingletonNew readObjectFromFileEnum() {
		MySingletonNew ob = null;
		try {
			FileInputStream fileIn = new FileInputStream("E:\\MySingletonNew.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			ob = (MySingletonNew) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
		} catch (ClassNotFoundException c) {
			System.out.println("MySingletonNew class not found");
			c.printStackTrace();
		}
		return ob;
	}
}

/*
 * How Spring implements singleton pattern for beans
 * http://techienjoy.com/Spring-Singleton-GOF-Singleton-Difference.php#sc
 * 
 * eg: java.lang.Runtime , java.awt.Toolkit
 * 
 * (Q)If we serialize a singleton object then change some values and then deserialize it.
 * 		we would have a difference in two objects.
 * 		One our own application and one from deserialization.
 * 		We can check this condition in readObject(Stream input) method
 * 		and can configure the object values we want.
 * 
 */