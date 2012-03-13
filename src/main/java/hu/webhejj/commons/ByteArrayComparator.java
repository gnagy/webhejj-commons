/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons;

import java.util.Comparator;

/**
 * Comparator for byte arrays
 */
public class ByteArrayComparator implements Comparator<byte[]> {

	public int compare(byte[] o1, byte[] o2) {
		
		int limit = Math.min(o1.length, o2.length);
		for(int i = 0; i < limit; i++) {
			if(o1[i] != o2[i]) {
				return o1[i] - o2[i];
			}
		}
		return o1.length - o2.length;
	}

}
