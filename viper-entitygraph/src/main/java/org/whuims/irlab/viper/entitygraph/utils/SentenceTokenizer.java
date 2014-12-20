/**
 * 对网页内容分句，按给定的句子结束符进行
 */
package org.whuims.irlab.viper.entitygraph.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.apache.lucene.analysis.Token;



/**
 * 
 * @author herochen
 * 
 * @version 2010-7-20
 */
public class SentenceTokenizer {
	
	public final static String PUNCTIONS = "。？！";
	public final static String SPACE = " \t\r\n";
	
	private StringBuffer buffer = new StringBuffer();
	
	private BufferedReader inputBuffer = null;
	
	private int tokenStart = 0, tokenEnd = 0;
	
	private Token token = new Token();
	
	public SentenceTokenizer(Reader reader){
		inputBuffer = new BufferedReader(reader, 2048);
	}
	
	public Token next() throws IOException{
		buffer.setLength(0);
		boolean atBegin = true;
		tokenStart = tokenEnd;
		
		int ci;
		char ch, pch;
		ci = inputBuffer.read();
		ch = (char) ci;
		while(true){
			if(ci == -1){
				break;
			}
			else if(PUNCTIONS.indexOf(ch) != -1){
				buffer.append(ch);
				tokenEnd++;
				break;
			}
			else if(atBegin && SPACE.indexOf(ch) != -1){
				tokenStart++;
				tokenEnd++;
				ci = inputBuffer.read();
				ch = (char)ci;
			}
			else{
				buffer.append(ch);
				tokenEnd++;
				atBegin = false;
				
				pch = ch;
				
				ci = inputBuffer.read();
				ch = (char)ci;
				
				if(SPACE.indexOf(pch) != -1 && SPACE.indexOf(ch) != -1){
					//inputBuffer.read();
					tokenEnd++;
					break;
				}
			}
		}
		if(buffer.length() == 0){
			return null;
		}else{
			token.clear();
			token.reinit(buffer.toString(), tokenStart, tokenEnd, "sentence");
			return token;
		}
	}
	
	public static void main(String arg[]) throws IOException{
		String content = "I have a dream.It is very huge!Ha,ha.";
		StringReader reader = new StringReader(content);
		SentenceTokenizer tokenizer = new SentenceTokenizer(reader);
		Token token = null;
		while((token = tokenizer.next()) != null){
			System.out.println(token.term() + "\t" + token.startOffset() + "\t" + token.endOffset());
		}
	}

}
