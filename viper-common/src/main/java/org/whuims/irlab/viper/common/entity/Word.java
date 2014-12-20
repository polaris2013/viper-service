/**
 * 
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package org.whuims.irlab.viper.common.entity;

/**
 * @title Word
 * @description TODO 
 * @author juyuan
 * @date 2014年8月31日
 * @version 1.0
 */
public class Word {

    private String wordStr = ""; // 词语
    private String pos = ""; // 词性标记
    private int wordIndex = 0; // 在句子中的起始索引
    private int sentID = 0; // 所在句子在文章中的编号
    private int wordID = 0; // 在句子中的ID

    private int termFreq = 1; // 句中的词频
    /**  
     * 在观点识别中，该标志位用以区分：
     *     人名标记: (myName)，  人称代词标记: (myRR)，     观点指示动词标记 : (vwordIndicator)等
     *  在实体识别中，该标志位用以区分不同实体类型：
     *  
     */
    int indicator = 0; // 标记，

    public void addTF() {
        this.termFreq++;
    }

    public int getWordID() {
        return wordID;
    }

    public void setWordID(int wordID) {
        this.wordID = wordID;
    }

    public String getWordStr() {
        return wordStr;
    }

    public void setWordStr(String wordStr) {
        this.wordStr = wordStr;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public int getWordIndex() {
        return wordIndex;
    }

    public void setWordIndex(int wordIndex) {
        this.wordIndex = wordIndex;
    }

    public int getSentID() {
        return sentID;
    }

    public void setSentID(int sentID) {
        this.sentID = sentID;
    }

    public int getTermFreq() {
        return termFreq;
    }

    public void setTermFreq(int termFreq) {
        this.termFreq = termFreq;
    }

    public int getIndicator() {
        return indicator;
    }

    public void setIndicator(int indicator) {
        this.indicator = indicator;
    }

    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return wordStr.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        Word wo = (Word) obj;
        if (wo.getWordStr().equals(wordStr))
            return true;
        return false;
    }

}
