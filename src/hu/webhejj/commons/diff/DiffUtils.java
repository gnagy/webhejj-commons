package hu.webhejj.commons.diff;

import hu.webhejj.commons.files.FSIterable;

import java.io.File;
import java.util.List;

public class DiffUtils {

	/** @return Differences in the two directories, recursively */
	public static List<Difference<File>> diffFilesLastModified(File left, File right) {
		return new SortedIterableDifferentiator<File>().diff(
				new FSIterable(left),
				new FSIterable(right),
				FileLastModifiedDiffComparator.INSTANCE);
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
