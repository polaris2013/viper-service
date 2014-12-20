/**
 * 
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package org.whuims.irlab.viper.common.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @title Sentence
 * @description TODO 
 * @author juyuan
 * @date 2014年8月31日
 * @version 1.0
 */
@SuppressWarnings("hiding")
public class Sentence<Word> extends ArrayList<Word> {

    /**
     * for serialization
     */
    private static final long serialVersionUID = -7658725638863718170L;

    int sentId = 0; // 句子的编号
    int wordCount = 0; // 句子中词语的数量
    String posSentence = ""; // 包括词语和词性标注的句子
    String termSentence = ""; //只包含词语的句子
    
 
    private List<Word> entityList;

    public Sentence() {

    }

    /**
     * 初始化句子实体
     * @param sentID  句子在文本中的ID
     * @param posSentence  带词性标注的分词结果（字符串）
     * @param size  组成句子的词汇数量
     */
    public Sentence(int sentID, String posSentence, int size) {
        this.sentId = sentID;
        this.wordCount = size;
        this.posSentence = posSentence;
    }

    /** 句子在文档中的起始位置 */
    private int offset_begin = 0;

    /** 句子在文档中的结束位置 */
    private int offset_end = 0;

    public int getSentId() {
        return sentId;
    }

    public void setSentId(int sentId) {
        this.sentId = sentId;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    public String getPosSentence() {
        return posSentence;
    }

    public void setPosSentence(String posSentence) {
        this.posSentence = posSentence;
    }

    public String getTermSentence() {
        return this.termSentence;
    }

    public void setTermSentence(String termSentence) {
        this.termSentence = termSentence;
    }
    public List<Word> getEntityList() {
        return entityList;
    }

    public void setEntitylist(List<Word> entityList) {
        this.entityList = entityList;
    }

    public int getOffset_begin() {
        return offset_begin;
    }

    public void setOffset_begin(int offset_begin) {
        this.offset_begin = offset_begin;
    }

    public int getOffset_end() {
        return offset_end;
    }

    public void setOffset_end(int offset_end) {
        this.offset_end = offset_end;
    }

}
