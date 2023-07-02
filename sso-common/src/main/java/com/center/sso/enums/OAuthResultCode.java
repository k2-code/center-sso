package com.center.sso.enums;

public enum OAuthResultCode {

    CLIENT_AUTHENTICATION_FAILED(401,"客户端认证失败"),

    USERNAME_OR_PASSWORD_ERROR(401,"用户名或密码错误"),

    UNSUPPORTED_GRANT_TYPE(401, "不支持的认证模式"),

    INVALID_TOKEN(401,"无效的token"),

    NO_PERMISSION(401,"无权限访问！"),

    UNAUTHORIZED(401, "系统错误");





    private final int code;

    private final String msg;

    private OAuthResultCode(int code,String msg){
        this.code=code;
        this.msg=msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
