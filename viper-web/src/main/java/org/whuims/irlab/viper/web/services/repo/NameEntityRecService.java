/**
 * 
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package org.whuims.irlab.viper.web.services.repo;

import java.util.HashSet;

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
import org.whuims.irlab.viper.common.IAnalyzer;
import org.whuims.irlab.viper.common.ViperConfig;
import org.whuims.irlab.viper.common.analyzer.AnalyzerHelper;
import org.whuims.irlab.viper.common.exception.AnalyzerException;
import org.whuims.irlab.viper.core.InputValues;
import org.whuims.irlab.viper.core.stub.EntityRecInput;
import org.whuims.irlab.viper.extractor.HtmlExtractor;
import org.whuims.irlab.viper.ictclas.IctclasAnalyzer;
import org.whuims.irlab.viper.ictclas.IctclasAnalyzerHelper;
import org.whuims.irlab.viper.stanford.StanfordAnalyzerHelper;
import org.whuims.irlab.viper.stanford.StanfordEnglishAnalyzer;
import org.whuims.irlab.viper.web.services.result.EntityResult;
import org.whuims.irlab.viper.web.services.result.ResultMsg;

/**
 * @title NameEntityRecService
 * @description TODO 
 * @author juyuan
 * @date 2014年11月2日
 * @version 1.0
 */
@Path("/entity")
public class NameEntityRecService {
    private static Logger LOG = LoggerFactory.getLogger(NameEntityRecService.class);
    private ViperConfig config = ViperConfig.getInstance();

    @Path("text")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String recTextNE(String userInput) {
   
        EntityRecInput input = null;
        ResultMsg msg = new ResultMsg();
        try {
            input = JSONObject.parseObject(userInput, EntityRecInput.class);
        } catch (Exception ex) {
            LOG.error("parse entity rec input error! ", ex);
            msg.setSuccess(Boolean.FALSE);
            msg.setResult(ex.getMessage());
            return JSON.toJSONString(input);
        }
        String text = input.getText();
        String lang = input.getLang();
        IAnalyzer analyzer;
        if (lang.equals(InputValues.LANG_CHINESE)) {
            analyzer = IctclasAnalyzer.getInstance();
        } else {
            analyzer = StanfordEnglishAnalyzer.getInstance();
        }

        try {
            analyzer.importUserDict(config.getPropValue(CFGKEYS.ICTCLAS_USER_DICT, "nvrs/userdict.txt"));
            String result = analyzer.analyzeText(text, 1);
          
            if (input.getReturnType().equals(InputValues.RETURNTYPE_STRUCT)) {
                EntityResult er = new EntityResult();
                String[] strAr = result.split(" ");
                int n = strAr.length;

                StringBuilder personBuf = new StringBuilder();
                HashSet<String> personSet=new HashSet<String>();
                HashSet<String> placeSet=new HashSet<String>();
                HashSet<String> orgSet=new HashSet<String>();
                HashSet<String> timeSet=new HashSet<String>();
                StringBuilder placeBuf = new StringBuilder();
                StringBuilder orgBuf = new StringBuilder();
                StringBuilder timeBuf = new StringBuilder();
                for (int i = 0; i < n; i++) {
                    String tokenstr = strAr[i];
                    String pos = AnalyzerHelper.getWordPos(tokenstr); // 获取词性
                    String wordStr = AnalyzerHelper.getWord(tokenstr); // 获取词语
                    if (IctclasAnalyzerHelper.isPersonEntity(pos) || StanfordAnalyzerHelper.isPersonEntity(pos)) {
                        if(!personSet.contains(wordStr)){
                            personBuf.append(wordStr + ",");
                            personSet.add(wordStr);
                        }      
                    } else if (IctclasAnalyzerHelper.isPlaceEntity(pos) || StanfordAnalyzerHelper.isPlaceEntity(pos)) {
                        if(!placeSet.contains(wordStr)){
                              placeBuf.append(wordStr + ",");
                              placeSet.add(wordStr);
                         }   
                    } else if (IctclasAnalyzerHelper.isTimeEntity(pos) || StanfordAnalyzerHelper.isTimeEntity(pos)) {
                         if(!timeSet.contains(wordStr)){
                             timeBuf.append(wordStr + ",");
                             timeSet.add(wordStr);
                         }
                       
                    } else if (IctclasAnalyzerHelper.isOrgEntity(pos) || StanfordAnalyzerHelper.isOrgEntity(pos)) {    
                        if(!orgSet.contains(wordStr)){
                            orgBuf.append(wordStr + ",");
                            orgSet.add(wordStr);
                        }
                    }

                }
               
                String orgs = orgBuf.toString();
         
                String persons = personBuf.toString();
                String places = placeBuf.toString();
                String times = timeBuf.toString();
                
                orgBuf=null;
                personBuf=null;
                timeBuf=null;
                placeBuf=null;
                
                orgSet=null;
                placeSet=null;
                personSet=null;
                timeSet=null;
                er.setPlacesEs(places.length() > 1 ? places.substring(0, places.length() - 1) : places);
                er.setPersonEs(persons.length() > 1 ? persons.substring(0, persons.length() - 1) : persons);
                er.setOrganEs(orgs.length() > 1 ? orgs.substring(0, orgs.length() - 1) : orgs);
                er.setTimeEs(times.length() > 1 ? times.substring(0, times.length() - 1) : times);
                msg.setResult(er);
            } else {
                msg.setResult(result);
            }
            msg.setSuccess(Boolean.TRUE);
          
            return JSON.toJSONString(msg);
        } catch (AnalyzerException e) {
            // TODO Auto-generated catch block
            LOG.error("stanford ner error!", e);
            msg.setSuccess(Boolean.FALSE);
            msg.setResult(e.getMessage());
            return JSON.toJSONString(msg);
        }

    }

