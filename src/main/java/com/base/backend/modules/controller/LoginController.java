package com.base.backend.modules.controller;

import com.base.backend.common.controller.BaseController;
import com.base.backend.core.web.WebApiResponse;
import com.base.backend.modules.entity.User;
import com.base.backend.modules.service.IUserService;
import com.base.backend.utils.EncryptUtils;
import com.base.backend.utils.JwtUtils;
import com.base.backend.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author kamen
 */
@RestController
@RequestMapping("/")
public class LoginController extends BaseController {

    @Autowired
    private IUserService userService;

    @PostMapping(value = "/login")
    public WebApiResponse<?> login(String username, String password, HttpSession session) {
        Optional<User> user = userService.findByActiveUsername(username);
        if (StringUtils.isBlank(username) || !user.isPresent()) {
            return WebApiResponse.error("10001", "用户名或者密码不正确");
        } else {
            if (user.get().getPassword().equals(EncryptUtils.decodeBase64(password))) {
                String token = JwtUtils.getToken(username);
                user.get().setLastAccessTime(LocalDateTime.now());
                Long userId = super.getLoginUserId(session);
                userService.save(user.get(), userId);
                ApiData apiData = new ApiData();
                apiData.token = token;
                return WebApiResponse.success(apiData);
            } else {
                return WebApiResponse.error("10001", "用户名或者密码不正确");
            }
        }
    }


    @PostMapping(value = "/logout")
    public WebApiResponse<?> logout(String token) {
        logger.debug("token: " + token);
        return WebApiResponse.success();
    }

    public static class ApiData {
        public String token = "";
    }

}
