/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons.io.table;

/** Utility methods to convert between alphabetical and numeric column names */
public class AlphaColumnUtil {

	private static final int ALPHABET_SIZE = 26;
	
	public static String toAlpha(int index) {
		if(index < 0) {
			throw new IndexOutOfBoundsException("Index must not be negative, was: " + index);
		}
		
		StringBuffer buf = new StringBuffer();
		while (index >= 0) {
			buf.insert(0, (char) ('A' + index % ALPHABET_SIZE));
			index = (index / ALPHABET_SIZE) - 1 ;
		}
		return buf.toString();
	}
	
	public static int toNumeric(String column) {
		int index = 0;
		int digit = 1;
		
		for(int i = column.length() - 1; i >= 0; i--) {
			char c = Character.toUpperCase(column.charAt(i));
			if(c < 'A' || c > 'Z') {
				throw new IllegalArgumentException("Illegal character in column " + column + ": " + c);
			} else {
				index += (c - 'A' + 1) * digit;
			}
			digit += 'Z' - 'A';
		}
		if(index < 1) {
			throw new IllegalArgumentException("Oops, column " + column + " turned into negative " + (index - 1));
		}
		return index - 1;
	}
}
