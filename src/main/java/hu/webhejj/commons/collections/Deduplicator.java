/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons.collections;

/**
 * Deduplicator always returns the first occurrence of an item.
 * 
 * Useful for e.g. deduplicating objects read from a file. 
 * 
 * @author Gergely Nagy <greg@webhejj.hu>
 *
 * @param <T> type to deduplicate
 */
public interface Deduplicator<T> {

	T deduplicate(T item);
}
