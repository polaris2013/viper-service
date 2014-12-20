/**
 * 
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package org.whuims.irlab.viper.web.services.result;

/**
 * @title ErrMsg
 * @description TODO 
 * @author juyuan
 * @date 2014年10月31日
 * @version 1.0
 */
public class ResultMsg {
    private boolean success=Boolean.TRUE;
    private Object result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

   

}
