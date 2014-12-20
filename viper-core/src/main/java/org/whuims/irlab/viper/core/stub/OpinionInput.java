/**
 * 
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package org.whuims.irlab.viper.core.stub;

import org.whuims.irlab.viper.core.InputValues;

/**
 * @title OpinionInput
 * @description TODO 
 * @author juyuan
 * @date 2014年11月3日
 * @version 1.0
 */
public class OpinionInput extends BaseUserInput{
    private String returnType=InputValues.RETURNTYPE_STRING;

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }
    
}
