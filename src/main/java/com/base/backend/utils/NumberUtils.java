package com.base.backend.utils;

/**
 * 数字相关工具类
 *
 */
public class NumberUtils {
    /**
     * 如果数字对象为空，则赋值为默认数值，用于防止空指针保存
     *
     * @param number        数字对象，可以是Integer、Float、Double等继承于Number的数字对象
     * @param defaultNumber 默认数值
     * @param <T>           对象类型（泛型）
     * @return 不会报空指针错的数字对象
     */
    public static <T extends Number> T getValue(T number, T defaultNumber) {
        return number == null ? defaultNumber : number;
    }

    //========================================================================
    // 浮点数四舍五入 开始
    //========================================================================

    /**
     * 默认保留两位小数
     */
    public static Float floatRounding(Float number) {
        return floatRounding(number, 2);
    }

    /**
     * 浮点数四舍五入
     *
     * @param number  四舍五入对象
     * @param keepNum 保留位数
     * @return 四舍五入结果
     */
    public static Float floatRounding(Float number, int keepNum) {
        float dig = (float) Math.pow(10, keepNum);
        return Math.round(number * dig) / dig;
    }
    //========================================================================
    // 浮点数四舍五入 结束
    //========================================================================
}
