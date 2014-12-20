/**
 * 
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package org.whuims.irlab.viper.common.exception;

/**
 * @title AnalyzerException
 * @description TODO 
 * @author juyuan
 * @date 2014年10月31日
 * @version 1.0
 */
public class AnalyzerException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private int errCode=0;

    public  AnalyzerException(String message){
        super(message);
    }
    
    public AnalyzerException(String message,Exception ex){
        super(message,ex);
    }
    
    
    public int getErrCode() {
        return errCode;
    }
    
     
  
    
    

}
