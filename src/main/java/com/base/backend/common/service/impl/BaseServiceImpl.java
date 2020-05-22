package com.base.backend.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.backend.common.entity.BaseEntity;
import com.base.backend.common.service.IBaseService;
import lombok.extern.slf4j.Slf4j;
import net.oschina.j2cache.CacheChannel;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author kamen
 */
@Slf4j
@CacheConfig(cacheNames = "default")
public class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseEntity> extends ServiceImpl<M, T> implements IBaseService<T> {
    @Autowired
    private CacheChannel cacheChannel;

    @Override
    public String version() {
        return "1.0.0";
    }

    @Override
    public void save(T entity, Long userId) {
        try {
            if (isValidEntityId(entity)) {
                FieldUtils.writeField(entity, "updatedBy", userId, true);
                FieldUtils.writeField(entity, "updatedTime", LocalDateTime.now(), true);
                this.updateById(entity);
            } else {
                FieldUtils.writeField(entity, "createdBy", userId, true);
                FieldUtils.writeField(entity, "createdTime", LocalDateTime.now(), true);
                this.updateById(entity);
                entity.setId(null);
                this.save(entity);
            }
        } catch (IllegalAccessException e) {
            log.info("Unable to set value to common fields like 'createdBy', 'updatedBy'");
        }
    }

    @Override
    @Cacheable(keyGenerator = "cacheKeyGenerator", condition = "#cacheable == true")
    public T getById(Serializable id, boolean cacheable) {
        return super.getById(id);
    }

    @Override
    @Cacheable(keyGenerator = "cacheKeyGenerator", condition = "#cacheable == true")
    public T getOne(T entity, boolean cacheable) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>(entity);
        return super.getOne(queryWrapper);
    }

    @Override
    public boolean isValidId(Long id) {
        return id != null && id > 0;
    }

    @Override
    public boolean isValidEntityId(BaseEntity idEntity) {
        return idEntity != null && idEntity.getId() != null && idEntity.getId() > 0;
    }
}
