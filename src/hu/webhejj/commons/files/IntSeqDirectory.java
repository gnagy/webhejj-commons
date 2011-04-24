/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons.files;

import hu.webhejj.commons.text.StringUtils;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A sequential directory where entries are created in numerical order.
 *
 */
public class IntSeqDirectory extends SeqDirectory {

	private final int splitSize;
	private final int levels;
	private final String keyFormat;

	private AtomicInteger largestKey;
	
	public IntSeqDirectory(File base) {
		this(base, 3, 4);
	}
	
	public IntSeqDirectory(File base, int splitSize, int levels) {
		super(base);
		this.splitSize = splitSize;
		this.levels = levels;
		keyFormat = "%0" + (splitSize * levels) + "d";
	}
	
	public IntSeqDirectory(Directory base, String name) {
		this(new File(base.getBase(), name));
	}

	public IntSeqDirectory(Directory base, String name, int splitSize, int levels) {
		this(new File(base.getBase(), name), splitSize, levels);
	}

	@Override
	protected void init() {
		super.init();
		largestKey = new AtomicInteger(0);
	}
	
	@Override
	protected void restore() {
		super.restore();
		
		// scan for largest key
		int index = 0;
		File file = base;
		for(int i = levels - 1; i >= 0; i--) {
			file = FileUtils.getLastAlphabetical(FileUtils.getSubdirectories(file));
			if(file == null) {
				break;
			}
			Integer intValue = Integer.valueOf(file.getName());
			index += (int) (intValue * Math.pow(10, splitSize * i));
		}
		largestKey = new AtomicInteger(index);
	}
	
	protected void doScanMaxDirs(StringBuilder buf, File base) {
		File maxFile = FileUtils.getLastAlphabetical(FileUtils.getSubdirectories(base));
		if(maxFile != null) {
			doScanMaxDirs(buf, maxFile);
			buf.append(maxFile.getName());
		}
	}

	@Override
	protected String getLargestKey() {
		return String.format(keyFormat, largestKey);
	}

	@Override
	protected String getNextKey() {
		return String.format(keyFormat, largestKey.addAndGet(1));
	}

	@Override
	protected String[] splitKey(String key) {
		return StringUtils.chop(key, splitSize);
	}
	
	/** @return crates and returns new sub-directory with the next highest value as name */
	public File createNextDir() {
		String[] path = splitKey(getNextKey());
		File file = base;
		for(int i = 0; i < path.length; i++) {
			file = new File(file, path[i]);
		}
		file.mkdirs();
		return file;
	}
}
