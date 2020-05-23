package com.base.backend.modules.demo.service.impl;

import com.base.backend.common.service.impl.BaseServiceImpl;
import com.base.backend.modules.demo.entity.Demo;
import com.base.backend.modules.demo.mapper.DemoMapper;
import com.base.backend.modules.demo.service.IDemoService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 演示表 服务实现类
 * </p>
 *
 * @author Kamen
 * @since 2020-05-22
 */
@Service
@CacheConfig(cacheNames = "demos")
public class DemoServiceImpl extends BaseServiceImpl<DemoMapper, Demo> implements IDemoService {

    @Override
    public void cleanCache() {

    }
}
