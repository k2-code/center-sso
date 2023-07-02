package com.center.sso.exception;

import com.center.sso.enums.OAuthResultCode;
import com.philisense.utils.ResultResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.UnsupportedGrantTypeException;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;

/**
 * 自定义异常翻译器，针对用户名、密码异常，授权类型不支持的异常进行处理
 */
@Component
public class OAuthServerWebResponseExceptionTranslator implements WebResponseExceptionTranslator{
    /**
     * 业务处理方法，重写这个方法返回客户端信息
     */
    @Override
    public ResponseEntity<ResultResponse> translate(Exception e){
        ResultResponse resultMsg = doTranslateHandler(e);
        return new ResponseEntity<>(resultMsg, HttpStatus.UNAUTHORIZED);
    }

    /**
     * 根据异常定制返回信息
     */
    private ResultResponse doTranslateHandler(Exception e) {
        //初始值，系统错误，
        OAuthResultCode resultCode = OAuthResultCode.UNAUTHORIZED;
        //判断异常，不支持的认证方式
        if(e instanceof UnsupportedGrantTypeException){
            resultCode = OAuthResultCode.UNSUPPORTED_GRANT_TYPE;
            //用户名或密码异常
        }else if(e instanceof InvalidGrantException){
            resultCode = OAuthResultCode.USERNAME_OR_PASSWORD_ERROR;
        }else if (e instanceof InvalidTokenException){
            resultCode = OAuthResultCode.INVALID_TOKEN;
        }
        return new ResultResponse(resultCode.getCode(),resultCode.getMsg(),null);
    }
}
