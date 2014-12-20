/**
 * 
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package org.whuims.irlab.viper.rrs;

import java.util.List;

import org.junit.Test;
import org.whuims.irlab.viper.common.exception.AnalyzerException;
import org.whuims.irlab.viper.rrs.RrsSolver;
import org.whuims.irlab.viper.rrs.bean.RrRef;

/**
 * @title RrsSolverTest
 * @description TODO 
 * @author juyuan
 * @date 2014年10月24日
 * @version 1.0
 */
public class RrsSolverTest {
    @Test 
    public void testRrSolve() throws AnalyzerException{
         String context="百度云是个共享的好工具，但它有很多问题";
         RrsSolver solver=new RrsSolver();
         List<RrRef> refList=solver.solveReference(context);
         for(RrRef rf:refList){
             System.out.println(rf);
         }
     }
}
