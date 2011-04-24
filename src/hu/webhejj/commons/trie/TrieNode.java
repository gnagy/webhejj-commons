/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons.trie;

import java.util.Arrays;

/**
 * Implementation of a Trie data structure for pattern matching
 *
 * @param <T> value type of trie node
 */
public class TrieNode<T> {

	private int tail;
	private char[] keys;
	private TrieNode<T>[] children;
	
	@SuppressWarnings("unchecked")
	public TrieNode(int size, T value) {
		keys = new char[size];
		children = new TrieNode[size];
		tail = 0;
	}
	
	private T value;
	
	public T getValue() {
		return value;
	}
	public void setValue(T value) {
		this.value = value;
	}
	
	@SuppressWarnings("unchecked")
	public TrieNode<T> addChild(char key) {
		
		int pos = Arrays.binarySearch(keys, 0, tail, key);
		
		if(pos >= 0) {
			return children[pos];
		}
		
		int ip = -pos - 1;
		TrieNode<T> trieNode = new TrieNode<T>(Math.max(5, keys.length / 2), null);
		
		char[] newKeys = keys;
		TrieNode<T>[] newChildren = children;
		
		if(tail >= keys.length) {
			newKeys = new char[keys.length * 2];
			newChildren = new TrieNode[keys.length * 2];
		}
		
		if(keys != newKeys) {
			System.arraycopy(keys, 0, newKeys, 0, ip);
			System.arraycopy(children, 0, newChildren, 0, ip);
		}

		if(ip < tail) {
			System.arraycopy(keys, ip, newKeys, ip + 1, tail - ip);
			System.arraycopy(children, ip, newChildren, ip + 1, tail - ip);
		}
		newKeys[ip] = key;
		newChildren[ip] = trieNode;		
		
		keys = newKeys;
		children = newChildren;
		tail++;
		
		return trieNode;
	}
	
	public boolean isLeaf() {
		return tail == 0;
	}
	
	public TrieNode<T> getChild(char key) {
		int pos = Arrays.binarySearch(keys, 0, tail, key);
		return pos < 0 ? null : children[pos];
	}
	
	public void dump(int indent) {
		for(int k = 0; k < tail; k++) {
			for(int i = 0; i < indent; i++) {
				System.out.print("  ");
			}
			System.out.format("%s    %s\n", keys[k], children[k].value == null ? "" : children[k].value.toString());
			children[k].dump(indent + 1);
		}
	}
}
