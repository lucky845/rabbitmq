package com.atguigu.rabbitmq.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class Fanout1Listener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        System.out.println("广播监听器1：" + new String(message.getBody()));
    }
}
