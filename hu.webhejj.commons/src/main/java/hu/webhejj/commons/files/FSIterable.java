/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons.files;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * Iterable that recursively traverses the file system and iterates over
 * directories and files in alphabetical order
 * 
 * @author greg
 *
 */
public class FSIterable implements Iterable<File> {

	private static class FileIteratorState {
		
		private static final File[] emptyFiles = new File[0];
		
		public FileIteratorState(File file) {
			index = 0;
			if(!file.exists()) {
				files = emptyFiles;
			} else if(file.isFile()) {
				files = new File[] { file };
			} else {
				files = file.listFiles();
				if(files == null) {
					files = emptyFiles;
				}
				if(files.length > 1) {
					Arrays.sort(files);
				}
			}
		}
		
		private File[] files;
		private int index;
		
		public boolean hasNext() {
			return index < files.length;
		}
		
		public File next() {
			return files[index++];
		}
	}
	
	public static class FSIterator implements Iterator<File> {

		private Stack<FileIteratorState> states = new Stack<FileIteratorState>(); 
		private File current;
		
		public FSIterator(File file) {
			states.push(new FileIteratorState(file));
		}

		@Override
		public boolean hasNext() {
			
			// discard states which have no more files
			while(states.size() > 0 && !states.peek().hasNext()) {
				states.pop();
			}
			return states.size() > 0;
		}

		@Override
		public File next() {
			if(hasNext()) {
				current = states.peek().next();
				if(current.isDirectory()) {
					states.push(new FileIteratorState(current));
				}
				return current;
			} else {
				throw new NoSuchElementException();
			}
		}

		@Override
		public void remove() {
			if(current == null) {
				throw new IllegalStateException();
			}
			FileUtils.deleteRecursive(current);
			current = null;
		}
	}
	
	private final File file;
	
	public FSIterable(File file) {
		this.file = file;
	}

	@Override
	public Iterator<File> iterator() {
		return new FSIterator(file);
	}
}
