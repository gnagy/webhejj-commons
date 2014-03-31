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

public abstract class HashMapDeduplicator<K, V> implements Deduplicator<V> {

	private final Map<K, V> items = new HashMap<K, V>();
	
	@Override
	public V deduplicate(V item) {
		
		K key = getKey(item);
		V original = items.get(key);
		
		if(original == null) {
			items.put(key, item);
			return item; 
		}
		return original;
	}
	
	protected abstract K getKey(V value);
}
