/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons.io.table;

import hu.webhejj.commons.CompareUtils;

import java.io.IOException;
import java.io.Writer;
import java.util.regex.Pattern;

/**
 * 
 * TableAppender for comma separated format
 * 
 * @author Gergely Nagy <greg@webhejj.hu>
 *
 */
public class CsvTableAppender implements TableAppender {
	
	private static final Pattern PATTERN_ESCAPED_CHARS = Pattern.compile("[,\"]");

	private final Writer writer;
	private int columnCount = 0;
	
	public CsvTableAppender(Writer writer) {
		this.writer = writer;
	}

	@Override
	public void append(String value) {
		try {
			if(columnCount++ > 0) {
					writer.write(",");
			}
			
			if(CompareUtils.isEmpty(value)) {
				return;
			}
			
			boolean escaped = PATTERN_ESCAPED_CHARS.matcher(value).find();

			if(escaped) {
				writer.write('"');
				for(int i = 0; i < value.length(); i++) {
					char c = value.charAt(i);
					if(c == '"') {
						writer.write("\\");
					}
					writer.write(c);
				}
				writer.write('"');
			
			} else {
				writer.write(value);
			}
			
		} catch (IOException e) {
			throw new RuntimeException("Error while writing " + value, e);
		}
	}

	@Override
	public void newRow() {
		try {
			writer.write("\n");
		} catch (IOException e) {
			throw new RuntimeException("Error while writing newline character", e);
		}
		columnCount = 0;
	}

}
