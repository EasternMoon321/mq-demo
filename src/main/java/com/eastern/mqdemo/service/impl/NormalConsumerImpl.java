package com.eastern.mqdemo.service.impl;

import com.eastern.mqdemo.service.IConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

/**
 * @Author chensheng13
 * @Description TODO
 * @Date 2022/9/17 13:50
 * @Version 1.0
 */
@Slf4j
@Service
public class NormalConsumerImpl implements IConsumer {

    @Override
    @JmsListener(destination = "${spring.activemq.queue}")
    public void receive(String msg) {
        log.info("{} receive msg {}", this.getClass().getName(), msg);
    }
}
