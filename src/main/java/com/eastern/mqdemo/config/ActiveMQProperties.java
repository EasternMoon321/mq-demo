package com.eastern.mqdemo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "spring.activemq")
public class ActiveMQProperties {

    /**
     * URL of the ActiveMQ broker. Auto-generated by default.
     */
    private String brokerUrl;


    /**
     * Login user of the broker.
     */
    private String user;

    private String password;

    private String queue;

    private String exceptionQueue;

    private String topic;

}
