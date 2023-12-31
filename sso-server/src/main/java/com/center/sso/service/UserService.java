package com.center.sso.service;

import com.center.sso.model.po.SysUser;
import com.center.sso.phili.utils.PageParam;
import com.center.sso.phili.utils.PageResponse;

import java.util.List;

public interface UserService {

    void save(SysUser user);

    PageResponse<SysUser> userPageQuery(SysUser user, PageParam pageParam);

    int disabledUsers(List<Long> userIds);

    SysUser getUserDetail(Long userId);

    Boolean checkUserName(String username);

    List<SysUser> getUsersInBatch(List<Long> userIds);

}
