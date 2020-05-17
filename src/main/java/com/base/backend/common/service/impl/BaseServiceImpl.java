package com.base.backend.common.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.backend.common.entity.IdEntity;
import com.base.backend.common.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.time.LocalDateTime;

/**
 * @author kamen
 */
@Slf4j
public class BaseServiceImpl<M extends BaseMapper<T>, T extends IdEntity> extends ServiceImpl<M, T> implements BaseService<T> {

    @Override
    public void save(T entity, Long userId) {
        try {
            if (isValidEntityId(entity)) {
                FieldUtils.writeField(entity, "updatedBy", userId, true) ;
                FieldUtils.writeField(entity, "updatedTime", LocalDateTime.now(), true) ;
                this.updateById(entity);
            } else {
                FieldUtils.writeField(entity, "createdBy", userId, true) ;
                FieldUtils.writeField(entity, "createdTime", LocalDateTime.now(), true) ;
                this.updateById(entity);
                entity.setId(null);
                this.save(entity);
            }
        } catch (IllegalAccessException e) {
            log.info("Unable to set value to common fields like 'createdBy', 'updatedBy'");
        }
    }

    @Override
    public boolean isValidId(Long id) {
        return id != null && id > 0;
    }

    @Override
    public boolean isValidEntityId(IdEntity idEntity) {
        return idEntity != null && idEntity.getId() != null && idEntity.getId() > 0;
    }
}
