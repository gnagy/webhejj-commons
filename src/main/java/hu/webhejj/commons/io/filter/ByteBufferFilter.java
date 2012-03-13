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
 * Inteface for filters operating on ByteBuffers
 *
 */
public interface ByteBufferFilter {

	public void reset();
	public void process(ByteBuffer buffer);
}
