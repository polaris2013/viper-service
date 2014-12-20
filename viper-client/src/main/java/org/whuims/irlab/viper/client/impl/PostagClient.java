/**
 * 
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package org.whuims.irlab.viper.client.impl;

import javax.ws.rs.core.MediaType;

import com.alibaba.fastjson.JSON;
import org.whuims.irlab.viper.client.BaseClient;
import org.whuims.irlab.viper.core.stub.PostagInput;

/**
 * @title PostagClient
 * @description TODO 
 * @author juyuan
 * @date 2014年11月3日
 * @version 1.0
 */
public class PostagClient extends BaseClient {
    
    
      public void postagText(){
          PostagInput input=new PostagInput();
          input.setText(testString);
          String msg=r.path("postag").path("text").entity(JSON.toJSONString(input),MediaType.APPLICATION_JSON).post(String.class);
          System.out.println(msg);
      }
      public void postagUrl(){
          PostagInput input=new PostagInput();
          input.setText(testUrl);
          String msg=r.path("postag").path("url").entity(JSON.toJSONString(input),MediaType.APPLICATION_JSON).post(String.class);
          System.out.println(msg);
      }
      public static void main(String args[]){
          PostagClient pclient=new PostagClient();
          pclient.postagText();
          pclient.postagUrl();
      }
}
