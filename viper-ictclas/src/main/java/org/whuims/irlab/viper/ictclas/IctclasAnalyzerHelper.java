/**
 * 
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package org.whuims.irlab.viper.ictclas;


/**
 * @title IctclasAnalyzerHelper
 * @description TODO 
 * @author juyuan
 * @date 2014年10月24日
 * @version 1.0
 */
public class IctclasAnalyzerHelper{
    public static boolean isPersonEntity(String pos){
        if(pos.equals(IctclasNERLabel.PERSON_ENTITY)||pos.equals(IctclasNERLabel.PERSON_ENTITY_EXTEND1)||pos.equals(IctclasNERLabel.PERSON_ENTITY_EXTEND2)) {
            return true;
        }else{ 
            return false;
        }
    }
    
    public static  boolean isPlaceEntity(String pos){
        if(pos.equals(IctclasNERLabel.PLACE_ENTITY)||pos.equals(IctclasNERLabel.PLACE_ENTITY_EXTEND1)) {
            return true;
        }else{ 
            return false;
        }
    }
    
    public static  boolean isOrgEntity(String pos){
         if(pos.equals(IctclasNERLabel.ORG_ENTITY)) {
             return true;
         }else{ 
             return false;
         }
    }
    
    public static  boolean isTimeEntity(String pos){
        if(pos.equals(IctclasNERLabel.TIME_ENTITY)) {
            return true;
        }else{ 
            return false;
        }
   }
}
