/**
 * 
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package org.whuims.irlab.viper.entitygraph.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.whuims.irlab.viper.common.entity.Sentence;
import org.whuims.irlab.viper.common.entity.Word;


/**
 * @title Filter
 * @description TODO 
 * @author juyuan
 * @date 2014年9月1日
 * @version 1.0
 */
public class Filter {
     private static Logger LOG=LoggerFactory.getLogger(Filter.class);
     public static List<Sentence<Word>>  filterSentenceWithInsufficientEntities(List<Sentence<Word>> oriSentList,int lower_bound){
            List<Sentence<Word>> sentList=new ArrayList<Sentence<Word>>();
            Iterator<Sentence<Word>> iter=oriSentList.iterator();
            while(iter.hasNext()){
                Sentence<Word> sent=iter.next();
                LOG.info("filter sent: "+sent.getTermSentence());
                List<Word> entityList=sent.getEntityList();
                if(entityList.size()>lower_bound){
                    sentList.add(sent);
                } 
            }
            return sentList;
     }
}
