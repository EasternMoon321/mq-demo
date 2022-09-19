package com.eastern.mqdemo.controller;

import com.eastern.mqdemo.config.ActiveMQProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ScheduledMessage;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.jms.*;

/**
 * @Author chensheng13
 * @Description TODO
 * @Date 2022/9/17 13:29
 * @Version 1.0
 */
@Api(tags = "mq队列")
@RestController
@RequestMapping("/mq")
public class MqController {

    @Resource
    private ActiveMQProperties properties;

    @ApiOperation("sendNormalMsg")
    @RequestMapping(value = "/sendNormalMsg", method = RequestMethod.GET)
    public void sendNormalMsg(String msg) throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(properties.getUser(), properties.getPassword(), properties.getBrokerUrl());
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = new ActiveMQQueue(properties.getQueue());
        MessageProducer producer = session.createProducer(queue);

        TextMessage textMessage = session.createTextMessage(msg);
        producer.send(textMessage);
        session.close();
        connection.close();
    }

    @Resource(name = "normalTemplate")
    private JmsTemplate normalTemplate;

    @ApiOperation("sendTemplateMsg")
    @RequestMapping(value = "/sendTemplateMsg", method = RequestMethod.GET)
    public void sendTemplateMsg(String msg){
        normalTemplate.convertAndSend(properties.getQueue(), msg);
    }

    @Resource(name = "asyTemplate")
    private JmsTemplate asyTemplate;
    @ApiOperation("sendAsyMsg")
    @RequestMapping(value = "/sendAsyMsg", method = RequestMethod.GET)
    public void sendAsyMsg(String msg){
        asyTemplate.convertAndSend(properties.getQueue(), msg);
    }

    @ApiOperation("sendExceptionMsg")
    @RequestMapping(value = "/sendExceptionMsg", method = RequestMethod.GET)
    public void sendExceptionMsg(String msg) {
        normalTemplate.convertAndSend(properties.getExceptionQueue(), msg);
    }

    @ApiOperation("sendPersistentMsg")
    @RequestMapping(value = "/sendPersistentMsg", method = RequestMethod.GET)
    public void sendPersistentMsg(String msg) {

    }

}
