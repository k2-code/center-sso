package com.center.sso.model.vo;

import com.center.sso.model.po.SysRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SysRolePermissionVO {

    //权限ID
    private Long permissionId;

    //权限url
    private String url;

    //权限名称
    private String permissionName;

    private List<SysRole> roles;
}
