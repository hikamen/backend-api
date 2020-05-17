package com.base.backend.modules.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.backend.common.service.impl.BaseServiceImpl;
import com.base.backend.modules.entity.User;
import com.base.backend.modules.mapper.UserMapper;
import com.base.backend.modules.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author kamen
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public Optional<User> findByActiveUsername(String username) {
        User param = new User();
        param.setUsername(username);
        param.setActive(1);
        return Optional.of(this.getOne(new QueryWrapper<>(param)));
    }

    private Optional<User> findByUsername(String username) {
        if (StringUtils.isNotBlank(username)) {
            User param = new User();
            param.setUsername(username);
            return Optional.of(this.getOne(new QueryWrapper<>(param)));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Page<User> selectPage(Page<User> page, Map<String, String> params) {
        page.setRecords(this.baseMapper.findPage(page, params));
        return page;
    }

    @Override
    public boolean exist(User user, String value) {
        boolean isExist = false;
        if (isValidEntityId(user)) {
            Optional<User> userDb = findByUsername(value);
            if (userDb.isPresent() && !user.getId().equals(userDb.get().getId())) {
                isExist = true;
            }
        } else {
            Optional<User> userDb = findByUsername(value);
            if (userDb.isPresent()) {
                isExist = true;
            }
        }
        return isExist;
    }
}
