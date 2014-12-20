/**
 * 
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package org.whuims.irlab.viper.client.impl;

import javax.ws.rs.core.MediaType;

import com.alibaba.fastjson.JSON;
import org.whuims.irlab.viper.client.BaseClient;
import org.whuims.irlab.viper.core.InputValues;
import org.whuims.irlab.viper.core.stub.OpinionInput;

/**
 * @title OpmClient
 * @description TODO 
 * @author juyuan
 * @date 2014年11月3日
 * @version 1.0
 */
public class OpmClient extends BaseClient{
    public void minePlainOpinion(){
        OpinionInput input=new OpinionInput();
//        input.setReturnType();
        input.setText("陈是先知，他认为这是个阴谋。");
        String msg=r.path("opinion").path("text").entity(JSON.toJSONString(input),MediaType.APPLICATION_JSON).post(String.class);
        System.out.println(msg);
    }
    
    public void mineStructOpinion(){
        OpinionInput input=new OpinionInput();
        input.setText("陈是先知，他认为这是个阴谋。");
        input.setReturnType(InputValues.RETURNTYPE_STRUCT);
        String msg=r.path("opinion").path("text").entity(JSON.toJSONString(input),MediaType.APPLICATION_JSON).post(String.class);
        System.out.println(msg);
    }
    
    public static void main(String args[]){
        OpmClient oc=new OpmClient();
        oc.minePlainOpinion();
        oc.mineStructOpinion();
    }
}
