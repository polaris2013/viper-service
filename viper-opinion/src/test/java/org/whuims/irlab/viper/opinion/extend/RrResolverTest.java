/**
 * 
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package org.whuims.irlab.viper.opinion.extend;

import java.util.List;

import org.junit.Test;

import org.whuims.irlab.viper.common.entity.Sentence;
import org.whuims.irlab.viper.common.entity.Word;
import org.whuims.irlab.viper.common.exception.AnalyzerException;
import org.whuims.irlab.viper.opinion.BaseAnalyzerTest;
import org.whuims.irlab.viper.opinion.analyzer.OpinionAnalyzer;
import org.whuims.irlab.viper.opinion.extend.RrResolver;

/**
 *  指代消解的测试类
 * @title RrResolverTest
 * @description TODO 
 * @author juyuan
 * @date 2014年8月28日
 * @version 1.0
 */
public class RrResolverTest extends BaseAnalyzerTest {
    @Test
    public void testRrResolver() throws AnalyzerException {
        String testText = " 小明是参与这次事故的目击者，也是受害者。他称”这是一场恶意事件“。";
        List<Sentence<Word>> context = OpinionAnalyzer.analyzeContent(testText, analyzer);
        System.out.println("共有" + context.size() + "句话");
        Sentence<Word> sent = context.get(1);
        System.out.println("获取后一句话的分词结果： " + sent.getPosSentence());

        int nId = 0; //“他”/rr  人称指示代词 的ID 
        Word word = RrResolver.getRR(context, sent, nId);
        String wordstr = word.getWordStr();
        String pos = word.getPos();
        System.out.println("指代消解后 - 观点持有者：" + wordstr + " 词性： " + pos);

    }
}
