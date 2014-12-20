/**
 * 
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package org.whuims.irlab.viper.stanford;

import org.junit.Test;

import org.whuims.irlab.viper.common.exception.AnalyzerException;

/**
 * @title StanfordEnglishAnalyzerTest
 * @description TODO 
 * @author juyuan
 * @date 2014年8月26日
 * @version 1.0
 */
public class StanfordEnglishAnalyzerTest {
    @Test 
    public void testStanfordEnglishAnalyzer() throws AnalyzerException{
         String srcContent="President Barack Obama and Israeli Prime Minister Benjamin Netanyahu offered a \"good cop-bad cop\" approach to Iran's nuclear ambitions on Wednesday, with Obama calling for more diplomacy while endorsing Israel's right to defend itself as it sees fit.";
         StanfordEnglishAnalyzer st=StanfordEnglishAnalyzer.getInstance();
         String result=st.analyzeText(srcContent, 0);
         System.out.println(result);
    }
}
