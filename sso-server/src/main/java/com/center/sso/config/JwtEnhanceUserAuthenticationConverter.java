package com.center.sso.config;


import com.center.sso.model.SecurityUser;
import com.center.sso.model.TokenConstant;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;

/**
 * 从map中提提取用户信息
 */
public class JwtEnhanceUserAuthenticationConverter extends DefaultUserAuthenticationConverter {

    /**
     * 重写抽取用户数据方法
     */
    @Override
    public Authentication extractAuthentication(Map<String, ?> map) {
        if (map.containsKey(USERNAME)) {
            Collection<? extends GrantedAuthority> authorities = getAuthorities(map);
            String username = (String) map.get(USERNAME);
            Long userId = Long.valueOf(map.get(TokenConstant.USER_ID).toString());
            SecurityUser user =new SecurityUser(userId,username,"",authorities);
            return new UsernamePasswordAuthenticationToken(user, "", authorities);
        }
        return null;
    }

    /**
     * 提取权限
     * 现在无角色直接返回null
     */
    private Collection<? extends GrantedAuthority> getAuthorities(Map<String, ?> map) {
        Object authorities = map.get(AUTHORITIES);
        if (authorities instanceof String) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList((String) authorities);
        }
        if (authorities instanceof Collection) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils
                    .collectionToCommaDelimitedString((Collection<?>) authorities));
        }
        return null;
        //暂时不需要角色
        //throw new IllegalArgumentException("Authorities must be a String or a Collection");
    }

}