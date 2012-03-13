/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons.io.filter;

import hu.webhejj.commons.crypto.Hasher;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * A FilterInputStream that calculates the hash of the bytes that
 * passed through
 *
 */
public class InputStreamHashTaker extends FilterInputStream {

	private final Hasher hasher;
	private byte[] hash = null;

	public InputStreamHashTaker(InputStream in, Hasher hasher) {
		super(in);
		this.hasher = hasher;
	}
	
	@Override
	public int read() throws IOException {
		int b = super.read();
		hasher.getDigester().update((byte) b);
		return b;
	}

	@Override
	public int read(byte[] b) throws IOException {
		int len = super.read(b);
		if(len > 0) {
			hasher.getDigester().update(b, 0, len);
		}
		return len;
	}
	
	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		int actualLen = super.read(b, off, len);
		if(len > 0) {
			hasher.getDigester().update(b, 0, actualLen);
		}
		return actualLen;
	}
	
	/**
	 * Calculates the hash value. Note, that since MessageDigest is reset when digest()
	 * is called, therefore this method cannot be called incrementally, i.e. while still
	 * processing the buffer. However, the hash value is cached in this filter,
	 * so the getHash() method can be safely called multiple times.
	 * 
	 * @return the hash value
	 */
	public byte[] getHash() {
		
		// digester.digest() resets the digester, so it cannot be called multiple times,
		// therefore we cache the hash
		if(hash  == null) {
			hash = hasher.getDigester().digest();
			if(hasher.getSalt() != null) {
				hasher.getDigester().update(hasher.getSalt());
			}
		}
		return hash;
	}
	
	public void setInputStream(InputStream is) {
		in = is;
		hasher.reset();
		hash = null;
	}
}
