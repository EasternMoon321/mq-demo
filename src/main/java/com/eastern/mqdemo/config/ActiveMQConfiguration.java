package com.eastern.mqdemo.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;

@Configuration
public class ActiveMQConfiguration {
    @Resource
    private ActiveMQProperties properties;

    @Bean("normalTemplate")
    public JmsTemplate normalTemplate(@Qualifier("normalFactory") ConnectionFactory factory) {
        return new JmsTemplate(factory);
    }

    @Bean("asyTemplate")
    public JmsTemplate asyTemplate(@Qualifier("normalFactory") ConnectionFactory factory) {
        ActiveMQConnectionFactory activeMQConnectionFactory = (ActiveMQConnectionFactory) factory;
        activeMQConnectionFactory.setUseAsyncSend(true);
        return new JmsTemplate(activeMQConnectionFactory);
    }

    @Bean("normalFactory")
    public ConnectionFactory factory() {
        return new ActiveMQConnectionFactory(properties.getUser(), properties.getPassword(), properties.getBrokerUrl());
    }

    @Bean("redeliveryFactory")
    public ConnectionFactory redeliveryFactory(@Qualifier("redeliveryPolicy") RedeliveryPolicy redeliveryPolicy) {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(properties.getUser(), properties.getPassword(), properties.getBrokerUrl());
        factory.setRedeliveryPolicy(redeliveryPolicy);
        return factory;
    }

    @Bean("redeliveryPolicy")
    public RedeliveryPolicy redeliveryPolicy() {
        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
        //??????????????????????????????????????????,????????????????????????
        redeliveryPolicy.setUseExponentialBackOff(true);
        //????????????,?????????6???   ???????????????10???
        redeliveryPolicy.setMaximumRedeliveries(10);
        //??????????????????,?????????1???
        redeliveryPolicy.setInitialRedeliveryDelay(2);
        //??????????????????????????????????????????500??????,????????????????????????500 * 2??????,?????????2??????value
        redeliveryPolicy.setBackOffMultiplier(2);
        //????????????????????????
        redeliveryPolicy.setUseCollisionAvoidance(false);
        //??????????????????????????????-1 ????????????????????????UseExponentialBackOff(true)???true?????????
        redeliveryPolicy.setMaximumRedeliveryDelay(-1);
        return redeliveryPolicy;
    }

    @Bean("defaultJmsListenerContainerFactory")
    public DefaultJmsListenerContainerFactory normalFactory(@Qualifier("redeliveryFactory") ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setSessionTransacted(true);
        factory.setSessionAcknowledgeMode(ActiveMQSession.AUTO_ACKNOWLEDGE);
        return factory;
    }

    @Bean("normalQueue")
    public Queue queue() {
        return new ActiveMQQueue(properties.getQueue());
    }

    @Bean("exceptionQueue")
    public Queue exceptionQueue() {
        return new ActiveMQQueue(properties.getExceptionQueue());
    }

    @Bean("normalTopic")
    public Topic topic() {
        return new ActiveMQTopic(properties.getTopic());
    }
}
