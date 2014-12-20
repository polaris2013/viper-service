/**
 * 
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package org.whuims.irlab.viper.stanford;

import org.junit.Test;

import org.whuims.irlab.viper.common.exception.AnalyzerException;

/**
 * @title StanfordChineseAnalyzerTest
 * @description TODO 
 * @author juyuan
 * @date 2014年8月26日
 * @version 1.0
 */
public class StanfordChineseAnalyzerTest {
    @Test
    public void testStanfordChineseAnalyzer() throws AnalyzerException{
         String testContent="武汉大学在中国, 胡锦涛是中国主席.";
         StanfordChineseAnalyzer sca=StanfordChineseAnalyzer.getInstance();
         String result=sca.analyzeText(testContent, 1);
         System.out.println(result);
    }
}
