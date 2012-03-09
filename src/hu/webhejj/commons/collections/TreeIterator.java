/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons.collections;

import hu.webhejj.commons.collections.TreeWalker.ChildProvider;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

public class TreeIterator<T> implements Iterator<T> {

	protected class TreeNodePos {
		protected T value;
		protected Iterator<T> childIterator;
	
		public TreeNodePos(T value) {
			this.value = value;
		}
		@Override
		public String toString() {
			return value + " " + (childIterator == null ? "null" : childIterator.hasNext());
		}
	}
	
	private final ChildProvider<T> childProvider;
	private final Stack<TreeNodePos> posStack = new Stack<TreeNodePos>();
	
	public TreeIterator(T root, ChildProvider<T> childProvider) {
		this.childProvider = childProvider;
		posStack.push(new TreeNodePos(root));
	}

	@Override
	public boolean hasNext() {
		return posStack.size() > 0;
	}

	@Override
	public T next() {
		
		if(!hasNext()) {
			throw new NoSuchElementException();
		}
		
		TreeNodePos next = posStack.peek();
		
		if(next.childIterator == null) {
			Iterable<T> children = childProvider.getChildren(next.value);
			if(children != null) {
				next.childIterator = children.iterator();
				if(next.childIterator.hasNext()) {
					posStack.push(new TreeNodePos(next.childIterator.next()));
				} else {
					goUpToParent();
				}
			} else {
				goUpToParent();
			}
		
		} else if(next.childIterator.hasNext()){
			posStack.push(new TreeNodePos(next.childIterator.next()));
		
		} else {
			goUpToParent();
		}
		
		return next.value;
	}
	
	protected void goUpToParent() {
		if(posStack.size() > 0) {
			posStack.pop();
			if(posStack.size() > 0) {
				TreeNodePos parent = posStack.peek();
				if(parent.childIterator != null && parent.childIterator.hasNext()) {
					posStack.push(new TreeNodePos(parent.childIterator.next()));
				} else {
					goUpToParent();
				}
			}
		}
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("remove() not supported");
	}
}
