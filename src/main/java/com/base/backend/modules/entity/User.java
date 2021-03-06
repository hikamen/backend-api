package com.base.backend.modules.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.base.backend.common.entity.BaseEntity;
import com.base.backend.modules.enums.GenderEnum;
import com.base.backend.modules.enums.UserTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author kamen
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_user")
@ToString(callSuper = true)
public class User extends BaseEntity {

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

    @TableField("gender")
    private GenderEnum gender;
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
     * 最近访问时间
     */
    @TableField("last_access_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastAccessTime;

    /**
     * 用户类型
     */
    @TableField("user_type")
    private UserTypeEnum userType;

}
