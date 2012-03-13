/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons;

/**
 * Like Eclipse's progress monitor, but without that dependency
 */
public interface ProgressMonitor {

	public void begin(String name, int max);
	
	public void progress(int amount);
	
	public void done();

	public void cancel();
	
	public boolean isCanceled();
}
