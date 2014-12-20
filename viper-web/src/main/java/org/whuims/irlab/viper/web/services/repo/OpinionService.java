/**
 * 
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package org.whuims.irlab.viper.web.services.repo;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.whuims.irlab.viper.common.exception.AnalyzerException;
import org.whuims.irlab.viper.core.InputValues;
import org.whuims.irlab.viper.core.stub.OpinionInput;
import org.whuims.irlab.viper.extractor.HtmlExtractor;
import org.whuims.irlab.viper.opinion.ViewMiner;
import org.whuims.irlab.viper.opinion.entity.View;
import org.whuims.irlab.viper.web.services.result.ResultMsg;

/**
 * @title OpinionService
 * @description TODO 
 * @author juyuan
 * @date 2014年11月2日
 * @version 1.0
 */
@Path("/opinion")
public class OpinionService {
    private static Logger LOG=LoggerFactory.getLogger(OpinionService.class );
     @Path("text")
     @POST
     @Consumes(MediaType.APPLICATION_JSON)
     @Produces(MediaType.TEXT_PLAIN)
     public String mineTextOpinion(String userInput){
         LOG.info("user analyze  input : {} ", userInput);
         OpinionInput input=null;
         ResultMsg msg=new ResultMsg();
         try{
           input=JSONObject.parseObject(userInput,OpinionInput.class);
         }catch(Exception ex){
             LOG.error("parse user input error!",ex);
             msg.setSuccess(Boolean.FALSE);
             msg.setResult(ex.getMessage());
             return JSON.toJSONString(msg);
         }
         String text=input.getText();
         String returnType=input.getReturnType();
         ViewMiner opm=new ViewMiner();
         List<View> viewList;
         try {
            viewList=opm.extractView(text);
        } catch (AnalyzerException e) {
            // TODO Auto-generated catch block
            LOG.error("mining view error!",e);
            msg.setSuccess(Boolean.FALSE);
            msg.setResult(e.getMessage());
            return JSON.toJSONString(msg);
        }
         
        if(returnType.equals(InputValues.RETURNTYPE_STRING)){
            List<String> strList=new ArrayList<String>();
            for(View view:viewList){
                strList.add(view.toString());
            }
            msg.setResult(strList);
         
        }else if(returnType.equals(InputValues.RETURNTYPE_STRUCT)){
            msg.setResult(viewList);
         
        }
        return JSON.toJSONString(msg);
      }
     
     @Path("url")
     @POST
     @Consumes(MediaType.APPLICATION_JSON)
     @Produces(MediaType.TEXT_PLAIN)
     public String mineUrlOpinion(String userInput){
         OpinionInput input=null;
         ResultMsg msg=new ResultMsg();
         try{
           input=JSONObject.parseObject(userInput,OpinionInput.class);
         }catch(Exception ex){
             LOG.error("parse user input error!",ex);
             msg.setSuccess(Boolean.FALSE);
             msg.setResult(ex.getMessage());
             return JSON.toJSONString(msg);
         }
         String url=input.getText();
         String returnType=input.getReturnType();
         
         HtmlExtractor extractor=new HtmlExtractor(url,"utf-8");
         String text=extractor.extract();
         ViewMiner opm=new ViewMiner();
         List<View> viewList;
         try {
            viewList=opm.extractView(text);
        } catch (AnalyzerException e) {
            // TODO Auto-generated catch block
            LOG.error("mining view error!",e);
            msg.setSuccess(Boolean.FALSE);
            msg.setResult(e.getMessage());
            return JSON.toJSONString(msg);
        }
         
        if(returnType.equals(InputValues.RETURNTYPE_STRING)){
            List<String> strList=new ArrayList<String>();
            for(View view:viewList){
                strList.add(view.toString());
            }
            msg.setResult(strList);
         
        }else if(returnType.equals(InputValues.RETURNTYPE_STRUCT)){
            msg.setResult(viewList);
         
        }
        return JSON.toJSONString(msg);
     }
         
}
