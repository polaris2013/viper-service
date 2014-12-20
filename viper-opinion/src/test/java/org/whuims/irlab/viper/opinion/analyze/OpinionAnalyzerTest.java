/**
 * 
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package org.whuims.irlab.viper.opinion.analyze;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import org.whuims.irlab.viper.common.entity.Sentence;
import org.whuims.irlab.viper.common.entity.Word;
import org.whuims.irlab.viper.common.exception.AnalyzerException;
import org.whuims.irlab.viper.opinion.BaseAnalyzerTest;
import org.whuims.irlab.viper.opinion.analyzer.OpinionAnalyzer;
import org.whuims.irlab.viper.opinion.utils.FileUtils;

/**
 * @title AnalyzerHandlerTest
 * @description TODO 
 * @author juyuan
 * @date 2014年8月28日
 * @version 1.0
 */
public class OpinionAnalyzerTest extends BaseAnalyzerTest {
    @Test
    public void testAnalyzeContent() throws AnalyzerException {
        String rawContent = FileUtils.readFile(testFile, encoding);
        List<Sentence<Word>> context = OpinionAnalyzer.analyzeContent(rawContent, analyzer);
        Iterator<Sentence<Word>> sentIter = context.iterator();
        while (sentIter.hasNext()) {
            Sentence<Word> sent = sentIter.next();
            System.out.println("分词结果：" + sent.getPosSentence());
            for (int i = 0; i < sent.size(); i++) {
                Word wordtoken = sent.get(i);
                System.out.println(wordtoken.getWordStr() + "\t" + wordtoken.getPos());
            }
        }
    }
}
