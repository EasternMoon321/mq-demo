package com.eastern.mqdemo.config;

import lombok.Data;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author chensheng13
 * @Description TODO
 * @Date 2022/9/17 16:27
 * @Version 1.0
 */
@Configuration
@EnableConfigurationProperties(ActiveMqProperties.class)
public class ActiveMqConfig {
}
