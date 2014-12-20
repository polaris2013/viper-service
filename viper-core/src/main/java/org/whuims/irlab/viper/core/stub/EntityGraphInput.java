/**
 * 
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package org.whuims.irlab.viper.core.stub;

import org.whuims.irlab.viper.core.InputValues;

/**
 * @title EntityGraphInput
 * @description TODO 
 * @author juyuan
 * @date 2014年11月3日
 * @version 1.0
 */
public class EntityGraphInput extends BaseUserInput {
     private String lang=InputValues.LANG_CHINESE;

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
     
}
