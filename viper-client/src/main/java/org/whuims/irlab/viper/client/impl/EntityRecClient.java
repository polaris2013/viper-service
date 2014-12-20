/**
 * 
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package org.whuims.irlab.viper.client.impl;

import javax.ws.rs.core.MediaType;

import com.alibaba.fastjson.JSON;
import org.whuims.irlab.viper.client.BaseClient;
import org.whuims.irlab.viper.core.InputValues;
import org.whuims.irlab.viper.core.stub.EntityRecInput;

/**
 * @title EntityRecClient
 * @description TODO 
 * @author juyuan
 * @date 2014年11月3日
 * @version 1.0
 */
public class EntityRecClient  extends BaseClient{
    
    public void  recEntities(){
        EntityRecInput rec=new EntityRecInput();
        rec.setLang(InputValues.LANG_CHINESE);
        rec.setText(testString);
        rec.setReturnType(InputValues.RETURNTYPE_STRUCT);
        String msg=r.path("entity").path("text").entity(JSON.toJSONString(rec),MediaType.APPLICATION_JSON).post(String.class);
        System.out.println(msg);
    }
    
    public static void main(String args[]){
        EntityRecClient erc=new EntityRecClient();
        erc.recEntities();
    }
}
