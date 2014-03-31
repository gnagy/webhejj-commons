/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons.text;

public class CharSequenceTokenizer implements CharSequence {

	private char separator;
	private CharSequence buf;
	private int start;
	private int end;
		
	public CharSequenceTokenizer(char separator) {
		this.separator = separator;
	}

	public void reset(CharSequence buf) {
		this.buf = buf;
		start = 0;
		end = 0;
	}
	
	public boolean next() {
		if(end > 0) {
			start = ++end;
		}
		if(end >= buf.length()) {
			return false;
		}
		for(;;) {
			if(buf.length() == end || separator == buf.charAt(end)) {
				return true;
			}
			end++;
		}
	}
	
	public CharSequenceTokenizer assertNextToken(String name) {
		if(!next()) {
			throw new RuntimeException("No more tokens, expected: " + name);
		}
		return this;
	}	
	
	public int getStart() {
		return start;
	}
	
	public int getEnd() {
		return end;
	};
	
	@Override
	public int length() {
		return end - start;
	}

	@Override
	public char charAt(int index) {
        if ((index < 0) || (index >= length())) {
            throw new StringIndexOutOfBoundsException(index);
        }
        return buf.charAt(start + index);
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		return buf.subSequence(this.start + start, this.start + end);
	}
	
	@Override
	public String toString() {
		return buf.subSequence(start, end).toString();
	}
}

