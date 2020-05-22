package com.base.backend.modules.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceTest extends BaseTest {

    @Autowired
    IUserService userService;

    @Test
    public void selectByIdTest() {
        System.out.println(userService.selectById(1L));;
    }
}
