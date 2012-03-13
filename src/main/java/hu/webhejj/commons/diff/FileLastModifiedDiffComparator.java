package hu.webhejj.commons.diff;

import java.io.File;

import hu.webhejj.commons.files.FileNameComparator;

public class FileLastModifiedDiffComparator extends FileNameComparator implements DiffComparator<File> {

	public static final FileLastModifiedDiffComparator INSTANCE = new FileLastModifiedDiffComparator();
	
	@Override
	public boolean equals(File left, File right) {
		return left.lastModified() == right.lastModified();
	}
}
