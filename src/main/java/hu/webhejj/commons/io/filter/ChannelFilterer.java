/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons.io.filter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.util.Arrays;
import java.util.List;

/**
 * Takes a list of ByteBufferFilters and applies them
 * to a File or ReadableByteChannel.
 *
 */
public class ChannelFilterer {
	
	private List<ByteBufferFilter> filters;
	
	public ChannelFilterer(ByteBufferFilter... filters) {
		this.filters = Arrays.asList(filters);
	}
	

	public void filter(File file, int bufSize) throws FileNotFoundException {
		filter(new FileInputStream(file).getChannel(), bufSize);
	}

	public void filter(ReadableByteChannel channel, int bufSize) {
		
		ByteBuffer buffer = ByteBuffer.allocate(bufSize);
		
		for (ByteBufferFilter filter : filters) {
			filter.reset();
		}
		
		while(true) {
			
			int read;
			try {
				read = channel.read(buffer);
			} catch (IOException e) {
				throw new RuntimeException("Error while reading from channel: ", e);
			}

			if(read < 0) {
				break;
			}
			
			reinitBuffer(buffer, read);
			
			for(ByteBufferFilter filter: filters) {
				int limit = buffer.limit();
				filter.process(buffer);
				ChannelFilterer.reinitBuffer(buffer, limit);
			}
		}
		
		try {
			channel.close();
		} catch (IOException e) {
			throw new RuntimeException("Error while closing channel: ", e);
		}
	}
	
	protected static void reinitBuffer(ByteBuffer buffer, int limit) {
		buffer.rewind();
		buffer.limit(limit);
	}
}
