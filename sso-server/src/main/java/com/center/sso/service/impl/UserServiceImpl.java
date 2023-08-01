package com.center.sso.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.center.sso.config.JwtResourceOwnerPasswordToken;
import com.center.sso.dao.SysUserRepository;
import com.center.sso.model.LoginVal;
import com.center.sso.model.po.SysUser;
import com.center.sso.phili.utils.BaseException;
import com.center.sso.phili.utils.PageParam;
import com.center.sso.phili.utils.PageResponse;
import com.center.sso.service.UserService;
import com.center.sso.utils.OauthUtil;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private SysUserRepository sysUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void save(SysUser user) {
        try {
            String s = JwtResourceOwnerPasswordToken.decodePassword(user.getPassword());
            user.setPassword(passwordEncoder.encode(s));
        }catch (Exception e){
            throw new BaseException("服务器错误");
        }
        Date date = new Date();
        user.setEnabled(true);
        user.setModifyDate(date);
        user.setCreateDate(date);

        LoginVal currentUser = OauthUtil.getCurrentUser();
        if (null != currentUser){
            user.setOwnerId(currentUser.getUserId());
            user.setModifyUserId(currentUser.getUserId());
        }

        sysUserRepository.save(user);
    }

    @Override
    public PageResponse<SysUser> userPageQuery(SysUser user, PageParam pageParam) {

        Sort.Order modify_date = new Sort.Order(Sort.Direction.ASC, "modifyDate");
        Sort sort = Sort.by(modify_date);
        PageRequest pageRequest = PageRequest.of(pageParam.getPageIndex() - 1, pageParam.getPageSize(), sort);

        Specification<SysUser> specification = new Specification<SysUser>() {
            @Override
            public Predicate toPredicate(Root<SysUser> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (!StringUtils.isBlank(user.getUsername())) {
                    Path<SysUser> username = root.get("username");
                    Predicate predicate = criteriaBuilder.like(username.as(String.class), "%" + user.getUsername() + "%");
                    predicates.add(predicate);
                }
                if (null != user.getEnabled()) {
                    Path<SysUser> enabled = root.get("enabled");
                    Predicate equal = criteriaBuilder.equal(enabled, user.getEnabled());
                    predicates.add(equal);
                }
                Predicate[] predicate = new Predicate[predicates.size()];
                return criteriaBuilder.and(predicates.toArray(predicate));
            }
        };
        Page<SysUser> all = sysUserRepository.findAll(specification, pageRequest);
        return new PageResponse<>(all);
    }

    @Override
    public int disabledUsers(List<Long> userIds) {
        if (CollectionUtil.isEmpty(userIds)){
            return 0;
        }
       return sysUserRepository.disabledUsers(userIds);

    }

    @Override
    public SysUser getUserDetail(Long userId) {
        Optional<SysUser> byId = sysUserRepository.findById(userId);
        return byId.get();
    }

    @Override
    public Boolean checkUserName(String username) {
        int i = sysUserRepository.countByUsername(username);
        return i > 0;
    }

    @Override
    public List<SysUser> getUsersInBatch(List<Long> userIds){
         if(null==userIds ||0==userIds.size()){
             return new ArrayList<>(0);
         }
        return sysUserRepository.findByIdIn(userIds.toArray(new Long[userIds.size()]));
    }
}
