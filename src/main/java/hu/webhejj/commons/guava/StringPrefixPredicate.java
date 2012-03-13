/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons.guava;

import com.google.common.base.Predicate;

/**
 * A Google guava predicate that matches strings beginning
 * with the specified prefix, optionally ignoring case
 *
 */
public class StringPrefixPredicate implements Predicate<String> {
	
	public static final boolean CASE_SENSITIVE = false;
	public static final boolean CASE_INSENSITIVE = true;
	
	private final boolean ignoreCase;
	private final String prefix;
	
	public StringPrefixPredicate(String prefix) {
		this(prefix, CASE_INSENSITIVE);
	}
	
	public StringPrefixPredicate(String prefix, boolean ignoreCase) {
		this.prefix = prefix;
		this.ignoreCase = ignoreCase;
	}

	public boolean apply(String string) {
		return string.regionMatches(ignoreCase, 0, prefix, 0, prefix.length());
	}
}
