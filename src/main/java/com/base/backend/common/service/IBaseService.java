package com.base.backend.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.base.backend.common.entity.BaseEntity;

import java.io.Serializable;

/**
 * @author kamen
 */
public interface IBaseService<T extends BaseEntity> extends IService<T> {

    /**
     * 缓存键名用到版本号，如果实体结构变化，需要更新版本号
     */
    String version();

    void save(T t, Long userId);

    T getById(Serializable id, boolean cacheable);

    T getOne(T entity, boolean cacheable);

    boolean isValidId(Long id);

    boolean isValidEntityId(BaseEntity idEntity);
}
