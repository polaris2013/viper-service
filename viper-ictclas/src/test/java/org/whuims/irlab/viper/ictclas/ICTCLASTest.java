/**
 * 
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package org.whuims.irlab.viper.ictclas;

import org.junit.Test;

import org.whuims.irlab.viper.common.CFGKEYS;
import org.whuims.irlab.viper.common.ViperConfig;
import org.whuims.irlab.viper.common.exception.AnalyzerException;

/**
 * 分词器调用测试类
 * @title ICTCLASTest
 * @description  分词器调用测试类
 * @author juyuan
 * @date 2014年8月6日
 * @version 1.0
 */
public class ICTCLASTest {
    @Test
    public void testICTCLAS() throws AnalyzerException {
        //     File file = new File("testfile/testError.txt");
        String s = "百度云是个好工具";
        IctclasAnalyzer splitter = IctclasAnalyzer.getInstance();
        ViperConfig config = ViperConfig.getInstance();
        int n = splitter.importUserDict(config.getPropValue(CFGKEYS.ICTCLAS_USER_DICT, ""));
        System.out.println("用户词典词数：->" + n);
        s = splitter.analyzeText(s, 1).trim();

        /* 测试1 */

        System.out.println("分词结果:\n" + s);
    }
}
