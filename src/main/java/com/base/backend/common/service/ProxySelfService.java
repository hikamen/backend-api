package com.base.backend.common.service;


import com.base.backend.common.utils.SpringContextUtils;
import com.base.backend.utils.GenericsUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ClassUtils;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 用于在子类中实现获取自身代理对象的接口
 * <p>
 * 因为Spring AOP的限制，类内部方法相互调用的时候，会使得缓存等不走代理导致缓存失效，所以获取自身代理对象后，就不受限制。
 *
 * @author kamen
 * @param <T> 自身的类
 */
public interface ProxySelfService<T> {
    /**
     * 默认获取自身代理实例的方法
     *
     * @return 自身代理实例/自身实例
     */
    default T getInstance() {
        Type[] types=getClass().getGenericInterfaces();
        Class<T> entityClass = (Class) types[0];
        return SpringContextUtils.getBean(entityClass);
    }

    /**
     * 获取代理类
     * 当前未完成，后面再寻求使用JDK8的新热性来实现默认直接获取当前接口的代理对象。
     *
     * @return Class<T>
     */
    @SuppressWarnings("unchecked")
    default Class<T> getProxyServiceClass() {
        List<Class<?>> interfaceClassList = ClassUtils.getAllInterfaces(getClass());
        if (CollectionUtils.isNotEmpty(interfaceClassList)) {
            for (Class<?> clazz : interfaceClassList) {
                if (ClassUtils.isInnerClass(ProxySelfService.class)) {
                    return (Class<T>) GenericsUtils.getSuperClassGenricType(clazz);
                }
            }
        }
        return null;
    }

    /**
     * 默认获取自身代理实例的方法
     *
     * @param clazz    自身类
     * @param instance 当前自身实例
     * @return 自身代理实例/自身实例
     */
    default T getInstance(Class<T> clazz, T instance) {
        try {
            return SpringContextUtils.getBean(clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
    }
}
