/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons;

import java.util.List;
import java.util.Stack;

/**
 * Abstract template class for building hierarchies of items that consist of segments.
 *
 * <p>For example, an Iterable of '/' separated paths can be converted to a nested
 * XML structure. See unit test code.</p>
 *
 * @param <T> item type
 */
public abstract class Hierarchizer<T> {

	private final Stack<String> stack = new Stack<String>();
	
	public void hierarchize(Iterable<T> items) {
		
		stack.clear();
		for(T item: items) {
			
			int i = 0;
			List<String> segments = getSegments(item);
			
			for(i = 0; i < stack.size() && i < segments.size(); i++) {
				if(!stack.get(i).equals(segments.get(i))) {
					break;
				}
			}
			
			for(int from = i, to = stack.size(); from < to; from++) {
                onPop(stack, stack.pop());
			}
			
			for(int from = i, to = segments.size(); from < to; from++) {
				onPush(stack, segments.get(from), item);
                stack.push(segments.get(from));
			}
		}
		
		while(stack.size() > 0) {
			onPop(stack, stack.pop());
		}
	}

	protected abstract List<String> getSegments(T item);
	protected abstract void onPush(Stack<String> stack, String segment, T item);
	protected abstract void onPop(Stack<String> stack, String segment);
}
