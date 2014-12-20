/**
 * 
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package org.whuims.irlab.viper.opinion.locator;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;

import org.whuims.irlab.viper.common.entity.Sentence;
import org.whuims.irlab.viper.common.entity.Word;
import org.whuims.irlab.viper.common.exception.AnalyzerException;
import org.whuims.irlab.viper.opinion.BaseAnalyzerTest;
import org.whuims.irlab.viper.opinion.analyzer.OpinionAnalyzer;
import org.whuims.irlab.viper.opinion.common.ViewIndicator;

/**
 * @title BaseLocatorTest
 * @description TODO 
 * @author juyuan
 * @date 2014年8月31日
 * @version 1.0
 */
public class BaseLocatorTest extends BaseAnalyzerTest {
    protected Sentence<Word> exampleSent;
    protected List<Word> viewerCandidates = new ArrayList<Word>(); //观点持有者候选列表
    protected List<Word> viewVerbCandidates = new ArrayList<Word>(); // 观点指示动词候选列表

    @Before
    public void prepareLocator() throws AnalyzerException {
        String testContext = "长江论坛秘书长、长江水资源保护局前局长翁立达接受记者李华采访时也 认为排污严重，他曾说：“全国一半以上的化工企业集中在长江流域。每天进入长江的生活和工业污水相当于一条黄河。”";
        List<Sentence<Word>> contextList = OpinionAnalyzer.analyzeContent(testContext, analyzer);
        exampleSent = contextList.get(0);
        System.out.println("分词结果: " + exampleSent.getPosSentence());
        for (int j = 0; j < exampleSent.size(); j++) {
            /* 循环句中每个词语 */
            Word word = exampleSent.get(j);
            if (word.getIndicator() == ViewIndicator.nameIndicator || word.getIndicator() == ViewIndicator.rrIndicator) {
                viewerCandidates.add(word); // 添加人名词汇
            } else if (word.getIndicator() == ViewIndicator.vwordIndicator) {
                viewVerbCandidates.add(word); // 添加观点指示动词
            }
        }
        System.out.println("观点持有者个数个数: " + viewerCandidates.size() + "\t 观点指示动词候选 个数 :" + viewVerbCandidates.size()
                + "\n ===================");
    }

    protected void listCandiates(List<Word> candidatesList) {
        for (int i = 0; i < candidatesList.size(); i++) {
            if (i > 0) {
                System.out.print(",");
            }
            System.out.print(candidatesList.get(i).getWordStr());
            if (i == candidatesList.size() - 1)
                System.out.print("\n");
        }
    }
}
