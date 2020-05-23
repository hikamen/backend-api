package com.base.backend.modules.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.backend.modules.entity.User;
import org.apache.ibatis.annotations.Param;

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

    List<Long> findPage(Page<User> page, @Param("p")Map<String, String> params);

}
