package com.akash.design.patterns;

// http://stackoverflow.com/questions/10941464/builder-pattern-with-inheritance
// http://stackoverflow.com/questions/9138027/builder-pattern-for-polymorphic-object-hierarchy-possible-with-java/9138629#9138629
//*** http://www.angelikalanger.com/GenericsFAQ/FAQSections/ProgrammingIdioms.html#What%20is%20the%20getThis%20trick?

class Abc {
	// mandatory
	private int a,b;
	
	// optional
	private int c,d;
	
	private Abc(AbcBuilder builder) {
		a = builder.a;
		b = builder.b;
		c = builder.c;
		d = builder.d;
	}
	
	public static class AbcBuilder {
		private int a,b;
		private int c,d;
		
		public AbcBuilder() {
		}
		AbcBuilder buildA(int a) {
			this.a = a; return this;
		}
		AbcBuilder buildB(int b) {
			this.b = b; return this;
		}
		AbcBuilder buildC(int c) {
			this.c = c; return this;
		}
		AbcBuilder buildD(int d) {
			this.d = d; return this;
		}
		public Abc build() {
			return new Abc(this);
		}
	}
}

public class BuilderPattern {
	public static void main(String args[]) {
		Abc ob = new Abc.AbcBuilder().buildA(2).buildB(1).buildC(5).build();
	}
}