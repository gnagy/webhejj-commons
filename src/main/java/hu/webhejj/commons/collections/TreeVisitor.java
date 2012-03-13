/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons.collections;

/**
 * An interface with callbacks for visiting trees.
 *
 * @param <T> node type of the tree
 */
public interface TreeVisitor<T> {
	
	/**
	 * Callback for visiting a node, before its children are visited.
	 * 
	 * @param node node being visited
	 * @return true if this node's children should be visited, false otherwise
	 */
	public boolean entering(T node);
	
	/**
	 * Callback after a node and all its children were visited.
	 * 
	 * @param node node being left
	 */
	public void leaving(T node);
}
