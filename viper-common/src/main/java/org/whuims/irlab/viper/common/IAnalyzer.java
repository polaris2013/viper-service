/**
 * 
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package org.whuims.irlab.viper.common;

import org.whuims.irlab.viper.common.exception.AnalyzerException;


/**
 *  分词器相关接口
 * @title IAnalyzer
 * @description TODO 
 * @author juyuan
 * @date 2014年8月26日
 * @version 1.0
 */
public interface IAnalyzer {
     
    /**
     * @param strContent
     *            待分词的字符串
     * @param tag
     *            是否标记词性，0：不标记;1：标记
     * @return  分词后的字符串列表
     */
    public String analyzeText(String strContent, int tag) throws AnalyzerException;
    
    public int  importUserDict(String userDict) throws AnalyzerException;
}
