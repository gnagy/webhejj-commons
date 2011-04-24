/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons.diff;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * An efficient differentiator that can compare iterables that contain elements in ascending order
 * according to the compare() method of the specified DiffComparator.
 *
 * @param <T> type of differentiated objects
 */
public class SortedIterableDifferentiator<T> implements Differentiator<T> {

	public List<Difference<T>> diff(Iterable<T> lefts, Iterable<T> rights, DiffComparator<T> comparator) {
		
		List<Difference<T>> differences = new ArrayList<Difference<T>>();

		Iterator<T> li = lefts.iterator();
		Iterator<T> ri = rights.iterator();

		T left = null;
		T right = null;
		while(li.hasNext() || ri.hasNext()) {
			if(left == null && li.hasNext()) {
				left = li.next();
			}
			if(right == null && ri.hasNext()) {
				right = ri.next();
			}
			
			int diff = comparator.compare(left, right);
			if(diff == 0) {
				if(comparator.equals(left, right)) {
					differences.add(new Difference<T>(left, right, Difference.Type.UNCHANGED));
				} else {
					differences.add(new Difference<T>(left, right, Difference.Type.CHANGED));
				}
				left = null;
				right = null;
			
			} else if(diff < 0) {
				differences.add(new Difference<T>(left, right, Difference.Type.DELETED));
				left = null;
			
			} else {
				differences.add(new Difference<T>(left, right, Difference.Type.ADDED));
				right = null;
			}
		}
		
		return differences;
	}
}
