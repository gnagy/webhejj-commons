/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons.collections;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Histogram that counts the number of times an item was added, with
 * java.util.Set semantics.
 * 
 * @param <T> type of items to count
 */
public class Histogram<T> {
	
	private Map<T, Integer> map = new HashMap<T, Integer>();

	/** add item to histogram, increasing its frequency by one */
	public int add(T item) {
		
		Integer count = map.get(item);
		
		if(count == null) {
			map.put(item, 1);
			return 1;
		} else {
			map.put(item, count + 1);
			return count + 1;
		}
	}
	
	/** get set of items in histogram */
	public Set<T> getItems() {
		return map.keySet();
	}
	
	/** @return the frequency of the item */
	public int getFrequency(T item) {
		return map.get(item);
	}
	
	/** @return entries in histogram */
	public Set<Entry<T, Integer>> entrySet() {
		return map.entrySet();
	}
	
	/** @return first entry with the highest frequency using a linear search on the set of items */
	public Entry<T, Integer> getMax() {
		
		Entry<T, Integer> max = null;
		for(Entry<T, Integer> entry: map.entrySet()) {
			if(max == null || entry.getValue() > max.getValue()) {
				max = entry;
			}
		}
		return max;
	}
	
	/** @return size of the histogram */
	public int getSize() {
		return map.size();
	}
	
	/** @return the total number of items in histogram (sum of all frequencies) */
	public int getTotal() {
		int sum = 0;
		for(Integer f: map.values()) {
			sum += f;
		}
		return sum;
	}
	
	@Override
	public String toString() {
		return map.toString();
	}
}