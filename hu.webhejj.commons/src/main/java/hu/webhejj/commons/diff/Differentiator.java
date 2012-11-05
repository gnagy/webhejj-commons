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

/**
 * Interface for implementations that can perform a diff operation on iterables
 *
 * @param <T> type of differentiated objects
 */
public interface Differentiator<T> {

	void diff(Iterable<T> lefts, Iterable<T> rights, DiffComparator<T> comparator, DiffHandler<T> diffHandler, ProgressMonitor monitor);
}
