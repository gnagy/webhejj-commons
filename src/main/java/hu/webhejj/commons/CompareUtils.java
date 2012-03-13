/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons;

import java.util.Collection;
import java.util.Comparator;

import hu.webhejj.commons.text.StringUtils;

/**
 * Utility methods for making comparisions
 */
public class CompareUtils {
	
	/** @return true if specified value is null, empty string or containing only whitespace or empty collection */
	public static boolean isEmpty(Object o) {
		
		if(o == null) {
			return true;
		}
		
		if(o instanceof String) {
			return ((String) o).trim().length() == 0;
		
		} else if (o instanceof Collection<?>) {
			return ((Collection<?>) o).size() == 0;
		}
		
		return false;
	}
	
	/** @return true if <code>o1 == o2 == null</code> or <code>o1.equals(o2)</code> */
	public static boolean isEqual(Object o1, Object o2) {
		return o1 == null ? o2 == null : o1.equals(o2);
	}
	
	/** @return true if <code>o1 != null && o2 != null</code> or <code>o1.equals(o2)</code>, or if String, not empty */
	public static boolean isEqualNotEmpty(Object o1, Object o2) {
		// neither can be null
		if(o1 == null || o2 == null) {
			return false;
		// o1 cannot be empty string
		} else if (o1 instanceof String && StringUtils.isEmpty((String) o1)) {
			return false;
		// o2 cannot be empty string
		} else if (o2 instanceof String && StringUtils.isEmpty((String) o2)) {
			return false;
		// they must be equal
		} else {
			return o1.equals(o2);
		}
	}

	/** @return true if <code>o1 != null && o2 != null</code> or <code>!o1.equals(o2)</code>, or if String, not empty */
	public static boolean isNotEqualNotEmpty(Object o1, Object o2) {
		// neither can be null
		if(o1 == null || o2 == null) {
			return false;
		// o1 cannot be empty string
		} else if (o1 instanceof String && StringUtils.isEmpty((String) o1)) {
			return false;
		// o2 cannot be empty string
		} else if (o2 instanceof String && StringUtils.isEmpty((String) o2)) {
			return false;
		// they must be equal
		} else {
			return !o1.equals(o2);
		}
	}

	/** @return true if <code>o1 == null || o2 == null</code> or <code>o1.equals(o2)</code>, or if String, both empty */
	public static boolean isEqualOrEmpty(Object o1, Object o2) {
		// can be null
		if(o1 == null || o2 == null) {
			return true;
		// can be both empty
		} else if (o1 instanceof String && StringUtils.isEmpty((String) o1) && o2 instanceof String && StringUtils.isEmpty((String) o2)) {
			return true;
		// they must be equal
		} else {
			return o1.equals(o2);
		}
	}
	
	/** @return true if any of <code>objects</code> is empty, false otherwise */
	public static boolean isAnyEmpty(Object... objects) {
		for(Object object: objects) {
			if(isEmpty(object)) {
				return true;
			}
		}
		return false;
	}
	
	/** @return true if all of <code>objects</code> are empty, false otherwise */
	public static boolean isAllEmpty(Object... objects) {
		for(Object object: objects) {
			if(!isEmpty(object)) {
				return false;
			}
		}
		return true;
	}
	
	/** null-save object compare */
	public static <T extends Comparable<T>> int compare(T o1, T o2) {
		if(o1 == null) {
			return o2 == null ? 0 : 1;
		} else if(o2 == null) {
			return -1;
		} else {
			return o1.compareTo(o2);
		}
	}

	/** null-save object compare */
	public static <T> int compare(T o1, T o2, Comparator<T> comparator) {
		if(o1 == null) {
			return o2 == null ? 0 : 1;
		} else if(o2 == null) {
			return -1;
		} else {
			return comparator.compare(o1, o2);
		}
	}

}
