package com.eastern.mqdemo.factory;

import com.eastern.mqdemo.config.ActiveMQProperties;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.*;

public interface JmsAbstractFactory {
    ConnectionFactory createConnectionFactory(ActiveMQProperties properties);
    Connection createConnection(ConnectionFactory connectionFactory) throws JMSException;
    Session createSession(Connection connection) throws JMSException;
    Destination createDestination(Session session, ActiveMQProperties properties) throws JMSException;
    JmsTemplate createJmsTemplate(ConnectionFactory connectionFactory);

}
