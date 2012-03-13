/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons.files;

import hu.webhejj.commons.files.FileUtils;

import java.io.File;

/**
 * Represents a directory in the local file system with some associated operations.
 * The underlying directory is created if does not exist yet, and state can be restored
 * if subclasses if it already exists.
 *
 */
public class Directory {

	/** File pointing to the underlying directory */
	protected final File base;

	/**
	 * Constructs a Directory with the specified base. If
	 * directory exists, restore() is called. If it doesn't
	 * exist, init() is called which by default calls base.mkdirs().
	 * 
	 * @param base base of this Directory
	 * @throws IllegalArgumentException if base exists but is not a directory
	 */
	public Directory(File base) {
		this.base = base;
		
		if(base.exists()) {
			if(!base.isDirectory()) {
				throw new IllegalArgumentException(base + " is not a directory");
			}
			restore();
		} else {
			init();
		}
	}

	/** 
	 * Constructs a directory within the specified base Directory
	 * 
	 * @param base base Directory
	 * @param name name of the child Directory
	 */
	public Directory(Directory base, String name) {
		this(new File(base.base, name));
	}
	
	
	/**
	 * Template method called when base does not exist yet. Creates base by default.
	 */
	protected void init() {
		base.mkdirs();
	}

	/**
	 * Template method called when base already exists. Does nothing by default.
	 */
	protected void restore() {
	}
	
	/**
	 * @return base of this directory as java.io.File
	 */
	public File getBase() {
		return base;
	}
	
	/**
	 * Return specified child of this directory as java.io.File
	 * @param name name of the child
	 * @return child as java.io.File
	 */
	public File getFile(String name) {
		return new File(base, name);
	}

	/**
	 * Deletes the specified child of this directory, recursively
	 * 
	 * @param name name of the child
	 * @return true if child was deleted, false otherwise 
	 */
	public boolean delete(String name) {
		return FileUtils.deleteRecursive(getFile(name));
	}
	
	/**
	 * Deletes all children of this directory
	 *
	 * @return true if at least one child was deleted, false otherwise 
	 */
	public boolean clear() {
		boolean result = false;
		for(File file: base.listFiles()) {
			result = result | FileUtils.deleteRecursive(file);
		}
		
		return result;
	}
}
