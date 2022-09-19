package com.eastern.mqdemo.service.queue;

import com.eastern.mqdemo.config.ActiveMQProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.*;

@Slf4j
@Service
public class ConsumerListener implements MessageListener {

    @Bean
    public DefaultMessageListenerContainer jmsContainer(@Qualifier("normalFactory") ConnectionFactory factory,
                                                        @Qualifier("normalQueue") Queue queue,
                                                        MessageListener messageListener) {
        DefaultMessageListenerContainer jmsContainer = new DefaultMessageListenerContainer();
        jmsContainer.setConnectionFactory(factory);
        jmsContainer.setDestination(queue);
        jmsContainer.setMessageListener(messageListener);
        return jmsContainer;
    }

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            log.info("{} receive message: {}", this.getClass().getName(), textMessage.getText());
            throw new RuntimeException(textMessage.getText());
        } catch (JMSException e) {
            log.error("exception message: ", e);
        }
    }
}
