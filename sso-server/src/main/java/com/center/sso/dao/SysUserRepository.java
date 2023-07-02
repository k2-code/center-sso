package com.center.sso.dao;

import com.center.sso.model.po.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface SysUserRepository extends JpaRepository<SysUser,Long>, JpaSpecificationExecutor<SysUser> {

//    SysUser findByUsernameAndStatus(String username,Integer status);

    SysUser findByUsernameAndEnabled(String username,Boolean status);

    Optional<SysUser> findById(Long id);

    //SysUser findByMobileAndStatus(String mobile,Integer status);

    @Modifying
    @Query(value = "update sys_user set enabled = false where id in (?1)",nativeQuery = true)
    int disabledUsers(List<Long> userIds);

    int countByUsername(String username);


    @Query(value = "SELECT id,name,email,phone FROM sys_user WHERE id in (?1)",nativeQuery = true)
    List<SysUser> findUsersInBatch(List<Long> userIds);

    List<SysUser> findByIdIn(Long[] adIds);
}
