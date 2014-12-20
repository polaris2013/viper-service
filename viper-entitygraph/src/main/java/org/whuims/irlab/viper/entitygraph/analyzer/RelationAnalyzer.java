/**
 * 
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package org.whuims.irlab.viper.entitygraph.analyzer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.whuims.irlab.viper.common.entity.Sentence;
import org.whuims.irlab.viper.common.entity.Word;
import org.whuims.irlab.viper.entitygraph.bean.Relation;
import org.whuims.irlab.viper.entitygraph.common.EntityTypes;

/**
 * @title RelationLocator
 * @description TODO 
 * @author juyuan
 * @date 2014年9月2日
 * @version 1.0
 */
public class RelationAnalyzer {
    public static List<Relation> analyzeRelations(Sentence<Word> sent, HashMap<String, Integer> entityTable) {
        List<Relation> relationList = new ArrayList<Relation>();
        List<Word> entitylist = sent.getEntityList();
        for (int i = 0; i < entitylist.size() - 1; i++) {
            int curEtype = entitylist.get(i).getIndicator();
            int nextEtype = entitylist.get(i + 1).getIndicator();
            if (curEtype == EntityTypes.PERSON_ENTITY) {
                if (nextEtype == EntityTypes.PERSON_ENTITY) {
                    Relation r = new Relation();
                    r.setSid(entityTable.get(entitylist.get(i).getWordStr()));
                    r.setTid(entityTable.get(entitylist.get(i + 1).getWordStr()));
                    r.setDistance(entitylist.get(i + 1).getWordID() - entitylist.get(i).getWordID());
                    r.setType("accompany:共现，二者存在某种关系");
                    add(relationList, r);
                } else if (nextEtype == EntityTypes.PLACE_ENTITY) {
                    Relation r = new Relation();
                    r.setSid(entityTable.get(entitylist.get(i).getWordStr()));
                    r.setTid(entityTable.get(entitylist.get(i + 1).getWordStr()));
                    r.setDistance(entitylist.get(i + 1).getWordID() - entitylist.get(i).getWordID());
                    r.setType("Location:位于某地，住在哪里");
                    add(relationList, r);
                } else if (nextEtype == EntityTypes.ORG_ENTITY) {
                    Relation r = new Relation();
                    r.setSid(entityTable.get(entitylist.get(i).getWordStr()));
                    r.setTid(entityTable.get(entitylist.get(i + 1).getWordStr()));
                    r.setDistance(entitylist.get(i + 1).getWordID() - entitylist.get(i).getWordID());
                    r.setType("Empoly:在某处工作，受雇于");
                    add(relationList, r);
                }
            } else if (curEtype == EntityTypes.PLACE_ENTITY) {
                if (nextEtype == EntityTypes.PERSON_ENTITY) {
                    Relation r = new Relation();
                    r.setSid(entityTable.get(entitylist.get(i).getWordStr()));
                    r.setTid(entityTable.get(entitylist.get(i + 1).getWordStr()));
                    r.setDistance(entitylist.get(i + 1).getWordID() - entitylist.get(i).getWordID());
                    r.setType("Location 位置关系 ");
                    add(relationList, r);
                } else if (nextEtype == EntityTypes.PLACE_ENTITY) {
                    Relation r = new Relation();
                    r.setSid(entityTable.get(entitylist.get(i).getWordStr()));
                    r.setTid(entityTable.get(entitylist.get(i + 1).getWordStr()));
                    r.setDistance(entitylist.get(i + 1).getWordID() - entitylist.get(i).getWordID());
                    r.setType("Accompany：共现关系");
                    add(relationList, r);
                } else if (nextEtype == EntityTypes.ORG_ENTITY) {
                    Relation r = new Relation();
                    r.setSid(entityTable.get(entitylist.get(i).getWordStr()));
                    r.setTid(entityTable.get(entitylist.get(i + 1).getWordStr()));
                    r.setDistance(entitylist.get(i + 1).getWordID() - entitylist.get(i).getWordID());
                    r.setType("Location：位置关系");
                    add(relationList, r);
                }
            } else if (curEtype == EntityTypes.ORG_ENTITY) {
                if (nextEtype == EntityTypes.PERSON_ENTITY) {
                    Relation r = new Relation();
                    r.setSid(entityTable.get(entitylist.get(i).getWordStr()));
                    r.setTid(entityTable.get(entitylist.get(i + 1).getWordStr()));
                    r.setDistance(entitylist.get(i + 1).getWordID() - entitylist.get(i).getWordID());
                    r.setType("Employ：雇佣关系，员工 ");
                    add(relationList, r);
                } else if (nextEtype == EntityTypes.PLACE_ENTITY) {
                    Relation r = new Relation();
                    r.setSid(entityTable.get(entitylist.get(i).getWordStr()));
                    r.setTid(entityTable.get(entitylist.get(i + 1).getWordStr()));
                    r.setDistance(entitylist.get(i + 1).getWordID() - entitylist.get(i).getWordID());
                    r.setType("Location： 位置关系，坐落于");
                    add(relationList, r);
                } else if (nextEtype == EntityTypes.ORG_ENTITY) {
                    Relation r = new Relation();
                    r.setSid(entityTable.get(entitylist.get(i).getWordStr()));
                    r.setTid(entityTable.get(entitylist.get(i + 1).getWordStr()));
                    r.setDistance(entitylist.get(i + 1).getWordID() - entitylist.get(i).getWordID());
                    r.setType("Accompany： 共现关系,二者存在某种关系");
                    add(relationList, r);
                }
            }
        }
        return relationList;
    }

    private static void add(List<Relation> rlist, Relation r) {

        if (r.getDistance() < 4) {
            rlist.add(r);
        }

    }
}
