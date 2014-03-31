/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons.text;

import java.io.UnsupportedEncodingException;

/**
 * Converts a string from one encoding to another. Useful if string was read
 * using the wrong encoding. 
 */
public class StringEncodingConverter {

	private String from;
	private String to;
	private boolean nop;
	
	
	/** construct a converter from the specified encoding to the specified encoding */
	public StringEncodingConverter(String from, String to) {
		
		if(from == null) {
			throw new NullPointerException("from parameter was null");
		}
		if(to == null) {
			throw new NullPointerException("to parameter was null");
		}
		
		// no conversion needed
		if(from.equals(to)) {
			nop = true;
		
		// set values
		} else {
			this.from = from;
			this.to = to;
		}
	}

	/** construct a converter from the system default encoding to the specified encoding */
	public StringEncodingConverter(String to) {
		this(System.getProperty("file.encoding"), to);
	}
	
	/** @return the specified string converted, or the same string object if no conversion is necessary */
	public String convert(String from) throws UnsupportedEncodingException {
		if(nop) {
			return from;
		}
		byte[] fromBytes = from.getBytes(this.from);
		return new String(fromBytes, this.to);
	}
}
