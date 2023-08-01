package com.center.sso.config;

import com.center.sso.utils.RSACoderHelper;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * 自定义授权模式，client_pwd,
 */
public class JwtResourceOwnerPasswordToken extends AbstractTokenGranter {

    //RSA私钥
    private static final byte[] pkArr = Base64.decodeBase64("MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEAiUxN23+b/7DRbckTMlicXY3ISGgZAQCxXEpCuy1cK97I6qDBLK+eghz5nSt+YBxJNR8URksmpCYsdWa6+ocwbwIDAQABAkBi8pHd0b6cbFLSeyoRi5jNN4QS4qq6dURdDcu/17XoiuRUupSLmNG95yRY7jRfN1NuR1yKRIRoPHjSDtiu1jWpAiEA8ewMEf6U6d/VEtfb6J6svs6t6wIhkP+cBjEff2SWEXsCIQCRSafyMxP7ld6K+A0vr6X6MBhaSy/Jd1/Aqw6rVcXonQIhALRj8swoLRoHUXZvhwb56o2Mx5qJSEY6kzj6wCXZ9xypAiB83RSdrxBJdHAidzS9+vNmpdcIIv4a46FDcL/WuIyycQIhAN8FWGeKRoche5roZm1e+Gekvz8P1tzKGs65ibALwcPV");

    private static final String GRANT_TYPE = "client_pwd";

    private final AuthenticationManager authenticationManager;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    public JwtResourceOwnerPasswordToken(AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices,
                                         ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
        super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Map<String, String> parameters = new LinkedHashMap<String, String>(tokenRequest.getRequestParameters());
        String username = parameters.get("username");
        String encryptPwd = parameters.get("password");
        String password;
        try {
            password = decodePassword(encryptPwd);
        } catch (Exception e) {
            throw new InvalidGrantException(e.getMessage());
        }
        if (null == password) {
            throw new InvalidGrantException("服务器异常，密码解析失败");
        }
        // Protect from downstream leaks of password
        parameters.remove("password");
//        password = passwordEncoder.encode(password);
        Authentication userAuth = new UsernamePasswordAuthenticationToken(username, password);
        ((AbstractAuthenticationToken) userAuth).setDetails(parameters);
        try {
            userAuth = authenticationManager.authenticate(userAuth);
        } catch (AccountStatusException ase) {
            //covers expired, locked, disabled cases (mentioned in section 5.2, draft 31)
            throw new InvalidGrantException(ase.getMessage());
        } catch (BadCredentialsException e) {
            // If the username/password are wrong the spec says we should send 400/invalid grant
            throw new InvalidGrantException(e.getMessage());
        }
        if (userAuth == null || !userAuth.isAuthenticated()) {
            throw new InvalidGrantException("Could not authenticate user: " + username);
        }

        OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(storedOAuth2Request, userAuth);
    }

    /**
     * 密码解码
     * @param decodePassword
     * @return
     * @throws Exception
     */
    public static String decodePassword(String decodePassword) throws Exception {
        byte[] pwdArr = Base64.decodeBase64(decodePassword);
        byte[] pwdDec = RSACoderHelper.decryptByPrivateKey(pwdArr, pkArr);
        return new String(pwdDec);

    }
}
