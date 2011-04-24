/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons;

import java.util.regex.Pattern;

/**
 * Utility methods for making assertions
 *
 */
public class Asserts {

	public static void notNullParameter(String parameterName, Object parameter) {
		if(parameter == null) {
			throw new NullPointerException("Parameter " + parameterName + " was null");
		}
	}
	
	public static void regexMatchesParameter(String parameterName,
			Pattern pattern, CharSequence value) {
		if(!pattern.matcher(value).matches()) {
			throw new IllegalArgumentException("Parameter " + parameterName +
					" does not match " + pattern + ", was: " + value);
		}
	}
}
