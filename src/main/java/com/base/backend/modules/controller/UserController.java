package com.base.backend.modules.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.backend.common.controller.BaseController;
import com.base.backend.modules.entity.User;
import com.base.backend.modules.service.IUserService;
import com.base.backend.core.web.WebApiResponse;
import com.base.backend.core.web.WebRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author kamen
 */
@RestController
@RequestMapping("/api/user")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    @ModelAttribute("user")
    public User get(@PathVariable(required = false) Long id) {
        User user;
        if (id != null && id > 0) {
            user = userService.selectById(id);
        } else {
            user = new User();
        }
        return user;
    }

    @GetMapping("/page")
    public WebApiResponse<Page<User>> page(Page<User> page, WebRequestContext requestContext) {
        page = userService.findPage(page, requestContext.getParams());
        return WebApiResponse.success(page);
    }

    @PostMapping("/save/{id}")
    public WebApiResponse<User> save(User user, HttpSession session) {
        Long userId = super.getLoginUserId(session);
        userService.save(user, userId);
        return WebApiResponse.success(user);
    }

    @DeleteMapping("/delete/{id}")
    public WebApiResponse<Boolean> delete(@PathVariable Long id) {
        return WebApiResponse.success(userService.removeById(id));
    }

    @GetMapping("/exist/{id}")
    public WebApiResponse<Boolean> exist(User user, String value) {
        return WebApiResponse.success(userService.exist(user, value));
    }

}

