package com.center.sso.security.filter;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.center.sso.model.LoginVal;
import com.center.sso.model.RequestConstant;
import com.center.sso.model.TokenConstant;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Nonnull;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 解析网关传递的用户信息，将其放入request中，便于其他服务直接获取用户信息
 */
@Component
public class AuthenticationFilter extends OncePerRequestFilter {
    /**
     * 具体方法主要分为两步
     * 1. 解密网关传递的信息
     * 2. 将解密之后的信息封装放入到request中
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull FilterChain filterChain) throws ServletException, IOException {
        //获取请求头中的加密的用户信息
        String token = request.getHeader(TokenConstant.TOKEN_NAME);
        if (StrUtil.isNotBlank(token)) {
            //解密
            String json = Base64.decodeStr(token);
            JSONObject jsonObject = JSON.parseObject(json);
            //获取用户身份信息、权限信息
            String principal = jsonObject.getString(TokenConstant.PRINCIPAL_NAME);
            Long userId = Long.valueOf(jsonObject.getString(TokenConstant.USER_ID));
            String jti = jsonObject.getString(TokenConstant.JTI);
            Long expireIn = jsonObject.getLong(TokenConstant.EXPR);
            JSONArray tempJsonArray = jsonObject.getJSONArray(TokenConstant.AUTHORITIES_NAME);

            //权限
            String[] authorities = new String[0];
            if (null != tempJsonArray) {
                authorities = tempJsonArray.toArray(new String[0]);
            }

            LoginVal loginVal = new LoginVal();
            loginVal.setUserId(userId);
            loginVal.setUsername(principal);
            loginVal.setAuthorities(authorities);
            loginVal.setJti(jti);
            loginVal.setExpireIn(expireIn);
            //放入request的attribute中
            request.setAttribute(RequestConstant.LOGIN_VAL_ATTRIBUTE, loginVal);
        }
        filterChain.doFilter(request, response);
    }
}