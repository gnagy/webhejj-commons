/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons.diff;

import hu.webhejj.commons.NopProgressMonitor;
import hu.webhejj.commons.files.FSIterable;

import java.io.File;
import java.util.List;

public class DiffUtils {

	/** @return Differences in the two directories, recursively */
	public static List<Difference<File>> diffFilesLastModified(File left, File right) {
		DiffCollector<File> collector = new DiffCollector<File>();
		new SortedIterableDifferentiator<File>().diff(
				new FSIterable(left),
				new FSIterable(right),
				FileLastModifiedDiffComparator.INSTANCE,
				collector,
				NopProgressMonitor.INSTANCE);
		return collector.getDifferences();
	}
	
	/** prints to stdout all differences on a new line */
	public static <T> void dump(Iterable<Difference<T>> diffs) {
		for(Difference<T> diff: diffs) {
			System.out.println(diff);
		}
	}
	
	/** prints to stdout all UNCHANGED differences on a new line */
	public static <T> void dumpUnchanged(Iterable<Difference<T>> diffs) {
		for(Difference<T> diff: diffs) {
			if(Difference.Type.UNCHANGED.equals(diff.getType())) {
				System.out.println(diff);
			}
		}
	}
	
	/** prints to stdout all but UNCHANGED differences on a new line */
	public static <T> void dumpDiffering(Iterable<Difference<T>> diffs) {
		for(Difference<T> diff: diffs) {
			if(!Difference.Type.UNCHANGED.equals(diff.getType())) {
				System.out.println(diff);
			}
		}
	}	
}
