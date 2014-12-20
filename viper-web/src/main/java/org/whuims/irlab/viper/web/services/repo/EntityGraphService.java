/**
 * 
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package org.whuims.irlab.viper.web.services.repo;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import org.whuims.irlab.viper.core.InputValues;
import org.whuims.irlab.viper.core.stub.EntityGraphInput;
import org.whuims.irlab.viper.entitygraph.ContentModeLoader;
import org.whuims.irlab.viper.entitygraph.EntityGraphFacade;
import org.whuims.irlab.viper.entitygraph.common.LanguageOpt;
import org.whuims.irlab.viper.web.services.result.ResultMsg;

/**
 * @title EntityGraphService
 * @description TODO 
 * @author juyuan
 * @date 2014年11月2日
 * @version 1.0
 */
@Path("/entitygraph")
public class EntityGraphService {
    private static Logger LOG=LoggerFactory.getLogger(EntityGraphService.class);
    @Path("text")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
   public String createTextEntityGraph(String userInput){
           EntityGraphInput input=null;
           ResultMsg msg=new ResultMsg();
           try{
             input=JSON.parseObject(userInput, EntityGraphInput.class);
           }catch(Exception ex){
             LOG.error("parse user input error! ",ex);
             msg.setSuccess(Boolean.FALSE);
             msg.setResult(ex.getMessage());
             return JSON.toJSONString(msg);
           }
           String lang=input.getLang();
           String text=input.getText();
           EntityGraphFacade facade=null;;
           if(lang.equals(InputValues.LANG_CHINESE)){
                 facade=new EntityGraphFacade(text,ContentModeLoader.STRING_MODE,LanguageOpt.CHINESE);
           }else if(lang.equals(InputValues.LANG_ENGLISH)){
               facade=new EntityGraphFacade(text,ContentModeLoader.STRING_MODE,LanguageOpt.ENGLISH);
           }
           if(facade!=null){
              msg.setResult(facade.runBuild());
              msg.setSuccess(Boolean.TRUE);
           }else {
               LOG.error("entitygraph facade init error!");
               msg.setSuccess(Boolean.FALSE);
               msg.setResult("entitygraph facade init error!");
           }
           
           return JSON.toJSONString(msg);
   }
   @Path("url")
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.TEXT_PLAIN)
   public String createUrlEntityGraph(String userInput){
       EntityGraphInput input=null;
       ResultMsg msg=new ResultMsg();
       try{
         input=JSON.parseObject(userInput, EntityGraphInput.class);
       }catch(Exception ex){
         LOG.error("parse user input error! ",ex);
         msg.setSuccess(Boolean.FALSE);
         msg.setResult(ex.getMessage());
         return JSON.toJSONString(msg);
       }
       String lang=input.getLang();
       String text=input.getText();
       EntityGraphFacade facade=null;;
       if(lang.equals(InputValues.LANG_CHINESE)){
             facade=new EntityGraphFacade(text,ContentModeLoader.URL_MODE,LanguageOpt.CHINESE);
       }else if(lang.equals(InputValues.LANG_ENGLISH)){
           facade=new EntityGraphFacade(text,ContentModeLoader.URL_MODE,LanguageOpt.ENGLISH);
       }
       if(facade!=null){
          msg.setResult(facade.runBuild());
          msg.setSuccess(Boolean.TRUE);
       }else {
           LOG.error("entitygraph facade init error!");
           msg.setSuccess(Boolean.FALSE);
           msg.setResult("entitygraph facade init error!");
       }
       
       return JSON.toJSONString(msg);
   }
}
