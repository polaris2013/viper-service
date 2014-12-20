/**
 * 
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package org.whuims.irlab.viper.client;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;


import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 * @title BaseClient
 * @description TODO 
 * @author juyuan
 * @date 2014年10月30日
 * @version 1.0
 */
public class BaseClient {
    protected URI baseURI= UriBuilder.fromUri(Config.getPropValue("predictws", "http://10.94.24.58:8080/viper-web/services")).build();
    protected ClientConfig config = new DefaultClientConfig();
    protected Client c = Client.create(config);  
    protected WebResource r=c.resource(baseURI); 
    protected String testString="中国科学院计算技术研究所在多年研究基础上，耗时一年研制出了ICTCLAS汉语词法分析系统";
    protected String testUrl="http://expo2010.sina.com.cn/news/roll/20101031/005915316.shtml";
}
