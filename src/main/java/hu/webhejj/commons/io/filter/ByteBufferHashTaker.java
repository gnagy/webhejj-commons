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

import java.nio.ByteBuffer;

/**
 * A ByteBufferFilter that calculates the hash of the bytes that
 * passed through
 *
 */
public class ByteBufferHashTaker implements ByteBufferFilter {

	private final Hasher hasher;
	private byte[] hash;
	
	public ByteBufferHashTaker(Hasher hasher) {
		this.hasher = hasher;
	}

	
	@Override
	public void reset() {
		hasher.reset();
		hash = null;
	}

	@Override
	public void process(ByteBuffer buffer) {
		hasher.getDigester().update(buffer);
	}
	
	public Hasher getHasher() {
		return hasher;
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
		if(hash == null) {
			hash = hasher.getDigester().digest();
			if(hasher.getSalt() != null) {
				hasher.getDigester().update(hasher.getSalt());
			}
		}
		return hash;
	}
}
