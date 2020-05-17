package com.base.backend.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.base.backend.common.entity.IdEntity;

/**
 * @author kamen
 */
public interface BaseService<T extends IdEntity> extends IService<T> {

    void save(T t, Long userId);

    boolean isValidId(Long id);

    boolean isValidEntityId(IdEntity idEntity);
}
