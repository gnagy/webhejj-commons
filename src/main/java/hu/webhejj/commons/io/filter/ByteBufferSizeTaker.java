/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons.io.filter;

import java.nio.ByteBuffer;

/**
 * A ByteBufferFilter that counts the total number of bytes
 * that passed through
 *
 */
public class ByteBufferSizeTaker implements ByteBufferFilter {

	private long size;

	@Override
	public void reset() {
		size = 0L;
	}

	@Override
	public void process(ByteBuffer buffer) {
		size += buffer.remaining();
	}

	public long getSize() {
		return new Long(size);
	}
}
