/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons.time;

/**
 * <p>Utility class for measuring time. Example usage:<p>
 * 
 * <pre>
 * long time = StopWatch.time();
 * someOperation();
 * time = StopWatch("Some operation", time);
 * anotherOperation();
 * StopWatch("Another operation", time);
 * </pre>
 */
public class StopWatch {

	/** @return current time in millisecods */
	public static long time() {
		return System.currentTimeMillis();
	}
	
	/** @log message message with time spent between now and start return current time */
	public static long time(String message, long start) {
		long time = System.currentTimeMillis();
		System.out.format("%s: %dms\n", message, time - start);
		return time; 
	}
}