    @Path("url")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String recUrlNE(String userInput) {
        EntityRecInput input = null;
        ResultMsg msg = new ResultMsg();
        try {
            input = JSONObject.parseObject(userInput, EntityRecInput.class);
        } catch (Exception ex) {
            LOG.error("parse entity rec input error! ", ex);
            msg.setSuccess(Boolean.FALSE);
            msg.setResult(ex.getMessage());
            return JSON.toJSONString(input);
        }

        String url = input.getText();
        HtmlExtractor extractor = new HtmlExtractor(url, "utf-8");
        String text = extractor.extract();
        String lang = input.getLang();
        IAnalyzer analyzer;
        if (lang.equals(InputValues.LANG_CHINESE)) {
            analyzer = IctclasAnalyzer.getInstance();
        } else {
            analyzer = StanfordEnglishAnalyzer.getInstance();
        }

        try {
            analyzer.importUserDict(config.getPropValue(CFGKEYS.ICTCLAS_USER_DICT, "nvrs/userdict.txt"));
            String result = analyzer.analyzeText(text, 1);
            msg.setSuccess(Boolean.TRUE);
            if (input.getReturnType().equals(InputValues.RETURNTYPE_STRUCT)) {
                EntityResult er = new EntityResult();
                String[] strAr = result.split(" ");
                int n = strAr.length;
                
                HashSet<String> personSet=new HashSet<String>();
                HashSet<String> placeSet=new HashSet<String>();
                HashSet<String> orgSet=new HashSet<String>();
                HashSet<String> timeSet=new HashSet<String>();
                StringBuilder placeBuf = new StringBuilder();
                StringBuilder orgBuf = new StringBuilder();
                StringBuilder timeBuf = new StringBuilder();
                StringBuilder personBuf = new StringBuilder();
                for (int i = 0; i < n; i++) {
                    String tokenstr = strAr[i];
                    String pos = AnalyzerHelper.getWordPos(tokenstr); // 获取词性
                    String wordStr = AnalyzerHelper.getWord(tokenstr); // 获取词语
                    LOG.info(wordStr+"\t"+pos);
                    if (IctclasAnalyzerHelper.isPersonEntity(pos) || StanfordAnalyzerHelper.isPersonEntity(pos)) {
                        if(!personSet.contains(wordStr)){
                            personBuf.append(wordStr + ",");
                            personSet.add(wordStr);
                        }      
                    } else if (IctclasAnalyzerHelper.isPlaceEntity(pos) || StanfordAnalyzerHelper.isPlaceEntity(pos)) {
                        if(!placeSet.contains(wordStr)){
                              placeBuf.append(wordStr + ",");
                              placeSet.add(wordStr);
                         }   
                    } else if (IctclasAnalyzerHelper.isTimeEntity(pos) || StanfordAnalyzerHelper.isTimeEntity(pos)) {
                         if(!timeSet.contains(wordStr)){
                             timeBuf.append(wordStr + ",");
                             timeSet.add(wordStr);
                         }
                       
                    } else if (IctclasAnalyzerHelper.isOrgEntity(pos) || StanfordAnalyzerHelper.isOrgEntity(pos)) {
                        LOG.info("org word:"+wordStr+"pos: "+pos);
                        if(!orgSet.contains(wordStr)){    
                            orgBuf.append(wordStr + ",");
                            orgSet.add(wordStr);
                        }
                    }

                }

                String orgs = orgBuf.toString();
                String persons = personBuf.toString();
                String places = placeBuf.toString();
                String times = timeBuf.toString();
                
                orgBuf=null;
                personBuf=null;
                timeBuf=null;
                placeBuf=null;
                
                orgSet=null;
                placeSet=null;
                personSet=null;
                timeSet=null;
                er.setPlacesEs(places.length() > 1 ? places.substring(0, places.length() - 1) : places);
                er.setPersonEs(persons.length() > 1 ? persons.substring(0, persons.length() - 1) : persons);
                er.setOrganEs(orgs.length() > 1 ? orgs.substring(0, orgs.length() - 1) : orgs);
                er.setTimeEs(times.length() > 1 ? times.substring(0, times.length() - 1) : times);
                msg.setResult(er);
            } else {
                msg.setResult(result);
            }
            msg.setSuccess(Boolean.TRUE);
            return JSON.toJSONString(msg);
        } catch (AnalyzerException e) {
            // TODO Auto-generated catch block
            LOG.error("stanford ner error!", e);
            msg.setSuccess(Boolean.FALSE);
            msg.setResult(e.getMessage());
            return JSON.toJSONString(msg);
        }
    }
}
