/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons.collections;

import hu.webhejj.commons.CompareUtils;

/**
 * Utility methods for working with arrays.
 */
public class ArrayUtils {

	/** @return true if haystack contains needle */
	public static <T> boolean contains(T needle, T... haystack) {
		for(T hay: haystack) {
			if(CompareUtils.isEqual(hay, needle)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * add value to the end of array a
	 * @param a array to add to, may be null
	 * @param value value to add to a
	 * @return new array with value added to the end
	 */
	public static int[] addToIntArray(int[] a, int value) {
		
		if(a == null || a.length == 0) {
			return new int[] {value};
		}
		
		int[] array = new int[a.length + 1];
		array[a.length] = value;
		return array;
	}

	/**
	 * insert value into the sorted array a, at the index returned by java.util.Arrays.binarySearch()
	 * @param a array to add to, may be null
	 * @param value value to add to a
	 * @return new sorted array with value added
	 */
	public static int[] addToSortedIntArray(int[] a, int value) {
		
		if(a == null || a.length == 0) {
			return new int[] {value};
		}
		
		int insertionPoint = - java.util.Arrays.binarySearch(a, value) - 1;
		if(insertionPoint < 0) {
			throw new IllegalArgumentException(String.format("Element %d already exists in array", value));
		}
		
		int[] array = new int[a.length + 1];
		if(insertionPoint > 0) {
			System.arraycopy(a, 0, array, 0, insertionPoint);
		}
		array[insertionPoint] = value;
		if(insertionPoint < a.length) {
			System.arraycopy(a, insertionPoint, array, insertionPoint + 1, array.length - insertionPoint - 1);
		}
		
		return array;
	}
	
	/**
	 * removes first value found by linear search from array a
	 * @param a array to remove from
	 * @param value value to remove from array
	 * @return new array with value removed
	 */
	public static int[] removeFromIntArray(int[] a, int value) {
		
		if(a == null) {
			throw new NullPointerException("Array was null");
		}
		
		int index = -1;
		for(int i = 0; i < a.length; i++) {
			if(a[i] == value) {
				index = i;
				break;
			}
		}
		if(index < 0) {
			throw new IllegalArgumentException(String.format("Element %d not found in array", value));
		}
		
		int[] array = new int[a.length - 1];
		if(index > 0) {
			System.arraycopy(a, 0, array, 0, index);
		}
		if(index < a.length) {
			System.arraycopy(a, index + 1, array, index, array.length - index);
		}
		
		return array;
	}
	
	/**
	 * add value to the end of array a
	 * @param a array to add to, may be null
	 * @param value value to add to a
	 * @return new array with value added to the end
	 */
	public static char[] addToCharArray(char[] a, char value) {
		
		if(a == null || a.length == 0) {
			return new char[] {value};
		}
		
		char[] array = new char[a.length + 1];
		array[a.length] = value;
		return array;
	}
	
	/**
	 * insert value into the sorted array a, at the index returned by java.util.Arrays.binarySearch()
	 * @param a array to add to, may be null
	 * @param value value to add to a
	 * @return new sorted array with value added
	 */
	public static char[] addToSortedCharArray(char[] a, char value) {
		
		if(a == null || a.length == 0) {
			return new char[] {value};
		}
		
		int insertionPochar = - java.util.Arrays.binarySearch(a, value) - 1;
		if(insertionPochar < 0) {
			throw new IllegalArgumentException(String.format("Element %d already exists in array", value));
		}
		
		char[] array = new char[a.length + 1];
		if(insertionPochar > 0) {
			System.arraycopy(a, 0, array, 0, insertionPochar);
		}
		array[insertionPochar] = value;
		if(insertionPochar < a.length) {
			System.arraycopy(a, insertionPochar, array, insertionPochar + 1, array.length - insertionPochar - 1);
		}
		
		return array;
	}
	
	/**
	 * removes first value found by linear search from array a
	 * @param a array to remove from
	 * @param value value to remove from array
	 * @return new array with value removed
	 */
	public static char[] removeFromCharArray(char[] a, char value) {
		
		if(a == null) {
			throw new NullPointerException("Array was null");
		}
		int index = -1;
		for(char i = 0; i < a.length; i++) {
			if(a[i] == value) {
				index = i;
				break;
			}
		}
		if(index < 0) {
			throw new IllegalArgumentException(String.format("Element %d not found in array", value));
		}
		
		char[] array = new char[a.length - 1];
		if(index > 0) {
			System.arraycopy(a, 0, array, 0, index);
		}
		if(index < a.length) {
			System.arraycopy(a, index + 1, array, index, array.length - index);
		}
		
		return array;
	}		
}
