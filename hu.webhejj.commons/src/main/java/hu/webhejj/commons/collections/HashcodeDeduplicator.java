/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons.collections;

public class HashcodeDeduplicator<T> extends HashMapDeduplicator<T, T> {

	protected T getKey(T value) {
		return value;
	};
}
