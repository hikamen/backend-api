package com.base.backend.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.backend.common.entity.BaseEntity;
import com.base.backend.common.service.IBaseService;
import com.base.backend.common.service.ProxySelfService;
import com.base.backend.utils.EncryptUtils;
import com.base.backend.utils.GenericsUtils;
import lombok.extern.slf4j.Slf4j;
import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.CacheObject;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author kamen
 */
@Slf4j
public abstract class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseEntity>
        extends ServiceImpl<M, T> implements IBaseService<T>, ProxySelfService<IBaseService<T>> {
    public static final String DEFAULT_REGION = "default";
    @Autowired
    protected CacheChannel cacheChannel;
    /**
     * 注入J2Cache生成的RedisTemplate，可进行redis多种数据结构的操作
     */
    @Autowired
    protected RedisTemplate<String, Serializable> j2CacheRedisTemplate;

    @Override
    public String version() {
        return "1.0.0";
    }

    protected String getCacheKey(Serializable key) {
        return GenericsUtils.getSuperClassGenricType(this.getClass(), 1).getSimpleName() + ":" + version() + ":" + key;
    }

    @Override
    public void save(T entity, Long userId) {
        try {
            if (isValidEntityId(entity)) {
                FieldUtils.writeField(entity, "updatedBy", userId, true);
                FieldUtils.writeField(entity, "updatedTime", LocalDateTime.now(), true);
                this.updateById(entity);
                cacheChannel.evict(DEFAULT_REGION, getCacheKey(entity.getId()));
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
    public boolean removeById(Long id) {
        if (id != null && id > 0) {
            cacheChannel.evict(DEFAULT_REGION, getCacheKey(id));
            return super.removeById(id);
        }
        return false;
    }

    @Override
    public T selectById(Long id) {
        CacheObject cacheObject = cacheChannel.get(DEFAULT_REGION, getCacheKey(id));
        if (cacheObject.getValue() != null) {
            return (T) cacheObject.getValue();
        }
        T t = this.getById(id);
        if (t != null) {
            cacheChannel.set(DEFAULT_REGION, getCacheKey(id), t);
        }
        return t;
    }

    @Override
    public Optional<Long> selectIdByOne(T entity) {
        String key = getCacheKey(EncryptUtils.md5(entity.toString()));
        CacheObject cacheObject = cacheChannel.get(DEFAULT_REGION, key);
        if (cacheObject.getValue() != null) {
            return Optional.of((Long) cacheObject.getValue());
        }
        QueryWrapper<T> queryWrapper = new QueryWrapper<>(entity, "id");
        T t = this.getOne(queryWrapper);
        if (t != null) {
            cacheChannel.set(DEFAULT_REGION, key, t.getId());
            return Optional.of(t.getId());
        }
        return Optional.empty();
    }

    @Override
    public Optional<T> selectOne(T entity) {
        Optional<Long> id = this.selectIdByOne(entity);
        return id.map(this::selectById);
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
