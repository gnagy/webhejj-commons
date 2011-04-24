/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons.xml;

import java.io.IOException;
import java.io.Writer;
import java.util.Stack;

/**
 * Simple indented XML Writer that only allows attribute values
 */
public class SimpleXmlWriter implements XmlWriter {

	private int indent = 4;
	private Stack<String> elements = new Stack<String>();
	private boolean isOpen = false;
	private boolean isLeaf = false;

	private Writer writer;
	
	public SimpleXmlWriter(Writer writer) throws IOException {
		this.writer = writer;
		
		writer.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
	}
	
	public void startElement(String name) throws IOException {
		
		if(isOpen) {
			writer.write(">\n");
		}
		
		indent();
		elements.push(name);
		writer.write("<");
		writer.write(name);
		isOpen = true;
		isLeaf = true;
	}

	public void endElement() throws IOException {
		
		if(elements.size() > 0) {

			String element = elements.pop();
			
			if(isOpen) {
				if(isLeaf) {
					writer.write("/>\n");
				} else {
					writer.write(">\n");
				}
			} else { 
				indent();
				writer.write("</");
				writer.write(element);
				writer.write(">\n");
			}
			isOpen = false;
			isLeaf = false;
		}
	}

	public void writeAttribute(String name, String value) throws IOException {
		writer.write(" ");
		writer.write(name);
		writer.write("=\"");
		writer.write(value);
		writer.write("\"");
	}
	
	public void close() throws IOException {
		while(elements.size() > 0) {
			endElement();
		}
		writer.close();
	}

	public void flush() throws IOException {
		writer.flush();
	}

	protected void indent() throws IOException {
		for(int i = 0; i < elements.size(); i++) {
			for(int j = 0; j < indent; j++) {
				writer.write(' ');
			}
		}
	}
}
