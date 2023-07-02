package com.center.sso.model;

import lombok.Data;

/**
 * JWT令牌相关的信息实体类
 */
@Data
public class JwtInformation {

    /**
     * JWT令牌唯一ID
     */
    private String jti;

    /**
     * 过期时间，单位秒，距离过期还有多少时间
     */
    private Long expireIn;
}
