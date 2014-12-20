/**
 * 
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package org.whuims.irlab.viper.common.analyzer;

/**
 * @title IctclasAnalyzerHelper
 * @description TODO 
 * @author juyuan
 * @date 2014年10月24日
 * @version 1.0
 */
public class AnalyzerHelper{
    /**
     * 获取一个单词的后缀标记词性
     * 
     * @param word
     * @return 如果没有则返回""
     */
    public static String getWordPos(String word) {
        String pos = "";
        if (word.matches(".*?/\\p{Alnum}+?")) {
            pos = word.substring(word.lastIndexOf("/"), word.length());
        }

        return pos;
    }

    /**
     * 获取一个标记词性的单词的原词，即去掉词性标记 如果只有词性标记，则返回词性标记
     * 
     * @param word
     * @return 没有则返回""
     */
    public static String getWord(String word) {
        String wor = "";
//        .+?/\\p{Alnum}+?
        if (word.matches(".+?/\\p{Alnum}+?")) {
            wor = word.substring(0, word.lastIndexOf("/"));
        }
        
        return wor;
    }
}
