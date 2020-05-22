package com.base.backend.core.cache;

import com.base.backend.common.service.IBaseService;
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
    @Override
    public Object generate(Object target, Method method, Object... params) {
        String entityName = GenericsUtils.getSuperClassGenricType(target.getClass(), 1).getSimpleName();
        String version = "";
        if (target instanceof IBaseService) {
            version = ((IBaseService) target).version();
        }
        String key = entityName + ":" + version + ":" + method.getName();
        log.info("generate key: {}", key);
        return key;
    }
}
