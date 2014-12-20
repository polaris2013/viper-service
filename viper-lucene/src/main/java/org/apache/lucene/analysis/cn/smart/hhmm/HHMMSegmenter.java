/**
 * Copyright 2009 www.imdict.net
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.lucene.analysis.cn.smart.hhmm;

import java.util.List;

import org.apache.lucene.analysis.cn.smart.CharType;
import org.apache.lucene.analysis.cn.smart.Utility;
import org.apache.lucene.analysis.cn.smart.WordType;

public class HHMMSegmenter {
	
	private static WordDictionary wordDict = WordDictionary.getInstance();

	/**
	 * 寻找sentence中所有可能的Token，最后再添加两个特殊Token，"始##始",
	 * "末##末"，"始##始"Token的起始位置是-1,"末##末"Token的起始位置是句子的长度
	 * 
	 * @param sentence
	 *            输入的句子，不包含"始##始","末##末"等
	 * @param coreDict
	 *            核心字典
	 * @return 所有可能的Token
	 * @see MultiTokenMap
	 */
	public SegGraph createSegGraph(String sentence) {
		int i = 0, j;
		int length = sentence.length();
		int foundIndex;
//		System.out.println("\r\n----第一步，获得每个词的类型。使用静态方法getCharType()，进入方法");
		CharType[] charTypeArray = getCharTypes(sentence);
//		System.out.println("----第一步，完成\r\n");
		StringBuffer wordBuf = new StringBuffer();
		SegToken token;
		int frequency = 0; // word的出现次数
		boolean hasFullWidth;
		WordType wordType;
		char[] charArray;

		SegGraph segGraph = new SegGraph();

//		System.out.println("----第二步，开始，开始segGraph的真正创建过程。\r\n");
		// i不断向后平移
		while (i < length) {
			hasFullWidth = false;
			switch (charTypeArray[i]) {
			case SPACE_LIKE:
				i++;
				break;
			case HANZI:
				/**
				 * 如果这个char是汉字，那么将wordBuf清空，准备找词。
				 */
				j = i + 1;
				// 清空wordBuf
				wordBuf.delete(0, wordBuf.length());

				// 不管单个汉字能不能构成词，都将单个汉字存到segGraph中去，否则会造成分此图断字
				wordBuf.append(sentence.charAt(i));
				// 取出第i个char
				charArray = new char[] { sentence.charAt(i) };

				frequency = wordDict.getFrequency(charArray);
				token = new SegToken(charArray, i, j, WordType.CHINESE_WORD, frequency);
//				System.out.println("  ￥第" + i + "个char的情况： " + new String(charArray) + "  " + charArray.length + "  " + token.weight);

				// 这儿就添加到segGraph，意味着charArray里面的东西无论是否能成为词，都会放到segGraph中去。
				segGraph.addToken(token);

				foundIndex = wordDict.getPrefixMatch(charArray);

				// -------------------------关键的一个循环-------------------------
				// 从当前的char开始，添加后续的char，看能否构成词，一旦加入某个词不行，跳出循环。
//				System.out.println("\r\n@@-----关键的小循环-----");
//				System.out.println("从当前的char开始，添加后续的char，看能否构成词。");
				while (j <= length && foundIndex != -1) {
					if (wordDict.isEqual(charArray, foundIndex) && charArray.length > 1) {
						// 就是我们要找的词， 也就是说找到了从i到j的一个成词SegToken，并且不是单字词
						frequency = wordDict.getFrequency(charArray);
						token = new SegToken(charArray, i, j, WordType.CHINESE_WORD, frequency);
						segGraph.addToken(token);
//						System.out.println("  找到一个词汇：  " + new String(token.charArray) + "  " + token.wordType + "  " + token.weight + "---------------");
					}
					// 上面只是个判断，无论找到词没有，继续往后走，没找到词，继续走，看wordBuf里面的东西能否与后面的char构成词。
					// 找到了词，还有可能是更长的词的部分，如“北京”之于”北京大学“。

					// 意思空格，不管它，往后跳
					while (j < length && charTypeArray[j] == CharType.SPACE_LIKE) {
						j++;
					}

					if (j < length && charTypeArray[j] == CharType.HANZI) {
//						System.out.println(sentence.charAt(j) + "  ￥￥同" + wordBuf.toString() + "有可能构成词汇，加入到wordBuf中去");

						wordBuf.append(sentence.charAt(j));
						charArray = new char[wordBuf.length()];
						wordBuf.getChars(0, charArray.length, charArray, 0);
						// idArray作为前缀已经找到过(foundWordIndex!=-1),
						// 因此加长过后的idArray只可能出现在foundWordIndex以后,
						// 故从foundWordIndex之后开始查找
						foundIndex = wordDict.getPrefixMatch(charArray, foundIndex);
						j++;
					} else {
						break;
					}
				}
//				System.out.println("@@-----关键的小循环-----\r\n");
				// -------------------------关键的一个循环-------------------------

				i++;
				break;
			case FULLWIDTH_LETTER:
				hasFullWidth = true;
			case LETTER:
				j = i + 1;
				while (j < length && (charTypeArray[j] == CharType.LETTER || charTypeArray[j] == CharType.FULLWIDTH_LETTER)) {
					if (charTypeArray[j] == CharType.FULLWIDTH_LETTER)
						hasFullWidth = true;
					j++;
				}
				// 找到了从i到j的一个Token，类型为LETTER的字符串
				charArray = Utility.STRING_CHAR_ARRAY;
				frequency = wordDict.getFrequency(charArray);
				wordType = hasFullWidth ? WordType.FULLWIDTH_STRING : WordType.STRING;
				token = new SegToken(charArray, i, j, wordType, frequency);
				segGraph.addToken(token);
				i = j;
				break;
			case FULLWIDTH_DIGIT:
				hasFullWidth = true;
			case DIGIT:
				j = i + 1;
				while (j < length && (charTypeArray[j] == CharType.DIGIT || charTypeArray[j] == CharType.FULLWIDTH_DIGIT)) {
					if (charTypeArray[j] == CharType.FULLWIDTH_DIGIT)
						hasFullWidth = true;
					j++;
				}
				// 找到了从i到j的一个Token，类型为NUMBER的字符串
				charArray = Utility.NUMBER_CHAR_ARRAY;
				frequency = wordDict.getFrequency(charArray);
				wordType = hasFullWidth ? WordType.FULLWIDTH_NUMBER : WordType.NUMBER;
				token = new SegToken(charArray, i, j, wordType, frequency);
				segGraph.addToken(token);
				i = j;
				break;
			case DELIMITER:
				j = i + 1;
				// 标点符号的weight不用查了，选个最大的频率即可
				frequency = Utility.MAX_FREQUENCE;
				charArray = new char[] { sentence.charAt(i) };
				token = new SegToken(charArray, i, j, WordType.DELIMITER, frequency);
				segGraph.addToken(token);
				i = j;
				break;
			default:
				j = i + 1;
				// 把不认识的字符当作未知串看待，例如GB2312编码之外的字符，每个字符当作一个
				charArray = Utility.STRING_CHAR_ARRAY;
				frequency = wordDict.getFrequency(charArray);
				token = new SegToken(charArray, i, j, WordType.STRING, frequency);
				segGraph.addToken(token);
				i = j;
				break;
			}
		}
//		System.out.println("----第二步，完成\r\n");

		// 为segGraph增加两个新Token： "始##始","末##末"
		charArray = Utility.START_CHAR_ARRAY;
		frequency = wordDict.getFrequency(charArray);
		token = new SegToken(charArray, -1, 0, WordType.SENTENCE_BEGIN, frequency);
		segGraph.addToken(token);

		// "末##末"
		charArray = Utility.END_CHAR_ARRAY;
		frequency = wordDict.getFrequency(charArray);
		token = new SegToken(charArray, length, length + 1, WordType.SENTENCE_END, frequency);
		segGraph.addToken(token);

		return segGraph;
	}

	/**
	 * 为sentence中的每个字符确定唯一的字符类型
	 * 
	 * @see Utility.charType(char)
	 * @param sentence
	 *            输入的完成句子
	 * @return 返回的字符类型数组，如果输入为null，返回也是null
	 */
	private static CharType[] getCharTypes(String sentence) {
		int length = sentence.length();
		CharType[] charTypeArray = new CharType[length];
		// 生成对应单个汉字的字符类型数组
		for (int i = 0; i < length; i++) {
			charTypeArray[i] = Utility.getCharType(sentence.charAt(i));
//			System.out.println("--------当前字符’" + sentence.charAt(i) + "‘, 类型为" + charTypeArray[i]);
		}

		return charTypeArray;
	}

	public List<SegToken> process(String sentence) {
//		System.out.println("\r\n----------------------分词三大步骤的第一步，寻找可能的词元---------------------------------\r\n");
//		System.out.println("\r\n---------\r\n调用createSegGraph方法。\r\n********");
		SegGraph segGraph = createSegGraph(sentence);
//		System.out.println("********\r\ncreateSegGraph方法调用完毕。\r\n---------\r\n");
//		System.out.println("\r\n----------------------词元已经全找出来了，接下来就要算最好的那个组合了。接下来的步骤是分词的关键。-----------\r\n");
//		System.out.println("\r\n----------------------分词三大步骤的第二步，计算组合概率，寻找最优组合---------------------------------\r\n");

		BiSegGraph biSegGraph = new BiSegGraph(segGraph);
		List<SegToken> shortPath = biSegGraph.getShortPath();
		return shortPath;
	}
}
