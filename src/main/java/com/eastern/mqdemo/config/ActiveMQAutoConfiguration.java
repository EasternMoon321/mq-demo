package com.eastern.mqdemo.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

@Configuration
@EnableConfigurationProperties(ActiveMQProperties.class)
public class ActiveMQAutoConfiguration {


}
