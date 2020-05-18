package com.base.backend.modules.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.backend.modules.entity.User;
import com.jarvis.cache.annotation.Cache;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author kamen
 */
public interface UserMapper extends BaseMapper<User> {

    @Cache(expire = 200, key = "'find-page-' + #hash(#args[0])", autoload = true)
    List<User> findPage(Page<User> page, Map<String, String> params);

}
