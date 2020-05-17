package com.base.backend.modules.controller;

import com.base.backend.common.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    String token = "eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiIzMmUxN2FjNS1mNDU4LTQ0NTAtODc5Yi0yZDk2N2I3NzE1YjYiLCJzdWIiOiJhZG1pbiIsImlhdCI6MTU4OTcwMDQ5MSwiZXhwIjoxNTg5Nzg2ODkxfQ.y4Aua7g9wtfuXH49oQQ_tfbRHcaVeZFkeRQIscxQIwzV0Q8iQB0A8kQM8aCrLbmf0d2BAtSB1EeAZLiUsC6exQ";

    @BeforeEach
    void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

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
