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
import org.whuims.irlab.viper.core.stub.UrlInput;
import org.whuims.irlab.viper.extractor.HtmlExtractor;
import org.whuims.irlab.viper.web.services.result.ResultMsg;

/**
 * @title UrlContentExtractorSerivce
 * @description TODO 
 * @author juyuan
 * @date 2014年11月3日
 * @version 1.0
 */
@Path("/extract")
public class UrlContentExtractorSerivce {
    
    private Logger LOG=LoggerFactory.getLogger(UrlContentExtractorSerivce.class);
    @Path("content")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String extractUrlContent(String userInput){
        UrlInput input=null;
        ResultMsg msg = new ResultMsg();
        LOG.info("user analyze  input : {} ", userInput);
        try {
            input = JSON.parseObject(userInput, UrlInput.class);
        } catch (Exception e) {
            LOG.error("input json format err! {}", userInput);
            msg.setSuccess(Boolean.FALSE);
            msg.setResult(e.getMessage());
            return JSON.toJSONString(msg);
        }
        HtmlExtractor extractor = new HtmlExtractor( input.getUrl(), "UTF-8");
        String content = extractor.extract();
        msg.setSuccess(Boolean.TRUE);
        msg.setResult(content);
        return JSON.toJSONString(msg);
    }
    
  
    
    
}
