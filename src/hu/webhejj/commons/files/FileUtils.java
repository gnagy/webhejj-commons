/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons.files;

import hu.webhejj.commons.Asserts;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Utility methods for working with files
 */
public class FileUtils {

	/**
	 * @return return the file with the largest last modified
	 * time stamp among the specified files
	 */
	public static File getLastModifiedFile(File[] files) {

		if(files == null || files.length == 0) {
			return null;
		}
		
		File lastScanFile = null;
		long maxLastModified = 0;
		for(File scanFile: files) {
			long lastModified = scanFile.lastModified();
			if(maxLastModified < lastModified) {
				lastScanFile = scanFile;
				maxLastModified = lastModified;
			}
		}
		return lastScanFile;
	}
	
	/** 
	 * @return the last file of the specified files as if they were sorted in
	 * ascending alphabetical order 
	 */
	public static File getLastAlphabetical(File[] files) {
		
		if(files == null || files.length == 0) {
			return null;
		}
		
		File maxFile = null;
		String maxFileName = "";
		for(File scanFile: files) {
			if(maxFileName.compareTo(scanFile.getName()) < 0) {
				maxFile = scanFile;
				maxFileName = scanFile.getName();
			}
		}
		return maxFile;
	}
	
	/** @return array of files that are contained in base and are directories */
	public static File[] getSubdirectories(File base) {
		return base.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return file.isDirectory();
			}
		});
	}
	
	/** @return array of files that are contained in base and are files */
	public static File[] getSubfiles(File base) {
		return base.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return file.isFile();
			}
		});
	}
	
	/**
	 * Deletes specified file from the file system, recursively if directory
	 * 
	 * @param file file or directory to delete
	 * @return true if file existed and was deleted, false if file did not exist
	 */
	public static boolean deleteRecursive(File file) {
		
		if(!file.exists()) {
			return false;
		}
		
		if(file.isDirectory()) {
			for(File child: file.listFiles()) {
				deleteRecursive(child);
			}
		}
		return file.delete();
	}
	
	/** save data from the specified input stream to file */
	public static void copy(InputStream is, File file) throws IOException {

		Asserts.notNullParameter("is", is);
		Asserts.notNullParameter("file", file);
		
		FileOutputStream os = null;
		os = new FileOutputStream(file);
		copy(is, os);
	}
	
	/** copy data from the specified input stream to the specified output stream */
	public static void copy(InputStream is, OutputStream os) throws IOException {

		Asserts.notNullParameter("is", is);
		Asserts.notNullParameter("os", os);
		
		try {
			byte buf[] = new byte[1024];
			int len = 0;
			
			while((len = is.read(buf)) > 0) {
				os.write(buf, 0, len);
			}
		
		} finally {
			if(os != null) {
				try {
					os.close();
				} catch (IOException e) {
					// ignore
				}
			}
			if(is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// ignore
				}
			}
		}
	}
	
	/** @return free space available on the partition for the specified file */
	public static long getFreeSpace(File file) {
		
		long free = file.getFreeSpace();
		while(free == 0) {
			file = file.getParentFile();
		}
		return free;
	}
}
