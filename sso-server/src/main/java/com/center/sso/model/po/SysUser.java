package com.center.sso.model.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户实体类
 */
@Data
@Entity
@Table(name = "sys_user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增主键
    private Long id;

    private String username;

    @JsonIgnore
    private String password;

    private String name;

    private String email;

    private String phone;

    private Boolean enabled;

    private String headImgUrl;

    private String sex;

    private String position;

    private Date createDate;

    private Date modifyDate;

    private Long ownerId;

    private Long modifyUserId;
}
