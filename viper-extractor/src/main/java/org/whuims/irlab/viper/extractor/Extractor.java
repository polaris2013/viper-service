package org.whuims.irlab.viper.extractor;

import org.w3c.dom.Document;

public class Extractor {
	String htmlCode = "";
	String urlString = "";
	String charset = "";
	private String title;
	private String mainContent;

	public Extractor(String htmlCode, String urlString, String charset) {
		super();
		this.htmlCode = htmlCode;
		this.urlString = urlString;
		this.charset = charset;
	}

	/** 提供一个不包含链接地址的构造方法，因为链接地址此处无用，只是用于获取htmlCode */
	public Extractor(String htmlCode, String charset){
		this.htmlCode = htmlCode;
		this.charset = charset;
	}
	public void parse() {
		HtmlDocumentPro htmlDoc = new HtmlDocumentPro(htmlCode, urlString,
				charset);
		Document document = htmlDoc.getDocument();
		this.title = htmlDoc.getTitle();
		DocAnalyzer analyzer = new DocAnalyzer(document);
		this.mainContent = analyzer.analyze();
	}

	public String getHtmlCode() {
		return htmlCode;
	}

	public String getUrlString() {
		return urlString;
	}

	public String getCharset() {
		return charset;
	}

	public String getTitle() {
		return title;
	}

	public String getMainContent() {
		return mainContent;
	}
	
}
