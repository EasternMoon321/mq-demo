package com.eastern.mqdemo.factory;

import com.eastern.mqdemo.config.ActiveMQProperties;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.stereotype.Service;

import javax.jms.*;


public class JmsMessage {
    private final ConnectionFactory connectionFactory;
    private final Connection connection;
    private final Session session;
    private final JmsTemplate jmsTemplate;
    private final Destination destination;


    public JmsMessage(JmsAbstractFactory jmsAbstractFactory, ActiveMQProperties properties) throws JMSException {
        connectionFactory = jmsAbstractFactory.createConnectionFactory(properties);
        connection = jmsAbstractFactory.createConnection(connectionFactory);
        session = jmsAbstractFactory.createSession(connection);
        destination = jmsAbstractFactory.createDestination(session, properties);
        jmsTemplate = jmsAbstractFactory.createJmsTemplate(connectionFactory);
    }

    public void sendMsg(String msg) {
        jmsTemplate.convertAndSend(destination, msg);
    }

    public void sendMsg(String msg, MessagePostProcessor postProcessor) {
        jmsTemplate.convertAndSend(destination, msg, postProcessor);
    }
}
