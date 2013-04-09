/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons.diff;

import hu.webhejj.commons.diff.Difference.Type;

import java.util.ArrayList;
import java.util.List;

public class DiffCollector<T> implements DiffHandler<T> {

	private List<Difference<T>> differences;
	
	public DiffCollector() {
		differences = new ArrayList<Difference<T>>();
	}

	public DiffCollector(List<Difference<T>> differences) {
		this.differences = differences;
	}
	
	public void begin() {
	}

	@Override
	public void handle(T left, T right, Type type) {
		differences.add(new Difference<T>(left, right, type));
	}

	public void finish() {
	}
	
	public List<Difference<T>> getDifferences() {
		return differences;
	}
}
