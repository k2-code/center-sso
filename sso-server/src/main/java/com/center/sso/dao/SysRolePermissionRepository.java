package com.center.sso.dao;

import com.center.sso.model.po.SysRolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SysRolePermissionRepository  extends JpaRepository<SysRolePermission,Long> {
    List<SysRolePermission> findByPermissionId(Long permissionId);
}
