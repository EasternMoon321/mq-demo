package com.eastern.mqdemo.service.queue;

import com.eastern.mqdemo.config.ActiveMQProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.*;

@Slf4j
@Service
public class ExceptionConsumer {

    @JmsListener(destination = "${spring.activemq.exception-queue}",
            containerFactory = "defaultJmsListenerContainerFactory")
    public void receive(Message message, Session session) throws JMSException {
        TextMessage textMessage = (TextMessage) message;
        log.info(textMessage.getText());
        try {
            throw new RuntimeException(textMessage.getText());
        } catch (Exception e) {
            session.recover();
        }

    }
}
