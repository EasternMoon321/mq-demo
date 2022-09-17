package com.eastern.mqdemo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @Author chensheng13
 * @Description TODO
 * @Date 2022/9/17 16:27
 * @Version 1.0
 */
@Data
@ConfigurationProperties(prefix = "spring.activemq")
public class ActiveMqProperties {
    private String brokerUrl;
    private String user;
    private String password;
    private String queue;
}
