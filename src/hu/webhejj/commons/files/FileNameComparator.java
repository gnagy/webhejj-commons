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
import java.util.Comparator;

/**
 * Compares Files by their name
 */
public class FileNameComparator implements Comparator<File> {

	@Override
	public int compare(File o1, File o2) {
		return o1.getName().compareTo(o2.getName());
	}
}
