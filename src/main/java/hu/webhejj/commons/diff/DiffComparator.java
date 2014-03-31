/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons.diff;

import java.util.Comparator;

/**
 * DiffComparator is a combination of Compartor<T> with an equals() method.
 * If two objects compare to 0, then if the equals method returns true
 * it means there is no difference, otherwise there was a change
 *
 * @param <T> type of differentiated objects
 */
public interface DiffComparator<T> extends Comparator<T> {
	
	/**
	 * @return true if left and right are to be considered unchanged, false otherwise
	 */
	boolean equals(T left, T right);
}


