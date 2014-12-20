package org.whuims.irlab.viper.extractor;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Cleaner {
	public Node cleanup(Element e) {
		NodeList c = e.getChildNodes();
		for (int i = 0; i < c.getLength(); i++) {
			if (c.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element t = (Element) c.item(i);
				if (Utility.isInvalidElement(t)) {
					e.removeChild(c.item(i));
				} else {
					cleanup(t);
				}
			}
		}
		return e;
	}
}
