/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons.io.filter;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * A FilterOutputStream that counts the total number of bytes
 * that passed through
 *
 */
public class OutputStreamSizeTaker extends FilterOutputStream {

	private long size;
	
	public OutputStreamSizeTaker(OutputStream out) {
		super(out);
	}
	
	@Override
	public void write(int b) throws IOException {
		size++;
		super.write(b);
	}
	
	@Override
	public void write(byte[] b) throws IOException {
		if(b != null) {
			size += b.length;
		}
		super.write(b);
	}
	
	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		size += len;
		super.write(b, off, len);
	}
	
	public long getSize() {
		return size;
	}
	
	public void setOutputStream(OutputStream os) {
		out = os;
		size = 0L;
	}
}
