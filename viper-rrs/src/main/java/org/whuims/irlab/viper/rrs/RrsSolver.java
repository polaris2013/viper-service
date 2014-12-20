/**
 * 
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package org.whuims.irlab.viper.rrs;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;






import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.whuims.irlab.viper.common.entity.Sentence;
import org.whuims.irlab.viper.common.entity.Word;
import org.whuims.irlab.viper.common.exception.AnalyzerException;
import org.whuims.irlab.viper.ictclas.IctclasAnalyzer;
import org.whuims.irlab.viper.rrs.bean.RrRef;


/**
 * @title RrsFinder
 * @description TODO 
 * @author juyuan
 * @date 2014年10月24日
 * @version 1.0
 */
public class RrsSolver {

    private  Logger logger=LoggerFactory.getLogger(RrsSolver.class);
    private int window = 4; // 向前查找的句子的阀值
   
    /**
     * 初始化中科院分词器，中科院分词器在分词的同时，做词性标注。中文分词准确率达98%以上，效果较好
     */
    private IctclasAnalyzer analyzer = IctclasAnalyzer.getInstance();
    
    public RrsSolver() {
        
    }

    public void setWindow(int window) {
        this.window = window;
    }

    public List<RrRef> solveReference(String textContent) throws AnalyzerException {
        logger.info("solve reference start ...");
        List<RrRef> refList=new ArrayList<RrRef>();
        List<Sentence<Word>> contextList=RrsAnalyzer.analyzeContent(textContent,analyzer);
        Iterator<Sentence<Word>> sentIter=contextList.iterator();
        while(sentIter.hasNext()){
            Sentence<Word> sentence=sentIter.next();
            for(Word term: sentence){
                if(term.getIndicator()==Constants.RR_INDICATOR){
                    int wid=term.getWordID();
                    String word=term.getWordStr();
                    String pos=term.getPos();
                    RrRef ref = new RrRef();
                    ref.setRrWord(word);
                    ref.setRrPos(pos);
                    ref.setRrSentecne(sentence.getTermSentence());
                    List<Word> subjectWords=GeneralRrsHandler.getRR(contextList, sentence, wid,window);
                    StringBuilder subjectBuilder=new StringBuilder();
                    StringBuilder subjectPosBuilder=new StringBuilder();
                    StringBuilder subjectContextBuilder=new StringBuilder();
                    Iterator<Word> subjectIterator=subjectWords.iterator();
                    while(subjectIterator.hasNext()){
                           Word subject=subjectIterator.next();
                           subjectBuilder.append(subject.getWordStr()+";");
                           subjectPosBuilder.append(subject.getPos()+";");
                           subjectContextBuilder.append(contextList.get(subject.getSentID()).getTermSentence()+"\n");
                    }
                    
                    String refSubject=subjectBuilder.toString();
                    if(refSubject.equals("")) continue;
                    ref.setReferSubject(refSubject.substring(0,refSubject.length()-1>0?refSubject.length()-1:0));
                    String refSubjectPos=subjectPosBuilder.toString();
                    ref.setReferSubjectPos(refSubjectPos.substring(0,refSubjectPos.length()-1>0?refSubjectPos.length()-1:0));
                    String refSubjectContext=subjectContextBuilder.toString();
                    ref.setReferSubjectSentence(refSubjectContext.substring(0,refSubjectContext.length()-1>0?refSubjectContext.length()-1:0));
                    refList.add(ref);
                }
            }
        }       
        return refList;
    }
    

}
