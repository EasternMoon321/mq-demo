package com.eastern.mqdemo.service.queue;

import com.eastern.mqdemo.config.ActiveMQProperties;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.*;

@Slf4j
@Service
public class NormalConsumer implements CommandLineRunner {

    @Resource
    private ActiveMQProperties properties;

    @Override
    public void run(String... args) throws Exception {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(properties.getUser(), properties.getPassword(), properties.getBrokerUrl());
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = new ActiveMQQueue(properties.getQueue());
        MessageConsumer consumer = session.createConsumer(queue);
        consumer.setMessageListener((message) -> {
            TextMessage textMessage = (TextMessage) message;
            try {
                log.info("{} receive message: {}", this.getClass().getName(), textMessage.getText());
            } catch (JMSException e) {
                log.error("exception message: ", e);
            }
        });
    }
}
