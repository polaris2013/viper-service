/**
 * 
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package org.whuims.irlab.viper.web.services.repo;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import org.whuims.irlab.viper.common.CFGKEYS;
import org.whuims.irlab.viper.common.ViperConfig;
import org.whuims.irlab.viper.common.exception.AnalyzerException;
import org.whuims.irlab.viper.core.InputValues;
import org.whuims.irlab.viper.core.stub.AnalyzerInput;
import org.whuims.irlab.viper.extractor.HtmlExtractor;
import org.whuims.irlab.viper.ictclas.IctclasAnalyzer;
import org.whuims.irlab.viper.stanford.StanfordChineseAnalyzer;
import org.whuims.irlab.viper.web.services.result.ResultMsg;

/**
 * @title AnalyzerService
 * @description TODO 
 * @author juyuan
 * @date 2014年10月30日
 * @version 1.0
 */
@Path("/analyze")
public class AnalyzerService {
    private static Logger LOG = LoggerFactory.getLogger(AnalyzerService.class);
    private ViperConfig config = ViperConfig.getInstance();

    @Path("text")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String splitTextContent(String userInput) {
        AnalyzerInput input = null;     
        ResultMsg msg = new ResultMsg();
        LOG.info("user analyze  input : {} ", userInput);
        try {
            input = JSON.parseObject(userInput, AnalyzerInput.class);
        } catch (Exception e) {
            LOG.error("input json format err! {}", userInput);
            msg.setSuccess(Boolean.FALSE);
            msg.setResult(e.getMessage());
            return JSON.toJSONString(msg);
        }
        String analyzerType = input.getAnalyzer();
        String result = "";
        if (analyzerType.equals(InputValues.ICTCLAS)) {
            IctclasAnalyzer analyzer = IctclasAnalyzer.getInstance();

            try {
                analyzer.importUserDict(config.getPropValue(CFGKEYS.ICTCLAS_USER_DICT, "nvrs/userdict.txt"));
                result = analyzer.analyzeText(input.getText(), 0);
                msg.setSuccess(Boolean.TRUE);
                msg.setResult(result);
            } catch (AnalyzerException e) {
                // TODO Auto-generated catch block
                LOG.error("ictclas analyzer fail!", e);
                msg.setSuccess(Boolean.FALSE);
                msg.setResult((String) e.getMessage());
                return JSON.toJSONString(msg);
            }
        } else if (analyzerType.equals(InputValues.LUCENE) || analyzerType.equals(InputValues.STANFORD)) {
            StanfordChineseAnalyzer analyzer = StanfordChineseAnalyzer.getInstance();
            try {
                result = analyzer.analyzeText(input.getText(), 0);
                msg.setSuccess(Boolean.TRUE);
                msg.setResult(result);
            } catch (AnalyzerException e) {
                // TODO Auto-generated catch block
                LOG.error("lucene analyzer fail!", e);
                msg.setSuccess(Boolean.FALSE);
                msg.setResult((String) e.getMessage());
                return JSON.toJSONString(msg);
            }

        }
        return JSON.toJSONString(msg);
    }

    @Path("url")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String splitUrlContent(String userInput) {
        AnalyzerInput input = null;
        ResultMsg msg = new ResultMsg();
        LOG.info("user analyze  input : {} ", input);
        try {
            input = JSON.parseObject(userInput, AnalyzerInput.class);
        } catch (Exception e) {
            LOG.error("input json format err! {}", userInput);
            msg.setSuccess(Boolean.FALSE);
            msg.setResult(e.getMessage());
            return JSON.toJSONString(msg);
        }
        String url = input.getText();
        HtmlExtractor extractor = new HtmlExtractor(url, "UTF-8");
        String content = extractor.extract();
        String analyzerType = input.getAnalyzer();
        String result;
        if (analyzerType.equals(InputValues.ICTCLAS)) {
            IctclasAnalyzer analyzer = IctclasAnalyzer.getInstance();

            try {
                analyzer.importUserDict(config.getPropValue(CFGKEYS.ICTCLAS_USER_DICT, "nvrs/userdict.txt"));
                result = analyzer.analyzeText(content, 0);
                msg.setSuccess(Boolean.TRUE);
                msg.setResult(result);
            } catch (AnalyzerException e) {
                // TODO Auto-generated catch block
                LOG.error("ictclas analyzer fail!", e);
                msg.setSuccess(Boolean.FALSE);
                msg.setResult((String) e.getMessage());
                return JSON.toJSONString(msg);
            }
        } else if (analyzerType.equals(InputValues.LUCENE) || analyzerType.equals(InputValues.STANFORD)) {
            StanfordChineseAnalyzer analyzer = StanfordChineseAnalyzer.getInstance();
            try {
                result = analyzer.analyzeText(content, 0);
                msg.setSuccess(Boolean.TRUE);
                msg.setResult(result);
            } catch (AnalyzerException e) {
                // TODO Auto-generated catch block
                LOG.error("lucene analyzer fail!", e);
                msg.setSuccess(Boolean.FALSE);
                msg.setResult((String) e.getMessage());
                return JSON.toJSONString(msg);
            }
        }
        return JSON.toJSONString(msg);

    }

    @Path("")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String welcome() {
        return "welcome to viper analyze service!";
    }
}
