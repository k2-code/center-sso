package com.center.sso.dao;

import com.center.sso.model.po.SysPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysPermissionRepository extends JpaRepository<SysPermission,Long> {
}
