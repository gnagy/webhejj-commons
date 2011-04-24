/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons.files;

import hu.webhejj.commons.collections.TreeVisitor;
import hu.webhejj.commons.collections.TreeWalker;

import java.io.File;
import java.util.Arrays;

/**
 * A TreeWalker that walks the file system
 * 
 */
public class FsWalker extends TreeWalker<File> {
	
	/**
	 * Provides files within a folder for tree walking, optionally sorted by name.
	 *
	 */
	public static class FileChildProvider implements ChildProvider<File> {
		
		private final FileNameComparator comparator;
		
		public FileChildProvider(boolean isSorted) {
			comparator = isSorted ? new FileNameComparator() : null; 
		}

		public Iterable<File> getChildren(File node) {
			if(node.isFile()) {
				return null;
			}
			File[] children = node.listFiles();
			if(comparator != null) {
				Arrays.sort(children, comparator);
			}
			return Arrays.asList(children);
		}
	}	
	
	public FsWalker(TreeVisitor<File> visitor, boolean isSorted) {
		super(visitor, new FileChildProvider(isSorted));
	}
}
