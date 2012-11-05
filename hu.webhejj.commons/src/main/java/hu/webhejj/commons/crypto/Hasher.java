/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons.crypto;

import hu.webhejj.commons.text.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * A stateful wapper around java.security.MessageDigest that can be configured
 * with an algorithm and an optional salt for performing hashing.
 * It is not thread-safe.
 */
public class Hasher {

	public static final String MD5 = "MD5";
	public static final String SHA_1 = "SHA-1";
	public static final String SHA_256 = "SHA-256";
	
	private MessageDigest digester;
	private byte[] salt;

	/**
	 * Construct a CryptUtils object with the specified algorithm
	 * (see static members of this class) and no salt
	 */
	public Hasher(String algorithm) {
		this(algorithm, (byte[]) null);
	}

	/**
	 * Construct a CryptUtils object with the specified algorithm
	 * (see static members of this class) and the specified salt
	 */
	public Hasher(String algorithm, String salt) {
		this(algorithm, StringUtils.decodeUtf8(salt));
	}

	/**
	 * Construct a CryptUtils object with the specified algorithm
	 * (see static members of this class) and the specified salt
	 */
	public Hasher(String algorithm, byte[] salt) {
		try {
			digester = MessageDigest.getInstance(algorithm);
			this.salt = salt;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Digest algorithm '" + algorithm + "' not found", e);
		}
	}
	
	/** @return the hash of the specified bytes */
	public byte[] hash(byte[] bytes) {
		reset(salt);
		return digester.digest(bytes);
	}

	/** @return the hash of the UTF-8 encoding of the specified string */
	public byte[] hash(String string) {
		return hash(StringUtils.decodeUtf8(string));
	}

	/** @return the hash of the specified file */
	public byte[] hash(File file) throws IOException {
		return hash(new FileInputStream(file));
	}
	
	/** @return the hash of the data read from the specified input stream */
	public byte[] hash(InputStream is) throws IOException {
		reset(salt);
		DigestInputStream dis = null;
		try {
			dis = new DigestInputStream(is, digester);
			byte[] buf = new byte[1024];
			while(dis.read(buf) >= 0) {};
			return dis.getMessageDigest().digest();
		} finally {
			if(dis != null) {
				dis.close();
			}
		}
	}
	
	/** resets the digester */
	public void reset() {
		reset(salt);
	}
	
	private void reset(byte[] salt) {
		this.salt = salt;
		digester.reset();
		if(salt != null) {
			digester.update(salt);
		}
	}

	/** @return the hashing algorithm */
	public String getHashAlgorithm() {
		return digester.getAlgorithm();
	}
	
	/** @return the salt, or null if no salt */
	public byte[] getSalt() {
		return salt;
	}
	
	/** @return the wrapped digester */
	public MessageDigest getDigester() {
		return digester;
	}
}
