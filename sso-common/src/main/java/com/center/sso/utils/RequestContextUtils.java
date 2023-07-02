package com.center.sso.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class RequestContextUtils {

    /**
     * 获取HttpServletRequest
     * @return TODO
     */
    public static HttpServletRequest getRequest(){
        return ((ServletRequestAttributes)(Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))).getRequest();
    }

}
