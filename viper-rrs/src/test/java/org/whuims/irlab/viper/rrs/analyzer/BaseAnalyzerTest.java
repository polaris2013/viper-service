/**
 * 
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package org.whuims.irlab.viper.rrs.analyzer;

import java.io.File;

import org.whuims.irlab.viper.common.ViperConfig;
import org.whuims.irlab.viper.ictclas.IctclasAnalyzer;

/**
 *  基础分析测试类
 * @title BaseAnalyzerTest
 * @description TODO 
 * @author juyuan
 * @date 2014年8月28日
 * @version 1.0
 */
public class BaseAnalyzerTest {
    protected IctclasAnalyzer analyzer = IctclasAnalyzer.getInstance();
    protected File testFile = new File("testfile/test05.txt");
    protected String encoding = "gb2312";
    protected ViperConfig config=ViperConfig.getInstance();

}
