/**
 * 
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package org.whuims.irlab.viper.entitygraph.processor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;





import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.whuims.irlab.viper.common.entity.Sentence;
import org.whuims.irlab.viper.common.entity.Word;
import org.whuims.irlab.viper.common.exception.AnalyzerException;
import org.whuims.irlab.viper.entitygraph.analyzer.RelationAnalyzer;
import org.whuims.irlab.viper.entitygraph.analyzer.EGAnalyzer;
import org.whuims.irlab.viper.entitygraph.bean.Relation;
import org.whuims.irlab.viper.entitygraph.common.EntityTypes;
import org.whuims.irlab.viper.entitygraph.common.LanguageOpt;
import org.whuims.irlab.viper.entitygraph.utils.Filter;

/**
 * @title TextProcessor
 * @description TODO 
 * @author juyuan
 * @date 2014年9月1日
 * @version 1.0
 */
public class EntityProcessor {

    private int langOpt = 1;
    private String srcContent = "";

    private Logger LOG = LoggerFactory.getLogger(EntityProcessor.class);
    private List<Sentence<Word>> sentenceList = new ArrayList<Sentence<Word>>();

    private List<Relation> relationList = new ArrayList<Relation>();

    private List<Word> personList = new ArrayList<Word>();
    private List<Word> placeList = new ArrayList<Word>();
    private List<Word> orgList = new ArrayList<Word>();

    public EntityProcessor(String srcContent, int lang) {
        this.langOpt = lang;
        this.srcContent = srcContent;

    }

    public void process() {
        this.processSentences();
        this.processGlobalEntities();
        this.processRelations();
    }

    private void processSentences() {
        LOG.info("EG analyze content ...");
        List<Sentence<Word>> rawSentences;
        try {
            rawSentences = EGAnalyzer.analyzeContent(srcContent, langOpt);
        } catch (AnalyzerException e) {
            // TODO Auto-generated catch block
            LOG.error("build sentence list error!", e);
            rawSentences = new ArrayList<Sentence<Word>>();
        }
        
        sentenceList = Filter.filterSentenceWithInsufficientEntities(rawSentences, 1); //filter
        LOG.info("build sentence list complete！...");
    }

    private void processGlobalEntities() {

       
        List<String> entityList = new ArrayList<String>();
        for (Sentence<Word> sent : sentenceList) {
            List<Word> sentEList = sent.getEntityList();
            for (Word word : sentEList) {
                String wordstr = word.getWordStr().trim();
                if (!entityList.contains(wordstr)) {
                    entityList.add(wordstr);
                    int entityType = word.getIndicator();
                    if (entityType == EntityTypes.PERSON_ENTITY) {
                             personList.add(word); 
                    } else if (entityType == EntityTypes.PLACE_ENTITY) {
                             placeList.add(word);
                    } else if (entityType == EntityTypes.ORG_ENTITY) {
                             orgList.add(word);
                    }
                    entityWordTable.put(wordstr, word);
                }
            }
        }
        for (int i = 0; i < entityList.size(); i++) {
            entityTable.put(entityList.get(i), i);//全局实体词表
            entityInverseTable.put(i, entityList.get(i));
        }
        LOG.info("process global entity list complete!");
    }
   
    public List<Word> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Word> personList) {
        this.personList = personList;
    }

    public List<Word> getPlaceList() {
        return placeList;
    }

    public void setPlaceList(List<Word> placeList) {
        this.placeList = placeList;
    }

    public List<Word> getOrgList() {
        return orgList;
    }

    public void setOrgList(List<Word> orgList) {
        this.orgList = orgList;
    }

    public HashMap<String, Word> getEntityWordTable() {
        return entityWordTable;
    }

    public void setEntityWordTable(HashMap<String, Word> entityWordTable) {
        this.entityWordTable = entityWordTable;
    }

    public HashMap<Integer, String> getEntityInverseTable() {
        return entityInverseTable;
    }

    public void setEntityInverseTable(HashMap<Integer, String> entityInverseTable) {
        this.entityInverseTable = entityInverseTable;
    }

    private void processRelations() {
        for (Sentence<Word> sent : sentenceList) {
            List<Relation> urel = RelationAnalyzer.analyzeRelations(sent, entityTable);
            for (Relation r : urel) {
                if (!contain(r)) {
                    relationList.add(r);
                }
            }
        }

        LOG.info("process relation list complete!");
    }

    public boolean contain(Relation r) {
        boolean dict = false;
        for (Relation rr : relationList) {
            if ((rr.getSid() == r.getSid()) && (rr.getTid() == r.getTid())) {
                int i = rr.getFreq();
                rr.setFreq(i + 1);
                int d1 = r.getDistance();
                int d2 = rr.getDistance();
                if (d1 < d2) {
                    rr.setDistance(d1);
                }
                dict = true;
            }
        }
        return dict;
    }

    private HashMap<String, Integer> entityTable = new HashMap<String, Integer>();
    private HashMap<Integer, String> entityInverseTable = new HashMap<Integer, String>();
    private HashMap<String, Word> entityWordTable = new HashMap<String, Word>();

    public EntityProcessor(String srcContent) {
        this(srcContent, LanguageOpt.CHINESE);
    }

    public List<Sentence<Word>> getSentenceList() {
        return sentenceList;
    }

    public void setSentenceList(List<Sentence<Word>> sentenceList) {
        this.sentenceList = sentenceList;
    }

    public List<Relation> getRelationList() {
        return relationList;
    }

    public void setRelationList(List<Relation> relationList) {
        this.relationList = relationList;
    }

    public HashMap<String, Integer> getEntityTable() {
        return entityTable;
    }

    public void setEntityTable(HashMap<String, Integer> entityTable) {
        this.entityTable = entityTable;
    }

}
