package com.atguigu.rabbitmq.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

/**
 * Consumer 限流机制
 * 1. 确保消息被确认。不确认是不继续处理其他消息的
 * 2. listener-container配置属性
 * prefetch = 1,表示消费端每次从mq拉去一条消息来消费,直到手动确认消费完毕后,才会继续拉取下一条消息。
 */
@Component
public class QosListener implements ChannelAwareMessageListener {
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        // 1. 获取消息
        System.out.println(new String(message.getBody()));
    }
}
