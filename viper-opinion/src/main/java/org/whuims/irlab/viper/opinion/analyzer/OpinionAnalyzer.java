/**
 * 
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package org.whuims.irlab.viper.opinion.analyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.whuims.irlab.viper.common.CFGKEYS;
import org.whuims.irlab.viper.common.IAnalyzer;
import org.whuims.irlab.viper.common.ViperConfig;
import org.whuims.irlab.viper.common.analyzer.AnalyzerHelper;
import org.whuims.irlab.viper.common.entity.Sentence;
import org.whuims.irlab.viper.common.entity.Word;
import org.whuims.irlab.viper.common.exception.AnalyzerException;
import org.whuims.irlab.viper.opinion.common.Tag;
import org.whuims.irlab.viper.opinion.common.ViewIndicator;
import org.whuims.irlab.viper.opinion.utils.ParaSegment;

/**
 * 对文本内容解析类，包括分词，词性标注，取词
 * @title AnalyzerHandler
 * @description TODO 
 * @author juyuan
 * @date 2014年8月28日
 * @version 1.0
 */
public class OpinionAnalyzer {
    private static ViperConfig config=ViperConfig.getInstance();
    /**
     * 对文本内容解析，包括  
     * 【1】分句  【2】分词，同时做词性标注  【3】识别3种观点指示词，对词语加上id和位置索引，并加入句子的词汇列表。
     * @param content
     * @return 句子结构的列表
     * @throws IOException
     */
    public   static List<Sentence<Word>> analyzeContent(String content, IAnalyzer analyzer) throws AnalyzerException {
        List<String> nameTagList = Tag.NR_TAG_LIST;
        List<String> vTagList = Tag.V_TAG_LIST;
        List<String> rrTagList = Tag.RR_TAG_LIST;   
        List<Sentence<Word>> list = new ArrayList<Sentence<Word>>();
        /** 分句，每一句话占一行 */
        ArrayList<String> sentList = ParaSegment.paraSegmentToList(content);
        
        /** 2) 分词 */
        analyzer.importUserDict(config.getPropValue(CFGKEYS.ICTCLAS_USER_DICT, "nvrs/userdict.txt"));  //导入用户词典
        int sentNum = sentList.size();
        for (int i = 0; i < sentNum; i++) {
            String tagSent = analyzer.analyzeText(sentList.get(i), 1); // 利用中科院分词器对句子进行分词，tag=1 表示同时返回词性标注结果 tag=0表示仅分词
            String[] strAr = tagSent.split(" ");
            List<String> wordList = Arrays.asList(strAr);
            Sentence<Word> sent = new Sentence<Word>(i, tagSent, wordList.size());  //利用分词结果初始化句子实体
            int index = 0; // 词语位置索引
            for (int j = 0; j < wordList.size(); j++) {
                if (j > 0) {
                    index += wordList.get(j - 1).length() + 1;
                }
                String wor = wordList.get(j);
                String pos = AnalyzerHelper.getWordPos(wor); // 获取词性
                String wordStr = AnalyzerHelper.getWord(wor); // 获取词语

                Word word = new Word(); // 生成Word对象
                //根据词性初步打上观点指示特征标记, 如人名（观点持有者标记，观点指示动词标记和人称代词标记）
                if (nameTagList.contains(pos)) {
                    word.setIndicator(ViewIndicator.nameIndicator);
                } else if (vTagList.contains(wor)) {
                    word.setIndicator(ViewIndicator.vwordIndicator);
                } else if (rrTagList.contains(wor)) {  
                    word.setIndicator(ViewIndicator.rrIndicator);
                }
                word.setWordStr(wordStr); //词汇的字符串表示 
                word.setPos(pos);  //词汇的词性标注
                word.setWordID(j);  //词汇在句子中的ID
                word.setWordIndex(index); //词汇在句子中的起始位置索引
                word.setSentID(i); //词汇所在句子的ID
                sent.add(word);   // 将词汇实体加入句子实体
            }
            list.add(sent);
        }
        return list;
    }
}
