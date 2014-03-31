/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons.io;

/**
 * Unchecked IO Exception
 * 
 * @author Gergely Nagy <greg@webhejj.hu>
 *
 */
public class RuntimeIOException extends RuntimeException {

	private static final long serialVersionUID = 623643776314085248L;

	public RuntimeIOException() {
	}

	public RuntimeIOException(String message) {
		super(message);
	}

	public RuntimeIOException(Throwable cause) {
		super(cause);
	}

	public RuntimeIOException(String message, Throwable cause) {
		super(message, cause);
	}
}
