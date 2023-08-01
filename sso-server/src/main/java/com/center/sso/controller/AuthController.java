package com.center.sso.controller;

import com.alibaba.fastjson.JSONObject;
import com.center.sso.enums.OAuthResultCode;
import com.center.sso.exception.OAuthServerWebResponseExceptionTranslator;
import com.center.sso.model.LoginVal;
import com.center.sso.model.SysConstant;
import com.center.sso.phili.utils.ResultResponse;
import com.center.sso.utils.OauthUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/oauth")
@Slf4j
public class AuthController {

    @Autowired
    private TokenEndpoint tokenEndpoint;

//    @Autowired
//    private CheckTokenEndpoint checkTokenEndpoint;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private OAuthServerWebResponseExceptionTranslator translator;


    @GetMapping("/logout")
    public ResultResponse logout() {
        LoginVal loginVal = OauthUtil.getCurrentUser();
        log.info("令牌唯一ID：{},过期时间：{}", loginVal.getJti(), loginVal.getExpireIn());
        //这个jti放入redis中，并且过期时间设置为token的过期时间
        stringRedisTemplate.opsForValue().set(SysConstant.JTI_KEY_PREFIX + loginVal.getJti(), "", loginVal.getExpireIn(), TimeUnit.SECONDS);
        return new ResultResponse(0, "注销成功", null);
    }

    /**
     * 重写 /oauth/token,对返回结果自定义
     *
     * @param principal
     * @param parameters
     * @return
     */
    @PostMapping("/token")
    public ResultResponse getToken(HttpServletResponse httpServletResponse, Principal principal, @RequestParam
            Map<String, String> parameters) {
        OAuth2AccessToken body = null;
        try {
            body = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        } catch (Exception e) {
            log.error("exception:{}", e.getMessage(), e);
            return translator.translate(e).getBody();
        }
        if (null == body) {
            return new ResultResponse(OAuthResultCode.UNAUTHORIZED.getCode(),
                    OAuthResultCode.UNAUTHORIZED.getMsg());

        }
        JSONObject token = new JSONObject();
        token.put("access_token", body.getValue());
        if (!parameters.get("grant_type").equals("refresh_token")) {
            token.put("refresh_token", body.getRefreshToken().getValue());
        }
        token.put("token_type", body.getTokenType());
        token.put("userId", body.getAdditionalInformation().get("userId"));
        token.put("jti", body.getAdditionalInformation().get("jti"));
        return new ResultResponse(token);
    }


//    @GetMapping("/check_token/{token}")
//    public ResultResponse checkToken(@PathVariable String token){
//        Map<String, ?> stringMap = checkTokenEndpoint.checkToken(token);
//        JSONObject checkRes = new JSONObject();
//        checkRes.put("username",stringMap.get("user_name"));
//        checkRes.put("userId",stringMap.get("userId"));
//        checkRes.put("active",stringMap.get("active"));
//        checkRes.put("exp",stringMap.get("exp"));
//        checkRes.put("scope",stringMap.get("scope"));
//        checkRes.put("client_id",stringMap.get("client_id"));
//        checkRes.put("jti",stringMap.get("jti"));
//        return new ResultResponse(checkRes);
//    }

}
