package com.base.backend.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.backend.common.entity.BaseEntity;
import com.base.backend.common.service.IBaseService;
import com.base.backend.common.service.ProxySelfService;
import lombok.extern.slf4j.Slf4j;
import net.oschina.j2cache.CacheChannel;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.time.LocalDateTime;

/**
 * @author kamen
 */
@Slf4j
@CacheConfig(cacheNames = "default")
public abstract class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseEntity>
        extends ServiceImpl<M, T> implements IBaseService<T>, ProxySelfService<IBaseService<T>> {
    @Autowired
    protected CacheChannel cacheChannel;

    @Override
    public String version() {
        return "1.0.0";
    }

    @Override
    @CacheEvict(keyGenerator = "cacheKeyGenerator", condition = "#entity.id > 0")
    public void save(T entity, Long userId) {
        try {
            if (isValidEntityId(entity)) {
                FieldUtils.writeField(entity, "updatedBy", userId, true);
                FieldUtils.writeField(entity, "updatedTime", LocalDateTime.now(), true);
                this.updateById(entity);
            } else {
                FieldUtils.writeField(entity, "createdBy", userId, true);
                FieldUtils.writeField(entity, "createdTime", LocalDateTime.now(), true);
                entity.setId(null);
                this.save(entity);
            }
            getInstance().cleanCache();
        } catch (IllegalAccessException e) {
            log.info("Unable to set value to common fields like 'createdBy', 'updatedBy'");
        }
    }

    @Override
    @CacheEvict(keyGenerator = "cacheKeyGenerator", condition = "#id > 0")
    public boolean removeById(Long id) {
        if (id != null && id > 0) {
            return super.removeById(id);
        }
        return false;
    }

    @Override
    @Cacheable(keyGenerator = "cacheKeyGenerator")
    public T selectById(Long id) {
        return this.getById(id);
    }

    @Override
    @Cacheable(keyGenerator = "cacheKeyGenerator")
    public Long selectIdByOne(T entity) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>(entity, "id");
        T t = this.getOne(queryWrapper);
        if (t != null) {
            return t.getId();
        }
        return null;
    }

    @Override
    public T selectOne(T entity) {
        Long id = getInstance().selectIdByOne(entity);
        if (id != null && id > 0) {
            return getInstance().selectById(id);
        }
        return null;
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
