/**
 * 
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package org.whuims.irlab.viper.client.impl;

import javax.ws.rs.core.MediaType;

import com.alibaba.fastjson.JSON;
import org.whuims.irlab.viper.client.BaseClient;
import org.whuims.irlab.viper.core.stub.RrsInput;

/**
 * @title RrsClient
 * @description TODO 
 * @author juyuan
 * @date 2014年11月3日
 * @version 1.0
 */
public class RrsClient  extends BaseClient {
    public void solveReference(){
        RrsInput input=new RrsInput();
//        input.setReturnType(InputValues.RETURNTYPE_STRUCT);
        input.setText("百度云是分享的好工具， 但是它有很多问题");
        String msg=r.path("rrs").path("text").entity(JSON.toJSONString(input),MediaType.APPLICATION_JSON).post(String.class);
        System.out.println(msg);
    }
    
    public static void main(String args[]){
        RrsClient rc=new RrsClient();
        rc.solveReference();
    }
}
