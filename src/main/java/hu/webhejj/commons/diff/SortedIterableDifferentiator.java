/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons.diff;

import hu.webhejj.commons.ProgressMonitor;

import java.util.Iterator;

/**
 * An efficient differentiator that can compare iterables that contain elements in ascending order
 * according to the compare() method of the specified DiffComparator.
 *
 * @param <T> type of differentiated objects
 */
public class SortedIterableDifferentiator<T> implements Differentiator<T> {

	public void diff(Iterable<T> lefts, Iterable<T> rights, DiffComparator<T> comparator, DiffHandler<T> diffHandler, ProgressMonitor monitor) {

		if(monitor.isCanceled()) {
			return;
		}
		
		Iterator<T> li = lefts.iterator();
		Iterator<T> ri = rights.iterator();

		T left = null;
		T right = null;
		while(li.hasNext() || ri.hasNext()) {
			
			if(monitor.isCanceled()) {
				return;
			}
			
			if(left == null) {
				if(li.hasNext()) {
					left = li.next();
				} else {
					while(ri.hasNext()) {
						diffHandler.handle(null, ri.next(), Difference.Type.ADDED);
					}
					return;
				}
			}
			if(right == null) {
				if(ri.hasNext()) {
					right = ri.next();
				} else {
					// the check above already removed a left from the iterator
					diffHandler.handle(left, null, Difference.Type.DELETED);
					while(li.hasNext()) {
						diffHandler.handle(li.next(), null, Difference.Type.DELETED);
					}
					return;
				}
			}

			
			int diff = comparator.compare(left, right);
			if(diff == 0) {
				if(comparator.equals(left, right)) {
					diffHandler.handle(left, right, Difference.Type.UNCHANGED);
				} else {
					diffHandler.handle(left, right, Difference.Type.CHANGED);
				}
				left = null;
				right = null;
			
			} else if(diff < 0) {
				diffHandler.handle(left, null, Difference.Type.DELETED);
				left = null;
			
			} else {
				diffHandler.handle(null, right, Difference.Type.ADDED);
				right = null;
			}
		}
	}
}
