/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons.diff;

import java.util.List;

/**
 * Interface implementations that can perform a diff operation on iterables
 *
 * @param <T> type of differentiated objects
 */
public interface Differentiator<T> {

	public List<Difference<T>> diff(Iterable<T> lefts, Iterable<T> rights, DiffComparator<T> comparator);

}
