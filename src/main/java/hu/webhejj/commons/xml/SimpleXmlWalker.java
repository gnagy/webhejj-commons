/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons.xml;

import hu.webhejj.commons.collections.TreeVisitor;

import java.io.IOException;
import java.io.InputStream;
import java.text.Normalizer;
import java.text.Normalizer.Form;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SimpleXmlWalker {

	public static class Element {
		private String name;
		private Attributes attributes;
		
		public String getName() {
			return name;
		}
		public int getAttributeCount() {
			return attributes.getLength();
		}
		public boolean hasAttribute(String name) {
			return getAttribute(name) != null;
		}
		public String getAttribute(String name) {
			String value = attributes.getValue(name);
			// return value;
			return value == null ? null : Normalizer.normalize(value, Form.NFC);
		}
	}
	
	protected class XmlHandler extends DefaultHandler {
		
		private Element element = new Element();
		
		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			
			element.name = qName;
			element.attributes = attributes;
			visitor.entering(element);
		}
		
		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			element.name = qName;
			visitor.leaving(element);
		}
	}
	
	private final InputStream is;
	private final TreeVisitor<Element> visitor;

	public SimpleXmlWalker(InputStream is, TreeVisitor<Element> visitor) {
		this.is = is;
		this.visitor = visitor;
	}

	public void walk() {
		
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			XmlHandler xmlHandler = new XmlHandler();
			
			saxParser.parse(is, xmlHandler);
		
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
			
		} catch (SAXException e) {
			throw new RuntimeException(e);
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
