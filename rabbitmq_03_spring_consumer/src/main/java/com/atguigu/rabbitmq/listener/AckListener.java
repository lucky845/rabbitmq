package com.atguigu.rabbitmq.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

/**
 * 测试自动确认
 */
@Component
public class AckListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        System.out.println(new String(message.getBody()));
    }
}
