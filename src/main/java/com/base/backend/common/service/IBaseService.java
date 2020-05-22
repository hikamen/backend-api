package com.base.backend.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.base.backend.common.entity.BaseEntity;

/**
 * @author kamen
 */
public interface IBaseService<T extends BaseEntity> extends IService<T> {

    /**
     * 缓存键名用到版本号，如果实体结构变化，需要更新版本号
     */
    String version();

    void save(T t, Long userId);

    boolean removeById(Long id);

    T selectById(Long id);

    Long selectIdByOne(T entity);

    T selectOne(T entity);

    boolean isValidId(Long id);

    boolean isValidEntityId(BaseEntity idEntity);

    void cleanCache();
}
