package com.akash.graph.api.usage;

import java.util.HashSet;
import java.util.Set;

class A {
	Set<? extends C> cset = new HashSet<>();

	public Set<? extends C> getSet() {
		return doGetSet();
	}

	public void addToSet(C c) {
		doAddToSet(c);
	}

	protected Set<? extends C> doGetSet() {
		return cset;
	}

	@SuppressWarnings("unchecked")
	protected void doAddToSet(C c) {
		Set<C> cset = (Set<C>) doGetSet();
		cset.add(c);
	}
	
	@Override
	public String toString() {
		return ""+doGetSet();
	}
}

class B extends A {
	Set<D> dset = new HashSet<>();

	protected Set<? extends C> doGetSet() {
		return dset;
	}

	@SuppressWarnings("unchecked")
	protected void doAddToSet(C c) {
		D d = (D) c;
		Set<D> dset = (Set<D>) doGetSet();
		dset.add(d);
	}
}

class C {
	@Override
	public String toString() {
		return "C obj";
	}
}

class D extends C {
	@Override
	public String toString() {
		return "D obj";
	}
}

public class InheritenceExample {
	
	public static void main(String args[]) {
		B b = new B();
		b.addToSet(new D());
		System.out.println(b);
	}
}