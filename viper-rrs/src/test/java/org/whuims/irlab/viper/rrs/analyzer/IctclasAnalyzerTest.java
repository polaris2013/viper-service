/**
 * 
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package org.whuims.irlab.viper.rrs.analyzer;

import java.io.IOException;

import org.junit.Test;

import org.whuims.irlab.viper.common.CFGKEYS;
import org.whuims.irlab.viper.common.exception.AnalyzerException;



/**
 *  分词器的测试类
 * @title IctclasAnalyzerTest
 * @description TODO 
 * @author juyuan
 * @date 2014年8月12日
 * @version 1.0
 */
public class IctclasAnalyzerTest extends BaseAnalyzerTest {
  
    /**
     * 对字符串文本进行分词
     * @throws IOException
     */
    @Test
    public void testAnalyzeText() throws AnalyzerException {
        String text = "百度云是一个共享的好工具，但它的问题很多";
        analyzer.importUserDict(config.getPropValue(CFGKEYS.ICTCLAS_USER_DICT, ""));

        String result = analyzer.analyzeText(text, 1);  // with pos tag 分词结果包含词性
//        String result2=analyzer.textSplit(text, 0);  // without pos tag 分词结果不含词性

        System.out.println(result);
    }

    /**
     *  对文件进行分词
     * @throws IOException
     */
    // @Test
    public void testAnalyzeFile() throws IOException {
        String sSrcFilename = "testfile/test05.txt";
        String sDestFilename = "testfile/output05.txt";
        boolean result = analyzer.analyzeFile(sSrcFilename, sDestFilename, 1);
        System.out.println(result);
    }
}
