package com.eastern.mqdemo.factory;

import com.eastern.mqdemo.config.ActiveMQProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.*;

@Slf4j
public class BaseFactory implements JmsAbstractFactory {

    @Override
    public ConnectionFactory createConnectionFactory(ActiveMQProperties properties) {
        return new ActiveMQConnectionFactory(properties.getUser(), properties.getPassword(), properties.getBrokerUrl());
    }

    @Override
    public Connection createConnection(ConnectionFactory connectionFactory) throws JMSException {
        return connectionFactory.createConnection();
    }

    @Override
    public Session createSession(Connection connection) throws JMSException {
        return connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    @Override
    public Destination createDestination(Session session, ActiveMQProperties properties) throws JMSException {
        return session.createQueue(properties.getQueue());
    }


    @Override
    public JmsTemplate createJmsTemplate(ConnectionFactory connectionFactory) {
        return new JmsTemplate(connectionFactory);
    }
}
