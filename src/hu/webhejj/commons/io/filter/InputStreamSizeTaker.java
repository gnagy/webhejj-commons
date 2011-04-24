/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons.io.filter;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * A FilterInputStream that counts the total number of bytes
 * that passed through
 *
 */
public class InputStreamSizeTaker extends FilterInputStream {

	public InputStreamSizeTaker(InputStream in) {
		super(in);
	}

	private long size;
	
	@Override
	public int read() throws IOException {
		size++;
		return super.read();
	}
	
	@Override
	public int read(byte[] b) throws IOException {
		if(b != null) {
			size += b.length;
		}
		return super.read(b);
	}
	
	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		size += len;
		return super.read(b, off, len);
	}
	
	public long getSize() {
		return size;
	}
	
	public void setInputStream(InputStream is) {
		in = is;
		size = 0L;
	}
}
