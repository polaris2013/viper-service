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
import org.whuims.irlab.viper.common.exception.AnalyzerException;
import org.whuims.irlab.viper.core.InputValues;
import org.whuims.irlab.viper.core.stub.RrsInput;
import org.whuims.irlab.viper.extractor.HtmlExtractor;
import org.whuims.irlab.viper.rrs.RrsSolver;
import org.whuims.irlab.viper.rrs.bean.RrRef;
import org.whuims.irlab.viper.web.services.result.ResultMsg;

/**
 * @title RrSolveService
 * @description TODO 
 * @author juyuan
 * @date 2014年11月2日
 * @version 1.0
 */
@Path("/rrs")
public class RrSolveService {
    
    private static Logger LOG=LoggerFactory.getLogger(RrSolveService.class);
    @Path("text")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String solveTextReference(String userInput){
  
          RrsInput  input=null;
          ResultMsg msg=new ResultMsg();
          try{
              input=JSON.parseObject(userInput, RrsInput.class);
          }catch(Exception ex){
              LOG.error("parse user input error!",ex);
              msg.setSuccess(Boolean.FALSE);
              msg.setResult(ex.getMessage());
              return JSON.toJSONString(msg);
          }
          
          String returnType= input.getReturnType();
          String text=input.getText();
          
          RrsSolver solver=new RrsSolver();
          List<RrRef> referList;
          try {
               referList=solver.solveReference(text);
              msg.setSuccess(Boolean.TRUE);
          } catch (AnalyzerException e) {
            // TODO Auto-generated catch block
              LOG.error("solve reference fail!",e);
              msg.setSuccess(Boolean.FALSE);
              msg.setResult(e.getMessage());
              return JSON.toJSONString(msg);
          }
          
          if(returnType.equals(InputValues.RETURNTYPE_STRING)){
              ArrayList<String> strList=new ArrayList<String> ();
              
              for(RrRef rrf:referList){
                   strList.add(rrf.toString());
               }
              msg.setResult(strList);
          }else if(returnType.equals(InputValues.RETURNTYPE_STRUCT)){
              msg.setResult(referList);
          }
          
          
          return JSON.toJSONString(msg);
           
    }
    
    
    @Path("url")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String solveUrlReference(String userInput){
        RrsInput  input=null;
        ResultMsg msg=new ResultMsg();
        try{
            input=JSON.parseObject(userInput, RrsInput.class);
        }catch(Exception ex){
            LOG.error("parse user input error!",ex);
            msg.setSuccess(Boolean.FALSE);
            msg.setResult(ex.getMessage());
            return JSON.toJSONString(msg);
        }
        
        String returnType= input.getReturnType();
        String url=input.getText();
        HtmlExtractor extractor=new HtmlExtractor(url,"utf-8");
        String text=extractor.extract();
        RrsSolver solver=new RrsSolver();
        List<RrRef> referList;
        try {
             referList=solver.solveReference(text);
            msg.setSuccess(Boolean.TRUE);
        } catch (AnalyzerException e) {
          // TODO Auto-generated catch block
            LOG.error("solve reference fail!",e);
            msg.setSuccess(Boolean.FALSE);
            msg.setResult(e.getMessage());
            return JSON.toJSONString(msg);
        }
        
        if(returnType.equals(InputValues.RETURNTYPE_STRING)){
            ArrayList<String> strList=new ArrayList<String> ();
            for(RrRef rrf:referList){
                 strList.add(rrf.toString());
             }
            msg.setResult(strList);
        }else if(returnType.equals(InputValues.RETURNTYPE_STRUCT)){
            msg.setResult(referList);
        }
        
        return JSON.toJSONString(msg);
    }
    
    
    
    
    
}
