/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons.io;

import java.io.ByteArrayInputStream;

public class SeekableByteArrayInputStream extends ByteArrayInputStream {

	public SeekableByteArrayInputStream(byte[] buf) {
		super(buf);
	}

	public SeekableByteArrayInputStream(byte[] buf, int offset, int length) {
		super(buf, offset, length);
	}
	
	public void seek(int pos) {
		if(pos < 0 || pos >= count) {
			throw new IndexOutOfBoundsException(String.format("%d is out of range of [0,%d)", pos, count));
		}
		this.pos = pos;
	}
}
