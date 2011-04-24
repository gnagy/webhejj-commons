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

import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Wrapper around javax.crypto.Cipher for encoding and decoding
 * data encoded as Base64 Strings using the blowfish algorithm.
 */
public class BlowfishCipher {
	
	public static final String ALGORITHM = "Blowfish";
	
	private final SecretKeySpec key;
	private final Cipher encoder;
	private final Cipher decoder;
	
	/** Constructs a blowfish cipher with the specified enrcryption key */
	public BlowfishCipher(byte[] keyBytes) throws GeneralSecurityException {
		
		key = new SecretKeySpec(keyBytes, ALGORITHM);

		encoder = Cipher.getInstance(ALGORITHM);
		encoder.init(Cipher.ENCRYPT_MODE, key);
		decoder = Cipher.getInstance(ALGORITHM);
		decoder.init(Cipher.DECRYPT_MODE, key);
	}

	/** @return Base64 encoding of the blowfish encrypted UTF-8 bytes of the specified string */
	public String encode(String secret) throws GeneralSecurityException {
		byte[] encoded = encoder.doFinal(StringUtils.decodeUtf8(secret));
		return StringUtils.encodeBase64(encoded);
	}

	/** @return The specified base64 encoded data decrypted using blowfish converted to an UTF-8 string */
	public String decode(String base64) throws GeneralSecurityException {
		byte[] encoded = StringUtils.decodeBase64(base64);
		byte[] decoded = decoder.doFinal(encoded);
		return StringUtils.encodeUtf8(decoded);
	}
}
