package com.atguigu.rabbitmq.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class Topic2Listener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        System.out.println("通配监听器2：" + new String(message.getBody()));
    }
}
