package com.center.sso.utils;


import com.center.sso.model.LoginVal;
import com.center.sso.model.RequestConstant;

/**
 * 从请求的线程中获取个人信息
 */
public class OauthUtil {

    /**
     * 获取当前请求登录用户的详细信息
     * @return TODO
     */
    public static LoginVal getCurrentUser(){
        return (LoginVal) RequestContextUtils.getRequest().getAttribute(RequestConstant.LOGIN_VAL_ATTRIBUTE);
    }
}
