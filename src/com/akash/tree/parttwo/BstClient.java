package com.akash.tree.parttwo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class BstClient {

	public static void main(String args[]) {

		BinarySearchTree<Integer> tree = new BinarySearchTree<>();
		tree.add(20);
		tree.add(15);
		tree.add(25);
		tree.add(12);
		tree.add(22);
		tree.add(9);
		tree.add(29);
		tree.delete(25);
		tree.add(26);
		tree.delete(9);
		tree.add(8);
		System.out.println(tree.getInOrderTraversal());

		ser(tree);
		deser();
	}

	static void ser(BinarySearchTree<Integer> tree) {
		FileOutputStream fout = null;
		ObjectOutputStream oos = null;
		try {
			fout = new FileOutputStream("F:/serialize.ser");
			oos = new ObjectOutputStream(fout);
			oos.writeObject(tree);
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

	static void deser() {
		FileInputStream fout = null;
		ObjectInputStream oos = null;
		try {
			fout = new FileInputStream("F:/serialize.ser");
			oos = new ObjectInputStream(fout);
			BinarySearchTree<Integer> tree = (BinarySearchTree<Integer>) oos.readObject();
			System.out.println(tree.getInOrderTraversal());
		} catch (Exception e) {
			System.out.println("while deser...");
			e.printStackTrace();
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