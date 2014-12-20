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
import com.alibaba.fastjson.JSONObject;
import org.whuims.irlab.viper.common.CFGKEYS;
import org.whuims.irlab.viper.common.ViperConfig;
import org.whuims.irlab.viper.common.exception.AnalyzerException;
import org.whuims.irlab.viper.core.stub.PostagInput;
import org.whuims.irlab.viper.extractor.HtmlExtractor;
import org.whuims.irlab.viper.ictclas.IctclasAnalyzer;
import org.whuims.irlab.viper.web.services.result.ResultMsg;

/**
 * @title PosTagger
 * @description TODO 
 * @author juyuan
 * @date 2014年11月2日
 * @version 1.0
 */

@Path("/postag")
public class PosTaggerService {
    private static Logger LOG = LoggerFactory.getLogger(PosTaggerService.class);
    private ViperConfig config=ViperConfig.getInstance();
    @Path("text")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String postagText(String userInput) {
        LOG.info("user analyze  input : {} ", userInput);
        PostagInput input = null;
        ResultMsg msg = new ResultMsg();
        
        try {
            input = JSONObject.parseObject(userInput, PostagInput.class);
        } catch (Exception e) {
            LOG.error("input json format err! {}", userInput);
            msg.setSuccess(Boolean.FALSE);
            msg.setResult(e.getMessage());
            return JSON.toJSONString(msg);
        }
        IctclasAnalyzer analyzer=IctclasAnalyzer.getInstance();
        
        try {
            analyzer.importUserDict( config.getPropValue(CFGKEYS.ICTCLAS_USER_DICT, "nvrs/userdict.txt"));
            String result = analyzer.analyzeText(input.getText(), 1);
            msg.setSuccess(Boolean.TRUE);
            msg.setResult(result);
        } catch (AnalyzerException e) {
            // TODO Auto-generated catch block
            LOG.error("ictclas pos tag fail!", e);
            msg.setSuccess(Boolean.FALSE);
            msg.setResult((String) e.getMessage());
            return JSON.toJSONString(msg);
        }
        
        
        return JSON.toJSONString(msg);

    }

    @Path("url")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String postagUrl(String userInput) {
        PostagInput input = null;
        ResultMsg msg = new ResultMsg();
        LOG.info("user analyze  input : {} ", userInput);
        try {
            input = JSONObject.parseObject(userInput, PostagInput.class);
            
        } catch (Exception e) {
            LOG.error("input json format err! {}", userInput);
            msg.setSuccess(Boolean.FALSE);
            msg.setResult(e.getMessage());
            return JSON.toJSONString(msg);
        }
        
       String url=input.getText();
       HtmlExtractor extractor = new HtmlExtractor(url, "UTF-8");
       String content = extractor.extract();
       IctclasAnalyzer analyzer=IctclasAnalyzer.getInstance();
       try {
           analyzer.importUserDict( config.getPropValue(CFGKEYS.ICTCLAS_USER_DICT, "nvrs/userdict.txt"));
           String result = analyzer.analyzeText(content, 1);
           msg.setSuccess(Boolean.TRUE);
           msg.setResult(result);
       } catch (AnalyzerException e) {
           // TODO Auto-generated catch block
           LOG.error("ictclas pos tag fail!", e);
           msg.setSuccess(Boolean.FALSE);
           msg.setResult((String) e.getMessage());
           return JSON.toJSONString(msg);
       }
       
       
       return JSON.toJSONString(msg);
    }

}
