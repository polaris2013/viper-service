/**
 * 
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package org.whuims.irlab.viper.opinion.analyze;

import org.junit.Test;

import org.whuims.irlab.viper.common.analyzer.AnalyzerHelper;
import org.whuims.irlab.viper.ictclas.*;
import org.whuims.irlab.viper.opinion.BaseAnalyzerTest;


/**
 * @title AnalyzerHelperTest
 * @description TODO 
 * @author juyuan
 * @date 2014年8月31日
 * @version 1.0
 */
public class AnalyzerHelperTest extends BaseAnalyzerTest {
    @Test
    public void testAnalyzeHelper() {
        String wordtoken = "这/rzv";
        wordtoken = "/nr";
        String pos = AnalyzerHelper.getWordPos(wordtoken);
        String word = AnalyzerHelper.getWord(wordtoken);
        System.out.println("get pos: " + pos + "\tget word:  " + word);

    }

}
