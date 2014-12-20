/**
 * 
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package org.whuims.irlab.viper.entitygraph.analyzer;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import org.apache.lucene.analysis.Token;
import org.whuims.irlab.viper.common.CFGKEYS;
import org.whuims.irlab.viper.common.IAnalyzer;
import org.whuims.irlab.viper.common.ViperConfig;
import org.whuims.irlab.viper.common.analyzer.AnalyzerHelper;
import org.whuims.irlab.viper.common.entity.Sentence;
import org.whuims.irlab.viper.common.entity.Word;
import org.whuims.irlab.viper.common.exception.AnalyzerException;
import org.whuims.irlab.viper.entitygraph.common.EntityTypes;
import org.whuims.irlab.viper.entitygraph.common.LanguageOpt;
import org.whuims.irlab.viper.entitygraph.utils.SentenceTokenizer;
import org.whuims.irlab.viper.entitygraph.utils.StopWordFilter;
import org.whuims.irlab.viper.ictclas.IctclasAnalyzer;
import org.whuims.irlab.viper.stanford.StanfordEnglishAnalyzer;

/**
 * @title AnalyzerHelper
 * @description TODO 
 * @author juyuan
 * @date 2014年8月31日
 * @version 1.0
 */
public class EGAnalyzer {
     private static ViperConfig config=ViperConfig.getInstance();
   
    /**
     * 对文本内容解析，包括  
     * 【1】分句  【2】分词，同时做词性标注  【3】识别3种观点指示词，对词语加上id和位置索引，并加入句子的词汇列表。
     * @param content
     * @param lang  语种
     * @return 句子结构的列表
     * @throws IOException
     */
    public static List<Sentence<Word>> analyzeContent(String content, int lang) throws AnalyzerException {
        
        List<Sentence<Word>> list = new ArrayList<Sentence<Word>>();
        List<Sentence<Word>> sentences = new ArrayList<Sentence<Word>>();
        List<String> entityTags = new ArrayList<String>();
        IAnalyzer analyzer = null;
        if (lang == LanguageOpt.CHINESE) {
            analyzer = IctclasAnalyzer.getInstance();
            analyzer.importUserDict(config.getPropValue(CFGKEYS.ICTCLAS_USER_DICT, "nvrs/userdict.txt"));  //导入用户词典
            String[] cn_ent_tags = { EntityTypes.ORG_ENTITY_CN, EntityTypes.PERSON_ENTITY_CN,
                    EntityTypes.PERSON_ENTITY_CN_EXTEND1,EntityTypes.PERSON_ENTITY_CN_EXTEND2, EntityTypes.PLACE_ENTITY_CN,EntityTypes.PLACE_ENTITY_CN_EXTEND1 };
            entityTags.addAll(Arrays.asList(cn_ent_tags));
        } else if (lang == LanguageOpt.ENGLISH) {
            analyzer = StanfordEnglishAnalyzer.getInstance();
            String[] en_ent_tags = { EntityTypes.ORG_ENTITY_EN, EntityTypes.PERSON_ENTITY_EN,
                    EntityTypes.PLACE_ENTITY_EN };
            entityTags.addAll(Arrays.asList(en_ent_tags));
        }
        //1)分句
        SentenceTokenizer tokenizer = new SentenceTokenizer(new StringReader(content));
        int i = 0;
     
        Token token;
        try {
            token = tokenizer.next();
     
        while (token != null) {
            Sentence<Word> sent = new Sentence<Word>();
            sent.setSentId(i);
            sent.setOffset_begin(token.startOffset());
            sent.setOffset_end(token.endOffset());       
            sent.setTermSentence(token.term());   
            sentences.add(sent);
            token = tokenizer.next();
            i++;
        }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new AnalyzerException("entitiy graph analyzer fail! ",e);
        }
        int sentNum = sentences.size();
        Hashtable<String, Word> wordSet = new Hashtable<String, Word>();
        //2 对每个句子进行分词
        for (int j = 0; j < sentNum; j++) {
            Sentence<Word> sentence=sentences.get(j);
            String sentContent = sentence.getTermSentence();
            
            String tagSent = analyzer.analyzeText(sentContent, 1);
            String[] strAr = tagSent.split(" ");
            int n = strAr.length;
            Sentence<Word> sent = new Sentence<Word>(j, tagSent, n);
            List<Word> entityList = new ArrayList<Word>();

            int index = 0; // 词语位置索引
            int wordId=0;
            for (int k = 0; k < n; k++) {
                if (k > 0) {
                    index += strAr[k - 1].length() + k;
                }
                String tokenstr = strAr[k];
                String pos = AnalyzerHelper.getWordPos(tokenstr); // 获取词性
                String wordStr = AnalyzerHelper.getWord(tokenstr); // 获取词语

                if (StopWordFilter.isStopword(wordStr)){
                    continue;
                }
   
                if (wordSet.containsKey(wordStr)) {
                    wordSet.get(wordStr).addTF();
                } else {
                    
                    Word word = new Word(); // 生成Word对象
                    word.setWordStr(wordStr); //词汇的字符串表示
                    
                    word.setPos(pos); //词汇的词性标注
                    word.setWordID(wordId); //词汇在句子中的ID
                    wordId++;
                    word.setWordIndex(index); //词汇在句子中的起始位置索引
                    word.setSentID(i); //词汇所在句子的ID
                    if (entityTags.contains(pos)) {
                        if (pos.equals(EntityTypes.PERSON_ENTITY_CN)||pos.equals(EntityTypes.PERSON_ENTITY_CN_EXTEND1)||pos.equals(EntityTypes.PERSON_ENTITY_CN_EXTEND2) || pos.equals(EntityTypes.PERSON_ENTITY_EN)) {
                            word.setIndicator(EntityTypes.PERSON_ENTITY);
                        } else if (pos.equals(EntityTypes.PLACE_ENTITY_CN)||pos.equals(EntityTypes.PLACE_ENTITY_CN_EXTEND1) || pos.equals(EntityTypes.PLACE_ENTITY_EN)) {
                            word.setIndicator(EntityTypes.PLACE_ENTITY);
                        } else if (pos.equals(EntityTypes.ORG_ENTITY_CN) || pos.equals(EntityTypes.ORG_ENTITY_EN)) {
                            word.setIndicator(EntityTypes.ORG_ENTITY);
                        }
                        entityList.add(word);
                    }
                    sent.add(word); // 将词汇实体加入句子实体
                    wordSet.put(wordStr, word);
                }

            }
           
            sent.setEntitylist(entityList);
            sent.setTermSentence(sentContent);
            sent.setOffset_begin(sentence.getOffset_begin());
            sent.setOffset_end(sentence.getOffset_end());
            list.add(sent);
        }
        return list;
    }

   

}
