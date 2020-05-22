package com.base.backend.modules.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.base.backend.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 演示表
 * </p>
 *
 * @author Kamen
 * @since 2020-05-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_demo")
public class Demo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 账号
     */
    @TableField("username")
    private String username;

    /**
     * 名称
     */
    @TableField("name")
    private String name;

    /**
     * 手机号码
     */
    @TableField("phone")
    private String phone;

    /**
     * 编码
     */
    @TableField("code")
    private String code;

    /**
     * 性别
     */
    @TableField("gender")
    private String gender;

    /**
     * 是否启用,1-启用;0-不启用
     */
    @TableField("active")
    private Integer active;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 用户类型，0：普通用户， 1：管理员
     */
    @TableField("user_type")
    private Integer userType;

    /**
     * 最近访问时间
     */
    @TableField("last_access_time")
    private LocalDateTime lastAccessTime;


}
