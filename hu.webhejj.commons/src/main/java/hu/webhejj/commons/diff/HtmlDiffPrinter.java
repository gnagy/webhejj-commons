/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons.diff;

import hu.webhejj.commons.CompareUtils;
import hu.webhejj.commons.collections.Histogram;
import hu.webhejj.commons.diff.Difference.Type;
import hu.webhejj.commons.xml.XmlWriter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map.Entry;

public class HtmlDiffPrinter<T> implements DiffHandler<T> {

	private XmlWriter out;
	private Difference.Type[] types;
	private Histogram<Type> typeHistogram;
	
	public HtmlDiffPrinter(XmlWriter out, Type... types) {
		this.out = out;
		this.types = types;
		typeHistogram = new Histogram<Difference.Type>();
		Arrays.sort(types);
	}
	
	public void begin() {
		try {
			out.startElement("html");
			out.startElement("head");
			out.startElement("style");
			out.writeText(".ADDED { background-color: #D0F5A9; }");
			out.writeText(".DELETED { background-color: #F5A9BC; }");
			out.writeText(".UNCHANGED { background-color: #CEE3F6; }");
			out.writeText(".CHANGED { background-color: #F4FA58; }");
			out.endElement();
			out.endElement();
			out.startElement("body");
			out.startElement("table");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void handle(T left, T right, Type type) {
		
		if(types != null && Arrays.binarySearch(types, type) < 0) {
			return;
		}
		typeHistogram.add(type);
		try {
			out.startElement("tr");
			out.writeAttribute("class", String.valueOf(type));
			
			if(Iterable.class.isAssignableFrom(left.getClass())) {
				Iterator<?> l = ((Iterable<?>) left).iterator();
				Iterator<?> r = ((Iterable<?>) right).iterator();
				
				out.startElement("td");
				out.writeText(String.valueOf(type));
				out.endElement();
				while(l.hasNext() || r.hasNext()) {
					String lv = l.hasNext() ? String.valueOf(l.next()) : null;
					String rv = r.hasNext() ? String.valueOf(r.next()) : null;
					
					out.startElement("td");
					if(CompareUtils.isEqual(lv, rv)) {
						out.writeAttribute("class", Type.UNCHANGED.toString());
						out.writeText(lv);
					} else {
						out.startElement("span");
						out.writeAttribute("class", Type.DELETED.toString());
						out.writeText(lv);
						out.endElement();
						out.startElement("span");
						out.writeAttribute("class", Type.ADDED.toString());
						out.writeText(rv);
						out.endElement();
					}
					out.endElement();
				}
				
			} else {
				out.startElement("td");
				out.writeText(String.valueOf(left));
				out.endElement();
				out.startElement("td");
				out.writeText(String.valueOf(right));
				out.endElement();
			}
			out.endElement();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void finish() {
		try {
			out.startElement("table");
			for(Entry<Type, Integer> entry: typeHistogram.entrySet()) {
				out.startElement("tr");
				out.startElement("td");
				out.writeText(entry.getKey().toString());
				out.endElement();
				out.startElement("td");
				out.writeText(entry.getValue().toString());
				out.endElement();
				out.endElement();
			}
			out.endElement();
			
			out.endElement();
			out.endElement();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
