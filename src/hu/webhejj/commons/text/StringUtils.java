/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons.text;

import hu.webhejj.commons.CompareUtils;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;

public class StringUtils {
	
	public static final String UTF8 = "UTF-8";

	// replace multiple spaces with a single space
	public static final Pattern multiSpacePattern = Pattern.compile("  +");
	public static final Pattern mapCsvSplitterPattern = Pattern.compile("([^=,]+)=([^=,]+)[,]?");
	
	// mapping for hex encoding
	private static final byte[] HEX_CHAR_TABLE = {
		(byte) '0', (byte) '1', (byte) '2', (byte) '3',
		(byte) '4', (byte) '5', (byte) '6', (byte) '7',
		(byte) '8', (byte) '9', (byte) 'a', (byte) 'b',
		(byte) 'c',	(byte) 'd', (byte) 'e', (byte) 'f'
	};

	/** @return true if String is null or is the empty string after trimming */
	public static boolean isEmpty(String string) {
		return string == null || string.trim().length() == 0;
	}
	
	/** null-save string equals */
	public static boolean equals(String s1, String s2) {
		return CompareUtils.isEqual(s1, s2);
	}
	
	/** null-save string compare */
	public static int compare(String s1, String s2) {
		return CompareUtils.compare(s1, s2);
	}

	/** append values to buf separated with the specified separator */
	public static void join(StringBuilder buf, Iterable<?> values, String separator) {
		for (Iterator<?> i = values.iterator(); i.hasNext();) {
			buf.append(i.next());
			if (i.hasNext()) {
				buf.append(separator);
			}
		}
	}

	/** @return string values in the list separated by the specified <code>separator</code> */
	public static String join(Iterable<?> values, String separator) {
		StringBuilder buf = new StringBuilder();
		join(buf, values, separator);
		return buf.toString();
	}

	/** @return string values of objects in the list separated by ", ". Note: no escaping performed! */
	public static String joinCSV(Iterable<?> values) {
		return join(values, ", ");
	}

	/**
	 * Code from http://www.javalobby.org/java/forums/t15908.html
	 * 
	 * @return the Levenshtein distance of two Strings
	 */
	public static int compareLevenshtein(String s, String t) {

		int n = s.length();
		int m = t.length();

		if (n == 0) {
			return m;
		}
		if (m == 0) {
			return n;
		}

		int[][] d = new int[n + 1][m + 1];

		for (int i = 0; i <= n; d[i][0] = i++)
			;
		for (int j = 1; j <= m; d[0][j] = j++)
			;

		for (int i = 1; i <= n; i++) {
			char sc = s.charAt(i - 1);
			for (int j = 1; j <= m; j++) {
				int v = d[i - 1][j - 1];
				if (t.charAt(j - 1) != sc) {
					v++;
				}
				d[i][j] = Math.min(Math.min(d[i - 1][j] + 1, d[i][j - 1] + 1), v);
			}
		}
		return d[n][m];
	}

	/** @return a <code>String</code> with multiple consecutive spaces replaced with a single space. */
	public static String removeDoubleSpaces(String string) {
		Matcher matcher = multiSpacePattern.matcher(string);
		return matcher.replaceAll(" ");
	}

	/** @return a string representation of an enum set suitable for storage in a database */
	public static <E extends Enum<E>> String enumCollectionToString(Collection<E> enumSet) {

		if (enumSet != null && !enumSet.isEmpty()) {
			StringBuilder buf = new StringBuilder();
			buf.append(",");

			for (Enum<E> value : enumSet) {
				buf.append(value);
				buf.append(",");
			}

			return buf.toString();
		}

		return null;
	}
	
	/** @return specified bytes in Base64 encoding as UTF-8 string */
	public static String encodeBase64(byte[] bytes) {
		return encodeUtf8(Base64.encodeBase64(bytes));
	}
	/** @return specified UTF-8 string decoded using Base64 */
	public static byte[] decodeBase64(String string) {
		return Base64.decodeBase64(decodeUtf8(string));
	}	
	
	/** @return specified bytes encoded as string of hexadecimal digits */
	public static String encodeHex(byte[] bytes) {
		
		if(bytes == null) {
			return "";
		}
		try {
			byte[] hex = new byte[2 * bytes.length];
			int index = 0;

			for (byte b : bytes) {
				int v = b & 0xFF;
				hex[index++] = HEX_CHAR_TABLE[v >>> 4];
				hex[index++] = HEX_CHAR_TABLE[v & 0xF];
			}
			return new String(hex, "ASCII");
		} catch (UnsupportedEncodingException e) {
			// should never happen, but still
			throw new RuntimeException("Could not encode to hex", e);
		}
	}
	
	/** @return string of hexadecimal digits as byte array */
	public static byte[] decodeHex(String hex) {
		if(hex == null) {
			return null;
		}
		
		int len = hex.length();
		if ((len & 0x01) != 0) {
			throw new RuntimeException("Odd number of characters.");
		}
	
		byte[] out = new byte[len >> 1];
	
		// two characters form the hex value.
		for (int i = 0, j = 0; j < len; i++) {
			int f = toHexDigit(hex.charAt(j), j) << 4;
			j++;
			f = f | toHexDigit(hex.charAt(j), j);
			j++;
			out[i] = (byte) (f & 0xFF);
		}
	
		return out;	
	}

	protected static int toHexDigit(char ch, int index) throws RuntimeException {
		int digit = Character.digit(ch, 16);
		if (digit == -1) {
			throw new RuntimeException("Illegal hexadecimal charcter " + ch + " at index " + index);
		}
		return digit;
	}

	/** @return bytes converted to UTF-8 string, catching 
	 * UnsupportedEncodingException since this is a pretty basic encoding
	 */
	public static String encodeUtf8(byte[] bytes) {
		try {
			return bytes == null ? null : new String(bytes, UTF8);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Could not convert bytes to UTF-8 string", e);
		}
	}
	
	/** @return string converted to UTF-8 bytes, catching 
	 * UnsupportedEncodingException since this is a pretty basic encoding
	 */
	public static byte[] decodeUtf8(String string) {
		try {
			return string == null ? null : string.getBytes(UTF8);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Could get UTF-8 bytes of " + string, e);
		}
	}
	
	/**
	 * chop up strings to chunks of the specified length (last chunk may be shorter)
	 */
	public static String[] chop(String string, int length) {
		if(string.length() == 0) {
			return new String[0];
		}
		int chunkCount = string.length() / length + 1;
		String chunks[] = new String[chunkCount];

		for(int i = 0; i < chunkCount; i++) {
			chunks[i] = string.substring(i * length, Math.min(string.length(), (i + 1) * length));
		}
		return chunks;
	}
}
