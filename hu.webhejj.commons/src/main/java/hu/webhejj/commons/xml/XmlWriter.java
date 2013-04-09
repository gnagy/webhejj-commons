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

/**
 * Interface for writing XML
 */
public interface XmlWriter {

	void startElement(String name) throws IOException;
	void endElement() throws IOException;
	
	void writeAttribute(String name, String value) throws IOException;
	void writeText(String text) throws IOException;
}
