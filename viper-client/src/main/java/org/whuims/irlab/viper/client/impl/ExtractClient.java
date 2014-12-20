/**
 * 
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package org.whuims.irlab.viper.client.impl;

import javax.ws.rs.core.MediaType;

import com.alibaba.fastjson.JSON;
import org.whuims.irlab.viper.client.BaseClient;
import org.whuims.irlab.viper.core.stub.UrlInput;

/**
 * @title ExtractClient
 * @description TODO 
 * @author juyuan
 * @date 2014年11月3日
 * @version 1.0
 */
public class ExtractClient extends BaseClient {
     public void extractUrlContent(){
        UrlInput input=new UrlInput();
        input.setUrl(testUrl);
//        input.setEncoding("gb2312");
        String msg= r.path("extract").path("content").entity(JSON.toJSONString(input), MediaType.APPLICATION_JSON).post(String.class);
        System.out.println(msg);
     }
     
     public static void main(String args[]){
        ExtractClient ec=new ExtractClient();
        ec.extractUrlContent();
     }
}
