package com.base.backend.config;

import com.base.backend.core.weixin.config.WeiXinProperties;
import com.jarvis.cache.serializer.HessianSerializer;
import com.jarvis.cache.serializer.ISerializer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * 描述
 * </P>
 *
 * @author kamen
 * @date 2020/5/7
 */
@Configuration
@EnableConfigurationProperties({WeiXinProperties.class})
public class Config {

    @Bean
    public ISerializer<Object> autoloadCacheSerializer() {
        return new HessianSerializer();
    }
}
