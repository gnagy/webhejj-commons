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
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
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
	
	public SimpleXmlWriter(OutputStream os) throws IOException {
		this.writer = new OutputStreamWriter(os, Charset.forName("UTF-8"));
		
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
		writeEscaped(name);
		writer.write("=\"");
		writeEscaped(value);
		writer.write("\"");
	}
	
	public void writeEscaped(String string) throws IOException {
		if(string == null) {
			return;
		}
		for(int i = 0; i < string.length(); i++) {
			char c = string.charAt(i);
			switch(c) {
			case '<': writer.write("&lt;"); break;
			case '>': writer.write("&gt;"); break;
			case '&': writer.write("&amp;"); break;
			case '"': writer.write("&quot;"); break;
			case '\'': writer.write("&apos;"); break;
			default: writer.write(c);
			}
		}
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

	@Override
	public void writeText(String text) throws IOException {
		if(isOpen) {
			writer.write(">");
			isOpen = false;
			isLeaf = true;
		}
		writeEscaped(text);
	}
}
