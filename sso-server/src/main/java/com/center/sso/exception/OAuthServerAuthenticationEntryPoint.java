package com.center.sso.exception;

import com.center.sso.enums.OAuthResultCode;
import com.center.sso.utils.ResponseUtils;
import com.center.sso.phili.utils.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用于处理客户端认证出错，包括客户端id、密码错误
 */
@Component
@Slf4j
public class OAuthServerAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * 认证失败后调用这个方法返回提示信息
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(OAuthResultCode.CLIENT_AUTHENTICATION_FAILED.getCode());
        ResponseUtils.result(response,new ResultResponse(OAuthResultCode.CLIENT_AUTHENTICATION_FAILED.getCode(),OAuthResultCode.CLIENT_AUTHENTICATION_FAILED.getMsg(),null));
    }
}