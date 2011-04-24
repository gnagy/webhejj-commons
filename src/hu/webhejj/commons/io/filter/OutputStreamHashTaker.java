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

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * A FilterOutputStream that calculates the hash of the bytes that
 * passed through
 *
 */
public class OutputStreamHashTaker extends FilterOutputStream {

	private final Hasher hasher;
	private byte[] hash = null;
	
	public OutputStreamHashTaker(OutputStream out, Hasher hasher) {
		super(out);
		this.hasher = hasher;
	}
	
	@Override
	public void write(int b) throws IOException {
		hasher.getDigester().update((byte) b);
		super.write(b);
	}
	
	@Override
	public void write(byte[] b) throws IOException {
		hasher.getDigester().update(b);
		super.write(b);
	}
	
	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		hasher.getDigester().update(b, off, len);
		super.write(b, off, len);
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
	
	public void setOutputStream(OutputStream os) {
		out = os;
		hasher.reset();
		hash = null;
	}
}
