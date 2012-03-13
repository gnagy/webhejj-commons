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

/**
 * Base class for directories where entries are created in a sequence
 * 
 */
public abstract class SeqDirectory extends Directory {

	public SeqDirectory(File base) {
		super(base);
	}

	public SeqDirectory(Directory base, String name) {
		super(base, name);
	}

	protected abstract String getLargestKey();
	protected abstract String getNextKey();
	protected abstract String[] splitKey(String key);
}
