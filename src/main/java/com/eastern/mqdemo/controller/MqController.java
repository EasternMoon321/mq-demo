package com.eastern.mqdemo.controller;

import org.apache.activemq.ScheduledMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.jms.Queue;

/**
 * @Author chensheng13
 * @Description TODO
 * @Date 2022/9/17 13:29
 * @Version 1.0
 */
@RestController
@RequestMapping("/mq")
public class MqController {

    @Resource
    private JmsTemplate jmsTemplate;
    @Value("${spring.activemq.queue}")
    private String queueName;

    @RequestMapping(value = "/sendMsg", method = RequestMethod.GET)
    public void sendMsg(String msg) {
        jmsTemplate.convertAndSend(queueName, msg);
    }

    @RequestMapping(value = "/delayMsg", method = RequestMethod.GET)
    public void delayMsg(String msg) {
        jmsTemplate.convertAndSend(queueName, msg, (message)-> {
            message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, 10 * 1000);
            return message;
        });
    }

    @RequestMapping(value = "/callbackMsg", method = RequestMethod.GET)
    public void callbackMsg(String msg) {
//        jmsTemplate.execute();
    }
}
