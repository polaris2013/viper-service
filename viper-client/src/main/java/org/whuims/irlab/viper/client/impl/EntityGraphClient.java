/**
 * 
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package org.whuims.irlab.viper.client.impl;

import javax.ws.rs.core.MediaType;

import com.alibaba.fastjson.JSON;
import org.whuims.irlab.viper.client.BaseClient;
import org.whuims.irlab.viper.core.InputValues;
import org.whuims.irlab.viper.core.stub.EntityGraphInput;

/**
 * @title EntityGraphClient
 * @description TODO 
 * @author juyuan
 * @date 2014年11月3日
 * @version 1.0
 */
public class EntityGraphClient extends BaseClient {
     public void getEntityGraphXml(){
         EntityGraphInput input=new EntityGraphInput();
         input.setLang(InputValues.LANG_CHINESE);
         input.setText("武汉大学是中国最好的学校之一，陆伟是武汉大学的教授。");
         String msg=r.path("entitygraph").path("text").entity(JSON.toJSONString(input),MediaType.APPLICATION_JSON).post(String.class);
         System.out.println(msg);
     }
     
     public static void main(String args[]){
         EntityGraphClient egc=new EntityGraphClient();
         egc.getEntityGraphXml();
     }
}
