package com.eastern.mqdemo.service;

/**
 * @Author chensheng13
 * @Description TODO
 * @Date 2022/9/17 13:49
 * @Version 1.0
 */
public interface IConsumer {
    void receive(String msg);
}
