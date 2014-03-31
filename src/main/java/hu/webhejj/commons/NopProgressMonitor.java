/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons;

public class NopProgressMonitor implements ProgressMonitor {

	public static final NopProgressMonitor INSTANCE = new NopProgressMonitor();
	
	private boolean isCandeled;
	
	@Override
	public void begin(String name, int max) {
	}

	@Override
	public void progress(int amount) {
	}

	@Override
	public void done() {
	}

	@Override
	public void cancel() {
		isCandeled = true;
	}

	@Override
	public boolean isCanceled() {
		return isCandeled;
	}
}
