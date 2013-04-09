/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons.diff;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Iterator;

import hu.webhejj.commons.diff.Difference.Type;

public class DiffPrinter<T> implements DiffHandler<T> {

	private PrintStream out;
	private Difference.Type[] types;
	
	public DiffPrinter() {
		this(System.out, (Type[]) null);
	}
	public DiffPrinter(PrintStream out) {
		this(out, (Type[]) null);
	}
	public DiffPrinter(Type... types) {
		this(System.out, types);
	}
	public DiffPrinter(PrintStream out, Type... types) {
		this.out = out;
		this.types = types;
		Arrays.sort(types);
	}

	public void begin() {
	}
	
	@Override
	public void handle(T left, T right, Type type) {
		
		if(types != null && Arrays.binarySearch(types, type) < 0) {
			return;
		}
		
		if(Iterable.class.isAssignableFrom(left.getClass())) {
			Iterator<?> l = ((Iterable<?>) left).iterator();
			Iterator<?> r = ((Iterable<?>) right).iterator();
			
			out.format("%10s [", type);
			while(l.hasNext() || r.hasNext()) {
				if(l.hasNext()) {
					out.print(l.next());
				}
				out.print("->");
				if(r.hasNext()) {
					out.print(r.next());
				}
				out.print(", ");
			}
			out.print("]\n");
		} else {
			out.format("%10s %20s %20s\n", type, left, right);
		}
	}
	
	public void finish() {
	}
}
