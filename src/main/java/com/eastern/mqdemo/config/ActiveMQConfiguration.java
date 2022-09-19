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
        //是否在每次尝试重新发送失败后,增长这个等待时间
        redeliveryPolicy.setUseExponentialBackOff(true);
        //重发次数,默认为6次   这里设置为10次
        redeliveryPolicy.setMaximumRedeliveries(10);
        //重发时间间隔,默认为1秒
        redeliveryPolicy.setInitialRedeliveryDelay(2);
        //第一次失败后重新发送之前等待500毫秒,第二次失败再等待500 * 2毫秒,这里的2就是value
        redeliveryPolicy.setBackOffMultiplier(2);
        //是否避免消息碰撞
        redeliveryPolicy.setUseCollisionAvoidance(false);
        //设置重发最大拖延时间-1 表示没有拖延只有UseExponentialBackOff(true)为true时生效
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
