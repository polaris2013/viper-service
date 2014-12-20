/**
 * 
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package org.whuims.irlab.viper.core.stub;

import org.whuims.irlab.viper.core.InputValues;

/**
 * @title EntityRecInput
 * @description TODO 
 * @author juyuan
 * @date 2014年11月3日
 * @version 1.0
 */
public class EntityRecInput extends BaseUserInput {
    
    private String lang;
    private String returnType=InputValues.RETURNTYPE_STRING;
    public String getLang() {
        return lang;
    }
    
    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }
    
    
}
