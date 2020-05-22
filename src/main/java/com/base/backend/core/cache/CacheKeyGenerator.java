package com.base.backend.core.cache;

import com.base.backend.common.entity.BaseEntity;
import com.base.backend.common.service.IBaseService;
import com.base.backend.utils.EncryptUtils;
import com.base.backend.utils.GenericsUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * <p>
 * 缓存主键生成器
 * </P>
 *
 * @author kamen
 * @date 2020/5/22
 */
@Component("cacheKeyGenerator")
@Slf4j
public class CacheKeyGenerator implements KeyGenerator {
    public static final String SAVE_METHOD = "save";
    public static final String REMOVE_METHOD = "removeById";
    public static final String SELECT_BY_ID_METHOD = "selectById";

    @Override
    public Object generate(Object target, Method method, Object... params) {
        String entityName = GenericsUtils.getSuperClassGenricType(target.getClass(), 1).getSimpleName();
        String version = "";
        if (target instanceof IBaseService) {
            version = ((IBaseService) target).version();
        }
        String key = entityName + ":" + version + ":";
        if (SAVE_METHOD.equals(method.getName()) || REMOVE_METHOD.equals(method.getName())) {
            key += SELECT_BY_ID_METHOD + ":";
            if (Long.class.isAssignableFrom(params[0].getClass())) {
                key += params[0];
            } else if (BaseEntity.class.isAssignableFrom(params[0].getClass())) {
                BaseEntity entity = (BaseEntity) params[0];
                key += entity.getId();
            }
        } else {
            String prefix = "";
            if (params.length == 1) {
                if (Long.class.isAssignableFrom(params[0].getClass())) {
                    prefix = String.valueOf(params[0]);
                } else if (BaseEntity.class.isAssignableFrom(params[0].getClass())) {
                    log.info("BaseEntity.toString: {}", params[0]);
                    prefix = EncryptUtils.md5(String.valueOf(params[0]));
                }
            }
            key += method.getName();
            if (!"".equals(prefix)) {
                key += ":" + prefix;
            }
        }
        log.info("generate key: {}", key);
        return key;
    }
}
