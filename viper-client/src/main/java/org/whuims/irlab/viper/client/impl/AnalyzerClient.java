/**
 * 
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package org.whuims.irlab.viper.client.impl;

import javax.ws.rs.core.MediaType;

import com.alibaba.fastjson.JSON;
import org.whuims.irlab.viper.client.BaseClient;
import org.whuims.irlab.viper.core.InputValues;
import org.whuims.irlab.viper.core.stub.AnalyzerInput;

/**
 * @title AnalyzerClient
 * @description TODO 
 * @author juyuan
 * @date 2014年10月30日
 * @version 1.0
 */
public class AnalyzerClient extends BaseClient{
    public void ictclasSplit(){
       AnalyzerInput input=new AnalyzerInput();
       input.setAnalyzer(InputValues.ICTCLAS);
        input.setText("中国科学院计算技术研究所在多年研究基础上，耗时一年研制出了ICTCLAS汉语词法分析系统");
      
        String msg=r.path("analyze").path("text").entity(JSON.toJSONString(input),MediaType.APPLICATION_JSON).post(String.class);
       System.out.println(msg);
    }
  
    public void luceneSplit(){
        AnalyzerInput input=new AnalyzerInput();
        input.setAnalyzer(InputValues.LUCENE);
        input.setText("中国科学院计算技术研究所在多年研究基础上，耗时一年研制出了ICTCLAS汉语词法分析系统");
        
        String msg=r.path("analyze").path("text").entity(JSON.toJSONString(input),MediaType.APPLICATION_JSON).post(String.class);
        System.out.println(msg);
    }
    
    
    public static void main(String args[]){
        AnalyzerClient ac=new AnalyzerClient();
        ac.luceneSplit();
        ac.ictclasSplit();
    }
}
