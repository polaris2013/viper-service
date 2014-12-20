/**
 * 
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package org.whuims.irlab.viper.opinion.utils;

import org.junit.Test;

import org.whuims.irlab.viper.opinion.extend.RrResolver;
import org.whuims.irlab.viper.opinion.utils.PositionUtils;

/**
 * @title PositionUtilsTest
 * @description TODO 
 * @author juyuan
 * @date 2014年8月31日
 * @version 1.0
 */
public class PositionUtilsTest {
    @Test
    public void testIsPart(){
        boolean flag=PositionUtils.isPart("他", RrResolver.RR_WORDSTR_ARRAY);
        System.out.println("is part: "+flag);
    }
    
}
