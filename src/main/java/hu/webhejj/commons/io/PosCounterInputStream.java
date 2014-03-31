/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class PosCounterInputStream extends FilterInputStream {

	private long pos = 0;
	
	public PosCounterInputStream(InputStream in) {
		super(in);
		
	}

	@Override
	public int read() throws IOException {
		pos++;
		return super.read();
	}
	
	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		int read = super.read(b, off, len);
		pos += read;
		return read;
	}
	
	@Override
	public long skip(long n) throws IOException {
		long skipped = super.skip(n);
		pos += skipped;
		return skipped;
	}
	
	public long getPos() {
		return pos;
	}
}
