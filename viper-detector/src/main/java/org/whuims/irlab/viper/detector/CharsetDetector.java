/**
 * 该类用于探测网页的编码格式，具体方法为：
 * <p>先获取链接对应的网页连接，读取http返回head信息，解析head中的编码描述</p>
 * <p>如果head未标识，则读取网页前面一段代码，获取meta信息，解析编码格式</p>
 * <p>仍未找到，默认为gb2312</p>
 */
package org.whuims.irlab.viper.detector;

import info.monitorenter.cpdetector.io.ASCIIDetector;
import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;
import info.monitorenter.cpdetector.io.ParsingDetector;
import info.monitorenter.cpdetector.io.UnicodeDetector;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




/**
 * 
 * 该类用于探测网页的编码格式，具体方法为：
 * <p>先获取链接对应的网页连接，读取http返回head信息，解析head中的编码描述</p>
 * <p>如果head未标识，则读取网页前面一段代码，获取meta信息，解析编码格式</p>
 * <p>仍未找到，默认为gb2312</p>
 * 
 * @author herochen
 *
 * version 2010-7-31
 */
public class CharsetDetector {
	private Logger log = LoggerFactory.getLogger(CharsetDetector.class);
	
	private String urlString = "";
	public CharsetDetector(String urlString){
		this.urlString = urlString;
	}

	public String detectJar(){
		/*------------------------------------------------------------------------  
		  detector是探测器，它把探测任务交给具体的探测实现类的实例完成。  
		  cpDetector内置了一些常用的探测实现类，这些探测实现类的实例可以通过add方法  
		  加进来，如ParsingDetector、 JChardetFacade、ASCIIDetector、UnicodeDetector。    
		  detector按照“谁最先返回非空的探测结果，就以该结果为准”的原则返回探测到的  
		  字符集编码。  
		--------------------------------------------------------------------------*/
		CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
		/*-------------------------------------------------------------------------  
		  ParsingDetector可用于检查HTML、XML等文件或字符流的编码,构造方法中的参数用于  
		  指示是否显示探测过程的详细信息，为false不显示。  
		---------------------------------------------------------------------------*/
		detector.add(new ParsingDetector(false));
		/*--------------------------------------------------------------------------  
		  JChardetFacade封装了由Mozilla组织提供的JChardet，它可以完成大多数文件的编码  
		  测定。所以，一般有了这个探测器就可满足大多数项目的要求，如果你还不放心，可以  
		  再多加几个探测器，比如下面的ASCIIDetector、UnicodeDetector等。  
		 ---------------------------------------------------------------------------*/
		detector.add(JChardetFacade.getInstance());
		// ASCIIDetector用于ASCII编码测定
		detector.add(ASCIIDetector.getInstance());
		// UnicodeDetector用于Unicode家族编码的测定
		detector.add(UnicodeDetector.getInstance());
		java.nio.charset.Charset charset = null;
		
		try{			
			URL url = new URL(urlString);
			charset = detector.detectCodepage(url);
		}catch(MalformedURLException e){
			e.printStackTrace();
			detector = null;
		}catch(IOException e){
			e.printStackTrace();
			detector = null;
		}
		 
		if (charset != null) {
			return charset.name();
		} else {
			return "gb2312";
		}
	}
	/**
	 * 获取链接对应的网页编码,在此处捕获所有异常，对于出现异常的链接，charset 赋值为gb2312
	 * 将所有的网络错误交由PageFetcher来处理
	 */
	public String detect(){
		String encoding="";
		InputStream in = null;
		HttpURLConnection conn = null;
		try {			
			conn = (HttpURLConnection) new URL(urlString).openConnection();
			setAsExplorer(conn);
			conn.setConnectTimeout(15000);
			conn.setReadTimeout(20000);
			in = conn.getInputStream();
			//尝试从http头中获取字符集
			String contentType = conn.getHeaderField("Content-Type").toLowerCase();
			boolean charsetFound = false;
			if (contentType.contains("charset=")) {
				//System.out.println(contentType);
				String[] encodings = contentType.split("charset=");
				if(encodings.length > 1){
					 encoding = contentType.split("charset=")[1];
					 charsetFound = true;
				}
			}
			//如果没有的话,读取头1024个字符，检查html的header
			byte[] buf = new byte[1024];
			if (!charsetFound) {
			    int len = in.read(buf);
			    while (len <= 32) {
			    	if(len == -1){
						return "gb2312";
					}
			        len = in.read(buf);
			    }
			    String header = new String(buf, 0, len);
			    Pattern p = Pattern.compile(".*<meta.*content=.*charset=.*", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
			    Matcher m = p.matcher(header);
			    if (m.matches()) {
			        encoding = header.toLowerCase().split("charset=")[1].replaceAll("[^a-z|1-9|\\-]", " ").split("\\s+")[0];
			    } else {
			        //如果没有的话，直接用gb2312解码
			        encoding = "gb2312";
			    }
			}
			/** 如果字符编码不是规定的网页编码格式，则统一用gb2312代替 */
			if(!encoding.equals("gb2312") && !encoding.equals("gbk") && !encoding.equals("GB2312") && !encoding.equals("GBK")
					&& !encoding.equals("utf8") && !encoding.equals("UTF8") && !encoding.equals("utf-8") && !encoding.equals("UTF-8") && !encoding.equals("GB18030")){
				encoding = "gb2312";
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			encoding = "gb2312";
			log.info(urlString + "\t" + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			encoding = "gb2312";
			log.info(urlString + "\t" + e.getMessage());
		} catch (Exception e){
			e.printStackTrace();
			encoding = "gb2312";
			log.info(urlString + "\t" + e.getMessage());
		}
		finally{
			try{
				if(in != null){
					in.close();
					in = null;
				}
			}catch(IOException e){
				e.printStackTrace();
			}
			try{
				conn.disconnect();
				conn = null;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return encoding;
	}
	
	private void setAsExplorer(URLConnection connection) {
		connection.setRequestProperty("User-Agent",
				"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)");
		connection
				.setRequestProperty(
						"Accept",
						"image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/x-shockwave-flash, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, */*");
		connection.setRequestProperty("Accept-Language", "zh-cn");
		connection.setRequestProperty("Accept-Encoding", "deflate");
		connection.setRequestProperty("Accept-Charset", "null");
		connection.setRequestProperty("Connection", "Keep-Alive");
	}
	
	public static void main(String arg[]){
		String url = "http://bbs.cnhan.com/thread-16409584-1-1.html";
		CharsetDetector detector = new CharsetDetector(url);
		try{
			String charset = detector.detect();
			System.out.println(charset);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
