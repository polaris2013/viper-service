/**
 * 
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package org.whuims.irlab.viper.stanford;

import java.io.File;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.SmartChineseAnalyzer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.whuims.irlab.viper.common.CFGKEYS;
import org.whuims.irlab.viper.common.CorpusLocator;
import org.whuims.irlab.viper.common.IAnalyzer;
import org.whuims.irlab.viper.common.ViperConfig;
import org.whuims.irlab.viper.common.ViperConstants;
import org.whuims.irlab.viper.common.exception.AnalyzerException;
import org.whuims.irlab.viper.stanford.StanfordNERLabel;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.AnswerAnnotation;

/**
 * @title StanfordChineseAnalyzer
 * @description TODO 
 * @author juyuan
 * @date 2014年8月26日
 * @version 1.0
 */
public class StanfordChineseAnalyzer implements IAnalyzer {
    private static StanfordChineseAnalyzer instance = null;
    private AbstractSequenceClassifier<CoreLabel> classifier;
    private Logger logger=LoggerFactory.getLogger(StanfordChineseAnalyzer.class);
    private ViperConfig config=ViperConfig.getInstance();
    private StanfordChineseAnalyzer() {
        init();
    }
    

    // 获取当前类加载的根目录，如：/C:/Program Files/Apache/Tomcat 6.0/webapps/fee/WEB-INF/classes/
  
    private void init() {
        //加载文件流的属性   
       String corpusLoc = config.getPropValue(CFGKEYS.STANFORD_NER_CN_CORPUS, CorpusLocator.stanford_cn_corpus);
       logger.info("loading from  location :"+ corpusLoc);
        
        if (!new File(corpusLoc).exists()) {
                corpusLoc = "src/test/resources/" + CorpusLocator.stanford_cn_corpus;
            if (!new File(corpusLoc).exists()) {
                corpusLoc = "src/main/resources/" + CorpusLocator.stanford_cn_corpus;
            }
        }
        classifier = CRFClassifier.getClassifierNoExceptions(corpusLoc);
        logger.info("loading resources successfully");
    }

    /**
     * 这种处理方式虽然引入了同步代码，但是因为这段同步代码只会在最开始的时候执行一次或多次，
     * 所以对整个系统的性能不会有影响。
     */
    private static synchronized void syncInit() {
        if (instance == null) {
            instance = new StanfordChineseAnalyzer();
        }
    }

    public static StanfordChineseAnalyzer getInstance() {
        if (instance == null) {
            syncInit();
        }
        return instance;
    }

    public String analyzeText(String srcContent, int tag) throws AnalyzerException {
        Analyzer ca = new SmartChineseAnalyzer(true);
        Reader sentence = new StringReader(srcContent);
        TokenStream ts = ca.tokenStream("sentence", sentence);
        Token nt = new Token();
        StringBuilder sb = new StringBuilder();
        try {
            nt = ts.next(nt);
            while (nt != null) {
                sb.append(nt.term() + ViperConstants.ANALYZER_WORD_SEP);
                nt = ts.next(nt);
            }
            ts.close();
        } catch (Exception e) {
            throw new AnalyzerException("stanford chinese ner  fail",e);
        }
        String text = sb.toString().trim();

        if (tag == 0) {
            return text;
        }

        List<List<CoreLabel>> lcls = classifier.classify(text);
        Iterator<List<CoreLabel>> iter = lcls.iterator();
        List<String> mergeList = new ArrayList<String>();
        while (iter.hasNext()) {
            List<CoreLabel> sentElement = iter.next(); //文本的一个句子
            int i = 0;
            int sentLen = sentElement.size();
            while (i < sentLen - 1) {
                CoreLabel token = sentElement.get(i);
                String word = token.word();
                String label = token.get(AnswerAnnotation.class);
                if (!label.equals(StanfordNERLabel.CN_PERSON_ENTITY_TAG)
                        && !label.equals(StanfordNERLabel.CN_PLACE_ENTITY_TAG)
                        && !label.equals(StanfordNERLabel.CN_ORG_ENTITY_TAG)) {
                    mergeList.add(word + ViperConstants.ANALYZER_POSTAG_SEP + label);
                    i++;
                    continue;
                }
                //                 对于命名实体，相同label的命名实体进行合并
                int j = i + 1;
                while (j < sentLen) {
                    CoreLabel nextToken = sentElement.get(j);
                    String nextLabel = nextToken.get(AnswerAnnotation.class);
                    String nextWord = nextToken.word();
                    if (!label.equals(nextLabel)) {
                        mergeList.add(word + ViperConstants.ANALYZER_POSTAG_SEP + label);
                        if (j == sentLen - 1) {
                            mergeList.add(nextWord + ViperConstants.ANALYZER_POSTAG_SEP + nextLabel);
                        }
                        i = j;
                        break;
                    }

                    word = word + nextWord;
                    label = nextLabel;
                    if (j == sentLen - 1) {
                        mergeList.add(word + ViperConstants.ANALYZER_POSTAG_SEP + label);
                        i = j;
                    }
                    j++;

                }
            }
        }

        StringBuilder sbt = new StringBuilder();
        Iterator<String> siter = mergeList.iterator();
        while (siter.hasNext()) {
            sbt.append(siter.next() + ViperConstants.ANALYZER_WORD_SEP);
        }
        text = sbt.toString().trim();
        return text;
    }


    /* (non-Javadoc)
     * @see org.whuims.irlab.viper.common.IAnalyzer#importUserDict(java.lang.String)
     */
    @Override
    public int importUserDict(String userDict) throws AnalyzerException {
        // TODO Auto-generated method stub
        return 0;
    }

    
}
