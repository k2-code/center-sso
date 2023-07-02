package com.center.sso.model;

public class TokenConstant {

    /**
     * JWT的秘钥
     * 配置到配置文件中，资源服务也可能用到
     */
    public final static String SIGN_KEY="myjszl";

    public final static String TOKEN_NAME="jwt-token";

    public final static String PRINCIPAL_NAME="principal";

    public static final String AUTHORITIES_NAME="authorities";

    public static final String USER_ID="userId";

    public static final String JTI="jti";

    public static final String EXPR="expr";
}
