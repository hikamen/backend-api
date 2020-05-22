package com.base.backend.modules.demo.service.impl;

import com.base.backend.modules.demo.entity.Demo;
import com.base.backend.modules.demo.mapper.DemoMapper;
import com.base.backend.modules.demo.service.IDemoService;
import com.base.backend.common.service.impl.BaseServiceImpl;
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
public class DemoServiceImpl extends BaseServiceImpl<DemoMapper, Demo> implements IDemoService {

}
