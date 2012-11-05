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
	
	@Override
	public void handle(T left, T right, Type type) {
		
		if(types != null && Arrays.binarySearch(types, type) < 0) {
			return;
		}
		out.format("%20s %20s %s\n", left, right, type);
	}
}
