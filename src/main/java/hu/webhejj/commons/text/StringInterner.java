/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons.text;

// based on code from org.apache.lucene.util.StringInterner
public class StringInterner {

	private static class Entry {
		final private String str;
		final private int hash;
		private Entry next;

		private Entry(String str, int hash, Entry next) {
			this.str = str;
			this.hash = hash;
			this.next = next;
		}
	}

	private final Entry[] cache;
	private final int maxChainLength;

	public StringInterner(int tableSize, int maxChainLength) {
		cache = new Entry[Math.max(1, nextHighestPowerOfTwo(tableSize))];
		this.maxChainLength = Math.max(2, maxChainLength);
	}

	public String intern(CharSequence s) {
		int h = hash(s);
		int slot = h & (cache.length - 1);

		Entry first = this.cache[slot];
		Entry nextToLast = null;

		int chainLength = 0;

		for (Entry e = first; e != null; e = e.next) {
			if (e.hash == h && isEqual(e.str, s)) {
				// if (e.str == s || (e.hash == h && e.str.compareTo(s)==0)) {
				return e.str;
			}

			chainLength++;
			if (e.next != null) {
				nextToLast = e;
			}
		}

		// insertion-order cache: add new entry at head
		String interned = s.toString().intern();
		this.cache[slot] = new Entry(interned, h, first);
		if (chainLength >= maxChainLength) {
			// prune last entry
			nextToLast.next = null;
		}
		return interned;
	}

	protected boolean isEqual(CharSequence cs1, CharSequence cs2) {
		if (cs1 == cs2) {
			return true;
		}
		if (cs1 == null || cs2 == null) {
			return false;
		}
		if (cs1.length() != cs2.length()) {
			return false;
		}
		for (int i = 0; i < cs1.length(); i++) {
			if (cs1.charAt(i) != cs2.charAt(i)) {
				return false;
			}
		}
		return true;
	}

	protected int nextHighestPowerOfTwo(int v) {
		v--;
		v |= v >> 1;
		v |= v >> 2;
		v |= v >> 4;
		v |= v >> 8;
		v |= v >> 16;
		v++;
		return v;
	}
	
	protected int hash(CharSequence s) {
		// this is from String.hashCode()
		int h = 0;
        for (int i = 0; i < s.length(); i++) {
            h = 31 * h + s.charAt(i);
        }

		// this is from HashMap.hash()
		h ^= (h >>> 20) ^ (h >>> 12);
        h ^= (h >>> 7) ^ (h >>> 4);

        return h;
	}
}
