/**
 * 
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package org.whuims.irlab.viper.rrs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;



import java.util.Set;

import org.whuims.irlab.viper.common.entity.Sentence;
import org.whuims.irlab.viper.common.entity.Word;
import org.whuims.irlab.viper.rrs.utils.Utility;

/**
 * @title GeneralRrsHandler
 * @description TODO 
 * @author juyuan
 * @date 2014年10月24日
 * @version 1.0
 */
public class GeneralRrsHandler {

    private static final int MAX_WORD_CNT=8;
    private static final int DEFAULT_WINDOW= 4; // 向前查找的句子的阀值
    

    /**
     * 简单指代消解，找到代词在上下文中所指代的
     * <pre>具体指代消解方法：向前找出一定阀值内的不超过wordCount个的人名词 
     * 根据一定的排除法，去除一部分, 根据一定点优先法排序 选出优先值最高的词汇
     * @param context 抽取观点的文本
     * @param sent  包含代词的句子
     * @param rId  （人称代词）在句子中的ID
     * @return
     */
    public static List<Word> getRR(List<Sentence<Word>> context, Sentence<Word> sent, int rId,int window) {
        if(window<-1){
            window=DEFAULT_WINDOW;
        }
        Queue<Word> queue = new ArrayDeque<Word>(); // 保存人名的堆栈
        Word term=sent.get(rId);
        String word = term.getWordStr();
        String pos=term.getPos();
        
        if (Utility.isPart(pos, Tag.RP_POS_SET)) {
            if(Utility.isPart(word,Tag.PLURAL_PERSON_RR_WORDS)){
               queue = addStack(context, sent, rId, Tag.PERSONNE_POS_SET,-1,window);
            }else if(Utility.isPart(word, Tag.PLURAL_THING_RR_WORDS)){
                queue = addStack(context, sent, rId, Tag.OTHERNE_POS_SET,-1,window);
            }else if(Utility.isPart(word, Tag.SINGLE_THING_RR_WORDS)){
                queue = addStack(context, sent, rId, Tag.OTHERNE_POS_SET, 1,window);
            }else{
                queue = addStack(context, sent, rId, Tag.PERSONNE_POS_SET, 1,window);
            }
        }else if(Utility.isPart(pos, Tag.RS_POS_SET)){
            if(Utility.isPart(word,Tag.PLURAL_THING_RR_WORDS)){
                queue = addStack(context, sent, rId, Tag.PLACENE_POS_SET,-1,window);
             }else{
                 queue = addStack(context, sent, rId, Tag.PLACENE_POS_SET, 1,window);
             }
        }else if(Utility.isPart(pos, Tag.RT_POS_SET)){
            queue = addStack(context, sent, rId, Tag.TIMENE_POS_SET, 1,window);
        }else if(Utility.isPart(pos, Tag.RO_POS_SET)) {           
            if(Utility.isPart(word,Tag.PLURAL_THING_RR_WORDS)){
                queue = addStack(context, sent, rId, Tag.OTHERNE_POS_SET,-1,window);
            }else{
                queue = addStack(context, sent, rId, Tag.OTHERNE_POS_SET, 1,window);
            }
        }
        List<Word> rrWords=new ArrayList<Word>();
        Set<String>  existSet=new HashSet<String>();
        while (queue.size() > 0) {
            Word can=queue.poll();
            if(!existSet.contains(can.getWordStr().trim())){
                rrWords.add(can);  
                existSet.add(can.getWordStr().trim());
            }
          
        }
        
        return rrWords;
    }
    /**
     * 返回找到的词汇
     * @param context  抽取观点的文本
     * @param sent  包含观点的句子
     * @param nId    观点持有者（人称代词）在句子中的ID
     * @param posAr  人名词汇集合
     * @return  消解候选栈
     */
    private static Queue<Word> addStack(List<Sentence<Word>> context, Sentence<Word> sent, 
            int nId, String[] posAr,int wordCount,int window) {
       
        if(wordCount==-1)  wordCount=MAX_WORD_CNT;
        Queue<Word> stack = new ArrayDeque<Word>();
        if (nId > sent.size() - 1 || nId < 0) { // check Out of Index Error
            return stack;
        }
        int sentId = sent.getSentId(); // 获取句子的编号
        for (int i = nId; i >= 0; i--) {
         
            if (Utility.isPosPart(sent.get(i).getPos(), posAr)) {
                if (stack.size() < wordCount) {                 
                        stack.add(sent.get(i));
                } else {
                    break;
                }
            }
        }
        int firId = 0; // 起始句子编号
        if (sentId - window> 0) {
            firId = sentId - window;
        }
        // 在前四句内查找人物代词
        for (int i = sentId - 1; i >= firId; i--) {
            boolean label = false; // 跳出最外层句子循环的标志
            for (int j = context.get(i).getWordCount() - 1; j >= 0; j--) {
              
                if (Utility.isPosPart(context.get(i).get(j).getPos(), posAr)) {
                  
                    if (stack.size() < wordCount) {
                        stack.add(context.get(i).get(j));
                    } else {
                        label = true;
                        break;
                    }
                }
            }
            if (label) {
                break;
            }
        }
        return stack;
    }
}
