package org.whuims.irlab.viper.extractor;

import org.w3c.dom.Document;

/**
 * 这里给出了程序流程 一、对获得的Document进行清理，去除没有文本、图像、视屏的部分。
 * 
 * @author Administrator
 * 
 */
public class DocAnalyzer {
	Document doc = null;
	String title = "";
	String mainContent = "";
   
	public DocAnalyzer(Document doc) {
		this.doc = doc;
	
	}

	public String analyze() {
		// 做一些简单的清理工作，主要是去除了scrip元素
		TextExtractor extractor = new TextExtractor(doc);
		
		if (doc != null) {
			mainContent = extractor.extract();
			return mainContent;
		} else {
			return "未能成功解析文本";
		}

	}
}
