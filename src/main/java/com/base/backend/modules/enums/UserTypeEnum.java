package com.base.backend.modules.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * <p>
 * 描述
 * </P>
 *
 * @author kamen
 * @date 2020/5/17
 */
public enum UserTypeEnum implements IEnum<Integer> {
    /**
     * 普通用户
     */
    NORMAL,
    /**
     * 管理员
     */
    ADMIN;

    @Override
    public Integer getValue() {
        return this.ordinal();
    }
}
