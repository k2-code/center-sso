package com.center.sso.model;

import lombok.Data;

/**
 * 保存登录用户的信息，可根据需要扩展
 */
@Data
public class LoginVal extends JwtInformation{

    private Long userId;

    private String username;

    private String[] authorities;
}
