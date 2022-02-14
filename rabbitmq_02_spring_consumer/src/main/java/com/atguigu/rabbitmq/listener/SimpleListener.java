package com.atguigu.rabbitmq.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class SimpleListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        System.out.println("简单监听器：" + new String(message.getBody()));
    }
}
