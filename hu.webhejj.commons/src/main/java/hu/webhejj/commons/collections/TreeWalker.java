/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons.collections;

import hu.webhejj.commons.ProgressMonitor;

/**
 * Takes a TreeVisitor and a ChildProvider and walks a tree starting from the specified
 * root node.
 * 
 * @param <T> node type of the tree
 */
public class TreeWalker<T> {

	/**
	 * Adapter class that can return the children of a node
	 *
	 * @param <T> node type of the tree
	 */
	public interface ChildProvider<T> {
		
		/**
		 * @param node node to get children of
		 * @return Iterable containing children, can be empty or null if leaf node
		 */
		Iterable<T> getChildren(T node);
	}
	
	private TreeVisitor<T> visitor;
	private ChildProvider<T> childProvider;
	

	/**
	 * @param visitor visitor for the nodes
	 * @param childProvider adapter for returning the children of a node
	 */
	public TreeWalker(TreeVisitor<T> visitor, ChildProvider<T> childProvider) {
		this.visitor = visitor;
		this.childProvider = childProvider;
	}

	/** Start walking the tree starting from the specified node */
	public void walk(T root) {
		doWalk(root, null);
	}
	
	public void walk(T root, ProgressMonitor monitor) {
		doWalk(root, monitor);
	}
	
	protected void doWalk(T node, ProgressMonitor monitor) {
		
		if(visitor.entering(node)) {
			Iterable<T> childIterable = childProvider.getChildren(node);
			if(childIterable != null) {
				for(T child: childIterable) {
					if(monitor != null && monitor.isCanceled()) {
						return;
					}
					doWalk(child, monitor);
				}
			}
			visitor.leaving(node);
		}
	}
}
