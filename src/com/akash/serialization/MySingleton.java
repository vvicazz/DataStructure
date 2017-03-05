package com.akash.serialization;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Things to keep in mind while creating singleton <br>
 * 1. multi-threaded environment <br>
 * 2. Clustered environment, handeling Serialization and deserialzation <br>
 * 3. calling clone() method <br>
 * 4. creating object via reflection <br>
 * 5. one single class in multiple webapp in same container <br>
 * http://www.oracle.com/technetwork/articles/java/singleton-1577166.html	<br>
 * http://stackoverflow.com/questions/23445434/how-to-create-a-jvm-global-singleton <br>
 */
class MySingleton implements Serializable {

	private static final long serialVersionUID = 1L;
	private static volatile MySingleton instance;

	private MySingleton() {
		if (MySingleton.instance != null) {
			throw new UnsupportedOperationException("Cannot create object via reflection");
		}
	}

	public static MySingleton getInstance() {
		if (instance == null) {
			synchronized (MySingleton.class) {
				if (instance == null) {
					instance = new MySingleton();
				}
			}
		}
		return instance;
	}

	public Object readResolve() {
		return getInstance();
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException("Cloning of this class is not allowed");
	}

	@SuppressWarnings("unchecked")
	public static void main(String args[]) {
		MySingleton ob1 = MySingleton.getInstance();
		MySingleton ob2 = MySingleton.getInstance();
		System.out.println(ob1.hashCode());
		System.out.println(ob2.hashCode());
		try {
			Class<MySingleton> singletonClass = (Class<MySingleton>) Class
					.forName("com.akash.serialization.MySingleton");
			MySingleton singletonReflection = singletonClass.newInstance();
			System.out.println(singletonReflection.hashCode());
		} catch (Exception e) {
			System.out.println(e);
		}
		try {
			ob1.clone();
		} catch (Exception e) {
			System.out.println(e);
		}
		try {
			serialize();
		} catch (ClassNotFoundException | IOException e) {
			System.out.println(e);
		}
	}

	private static void serialize() throws FileNotFoundException, IOException, ClassNotFoundException {
		MySingleton instanceOne = MySingleton.getInstance();
		ObjectOutput out = new ObjectOutputStream(new FileOutputStream("F:\\filename.ser"));
		out.writeObject(instanceOne);
		out.close();

		// deserailize from file to object
		ObjectInput in = new ObjectInputStream(new FileInputStream("F:\\filename.ser"));
		MySingleton instanceTwo = (MySingleton) in.readObject();
		in.close();

		System.out.println("instanceOne hashCode=" + instanceOne.hashCode());
		System.out.println("instanceTwo hashCode=" + instanceTwo.hashCode());
	}
}