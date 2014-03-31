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

	void begin(String name, int max);
	
	void progress(int amount);
	
	void done();

	void cancel();
	
	boolean isCanceled();
}
