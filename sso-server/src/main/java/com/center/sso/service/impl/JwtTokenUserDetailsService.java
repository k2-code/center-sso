package com.center.sso.service.impl;

import cn.hutool.core.util.ArrayUtil;
import com.center.sso.dao.SysRoleRepository;
import com.center.sso.dao.SysUserRepository;
import com.center.sso.dao.SysUserRoleRepository;
import com.center.sso.model.SecurityUser;
import com.center.sso.model.SysConstant;
import com.center.sso.model.po.SysUser;
import com.center.sso.model.po.SysUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * UserDetailsService的实现类，从数据库加载用户的信息，比如密码、角色。。。。
 */
@Service
@Primary
public class JwtTokenUserDetailsService implements UserDetailsService {

    @Autowired
    private SysUserRepository sysUserRepository;

    @Autowired
    private SysUserRoleRepository sysUserRoleRepository;

    @Autowired
    private SysRoleRepository sysRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = sysUserRepository.findByUsernameAndEnabled(username, true);
        if (Objects.isNull(user))
            throw new UsernameNotFoundException("用户不存在！");
        //角色
        List<SysUserRole> sysUserRoles = sysUserRoleRepository.findByUserId(user.getId());
        //该用户的所有权限（角色）
        List<String> roles=new ArrayList<>();
        for (SysUserRole userRole : sysUserRoles) {
            sysRoleRepository.findById(userRole.getRoleId()).ifPresent(o-> roles.add(SysConstant.ROLE_PREFIX+o.getCode()));
        }
        return SecurityUser.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                //将角色放入authorities中,现在无角色
                .authorities(AuthorityUtils.createAuthorityList(ArrayUtil.toArray(roles,String.class)))
                .build();
    }
}
