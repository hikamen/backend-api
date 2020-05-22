package com.base.backend.modules.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.backend.common.service.IBaseService;
import com.base.backend.modules.entity.User;

import java.util.Map;
import java.util.Optional;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author kamen
 */
public interface IUserService extends IBaseService<User> {

    Optional<User> findByActiveUsername(String username);

    Page<User> findPage(Page<User> page, Map<String, String> params);

    boolean exist(User user, String value);
}
