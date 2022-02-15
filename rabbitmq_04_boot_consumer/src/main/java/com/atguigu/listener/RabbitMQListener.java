package com.atguigu.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQListener {

    // 监听两个队列
    @RabbitListener(queues = {"queue-boot1", "queue-boot2"})
    public void listenerQueue1(Message message) {
        System.out.println(new String(message.getBody()));
    }

}
