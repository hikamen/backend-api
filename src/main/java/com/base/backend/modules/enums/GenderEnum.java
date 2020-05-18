package com.base.backend.modules.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * <p>
 * 描述
 * </P>
 *
 * @author kamen
 * @date 2020/5/17
 */
public enum GenderEnum {
    MALE("M", "男"),
    FEMALE("F", "女"),
    UNKNOWN("U", "未知") ;

    @EnumValue
    String value;
    String text;

    GenderEnum(String value, String text) {
        this.value = value;
        this.text = text;
    }
}
