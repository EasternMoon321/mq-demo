package com.eastern.mqdemo.factory;

import com.eastern.mqdemo.config.ActiveMQProperties;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

public class AsyFactory extends BaseFactory {

    @Override
    public ConnectionFactory createConnectionFactory(ActiveMQProperties properties) {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(properties.getUser(), properties.getPassword(), properties.getBrokerUrl());
        factory.setUseAsyncSend(true);
        return factory;
    }
}
