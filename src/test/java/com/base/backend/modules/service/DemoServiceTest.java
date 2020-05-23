package com.base.backend.modules.service;

import com.base.backend.modules.demo.entity.Demo;
import com.base.backend.modules.demo.service.IDemoService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.Assert.*;

public class DemoServiceTest extends BaseTest {

    @Autowired
    IDemoService demoService;

    @Test
    public void selectByIdTest() {
        Demo demo = demoService.selectById(1L);
        assertNotNull(demo);
        assertSame(1L, demo.getId());
    }

    @Test
    public void updateByIdTest() {
        Demo demo = new Demo();
        demo.setId(1L);
        demo.setCode("test");
        demoService.save(demo, 1L);
        Demo after = demoService.selectById(1L);
        assertEquals("test", after.getCode());
    }

    @Test
    public void selectOneTest() {
        Demo demo = new Demo();
        demo.setId(1L);
        Optional<Demo> after = demoService.selectOne(demo);
        assertTrue(after.isPresent());
        assertEquals("test", after.get().getCode());
    }

    @Test
    public void selectOneTest2() {
        Demo demo = new Demo();
        demo.setCode("code2");
        Optional<Demo> after = demoService.selectOne(demo);
        assertTrue(after.isPresent());
        assertSame(2L, after.get().getId());
    }

    @Test
    public void removeByIdTest() {
        demoService.removeById(1L);
    }
}
