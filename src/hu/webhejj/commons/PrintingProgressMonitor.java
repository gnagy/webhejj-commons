/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons;

import java.io.PrintStream;

/**
 * A progress monitor that writes to a PrintStream 
 */
public class PrintingProgressMonitor implements ProgressMonitor {

	private String name;
	private int max;
	private int progress;
	private PrintStream out;
	private boolean isCanceled = false;
	
	public PrintingProgressMonitor(PrintStream out) {
		this.out = out;
	}

	public void begin(String name, int max) {
		this.name = name;
		this.max = max;
		progress(0);
	}

	public void cancel() {
		out.print("Canceled ");
		out.println(name);
		isCanceled = true;
		progress = 0;
		max = 0;
	}

	public void done() {
		progress(max); 
	}

	public void progress(int amount) {
		progress = amount;
		
		int percent = (max == 0 ? 100 : progress * 100 / max); 
		
		out.print(name);
		out.print(": ");
		out.print(percent);
		out.println("%");
	}
	
	@Override
	public boolean isCanceled() {
		return isCanceled;
	}
}
