/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons.diff;

/**
 * Difference carries the result of the comparison of two collections.
 * 
 * @param <T> type of differentiated objects
 */
public class Difference<T> {

	/** Type of difference */
	public static enum Type {
		/** means the element is present in both collections unchanged */
		UNCHANGED,
		/** means the element is present in both collections but was changed */
		CHANGED,
		/** means the element is not present in the "left" collection but present in the "right" collection */
		ADDED,
		/** means the element is present in the "left" collection but not present in the "right" collection */
		DELETED
	}
	
	private T left;
	private T right;
	private Type type;
	
	/**
	 * @param left left value
	 * @param right right value
	 * @param type type of difference
	 */
	public Difference(T left, T right, Type type) {
		this.left = left;
		this.right = right;
		this.type = type;
	}
	
	public T getLeft() {
		return left;
	}
	public void setLeft(T left) {
		this.left = left;
	}
	public T getRight() {
		return right;
	}
	public void setRight(T right) {
		this.right = right;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return type + "\t" + left + "\t" + right;
	}
}
