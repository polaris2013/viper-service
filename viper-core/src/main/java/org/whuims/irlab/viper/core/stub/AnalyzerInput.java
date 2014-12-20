/**
 * 
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package org.whuims.irlab.viper.core.stub;

import org.whuims.irlab.viper.core.InputValues;



/**
 * @title AnalyzerInput
 * @description TODO 
 * @author juyuan
 * @date 2014年10月30日
 * @version 1.0
 */
public class AnalyzerInput extends BaseUserInput {
    private String analyzer = InputValues.ICTCLAS;


    public String getAnalyzer() {
        return analyzer;
    }

    public void setAnalyzer(String analyzer) {
        this.analyzer = analyzer;
    }

    

}
