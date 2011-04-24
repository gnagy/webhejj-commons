/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons.collections;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Utility methods for working with collections.
 */
public class CollectionUtils {

	/**
	 * Same as in java.util.Collections, but returns list to allow daisy chaining calls
	 */
	public static <T extends Comparable<? super T>> List<T> sort(List<T> list) {
		Collections.sort(list);
		return list;
	}

	/**
	 * Same as in java.util.Collections, but returns list to allow daisy chaining calls
	 */
	public static <T> List<T> sort(List<T> list, Comparator<T> comparator) {
		Collections.sort(list, comparator);
		return list;
	}
	
	/**
	 * Adds elements from one or more iterables to a single flat list
	 * 
	 * @param <E> item type
	 * @param iterables iterables to take elements from
	 * @return list containing items from all iterables
	 */
	public static <E> List<E> asFlatList(Iterable<E>... iterables) {
		List<E> list = new ArrayList<E>();
		for(Iterable<E> iterable: iterables) {
			for(E e: iterable) {
				list.add(e);
			}
		}
		return list;
	}
	
	/**
	 * Writes the string value of the specified items to appendable,
	 * wrapping an eventual IOException into a RuntimeException
	 * 
	 * @param appendable to append values to
	 * @param items items to dump
	 */
	public static void dump(Appendable appendable, Iterable<?> items) {
		try {
			for(Object item: items) {
				appendable.append(String.valueOf(item));
				appendable.append("\n");
			}
		} catch (IOException e) {
			throw new RuntimeException("An error occured while appending", e);
		}
	}
}
