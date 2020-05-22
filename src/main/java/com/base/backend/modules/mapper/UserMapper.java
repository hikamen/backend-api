package com.base.backend.modules.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.base.backend.modules.entity.User;

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

    List<User> findPage(Map<String, String> params);

}
