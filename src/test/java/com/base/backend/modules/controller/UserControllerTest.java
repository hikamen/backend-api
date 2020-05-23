package com.base.backend.modules.controller;

import com.base.backend.common.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * <p>
 * 描述
 * </P>
 *
 * @author kamen
 * @date 2020/5/17
 */
@SpringJUnitWebConfig
public class UserControllerTest {
    MockMvc mockMvc;
    String token = "eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJmNTAwYzAyMS0wZmU2LTQ1NGUtODAyOC1iMTRhMzBlNzVkMzciLCJzdWIiOiJhZG1pbiIsImlhdCI6MTU5MDE5MzIwNCwiZXhwIjoxNTkwMjc5NjA0fQ.6knE7gtHjiFXKqTxqBFCoONRvowwZTULcUEgYtA9csDc8toc-QSvHV7IRRj_18LJabZt4Mh00YnWtWEYTsr73Q";
    private String password = "ZWY3OTdjODExOGYwMmRmYjY0OTYwN2RkNWQzZjhjNzYyMzA0OGM5YzA2M2Q1MzJjYzk1YzVlZDdhODk4YTY0Zg=";

    @BeforeEach
    void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void loginTest() throws Exception {
        this.mockMvc.perform(post("/login").accept(MediaType.APPLICATION_JSON).param("username", "admin").param("password", password)).andExpect(status().isOk()).andExpect(jsonPath("$.code").value(0));
    }

    @Test
    void pageTest() throws Exception {
        this.mockMvc.perform(get("/api/user/page").header(Constants.AUTHORIZATION, token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.username").value("admin"));

    }
}
