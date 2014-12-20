/**
 * 
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package org.whuims.irlab.viper.stanford;

/**
 * @title StanfordEnglishAnalyzerHelper
 * @description TODO 
 * @author juyuan
 * @date 2014年11月18日
 * @version 1.0
 */
public class StanfordAnalyzerHelper {
       public static boolean isPersonEntity(String pos){
           if(pos.equals(StanfordNERLabel.EN_PERSON_ENTITY_TAG)||pos.equals(StanfordNERLabel.CN_PERSON_ENTITY_TAG)) {
               return true;
           }else{ 
               return false;
           }
       }
       
       public static  boolean isPlaceEntity(String pos){
           if(pos.equals(StanfordNERLabel.EN_PLACE_ENTITY_TAG)||pos.equals(StanfordNERLabel.CN_PLACE_ENTITY_TAG)) {
               return true;
           }else{ 
               return false;
           }
       }
       
       public static  boolean isOrgEntity(String pos){
            if(pos.equals(StanfordNERLabel.EN_ORG_ENTITY_TAG)||pos.equals(StanfordNERLabel.CN_ORG_ENTITY_TAG)) {
                return true;
            }else{ 
                return false;
            }
       }
       
       public static  boolean isTimeEntity(String pos){
           if(pos.equals(StanfordNERLabel.EN_TIME_ENTITY_TAG)) {
               return true;
           }else{ 
               return false;
           }
      }
}
