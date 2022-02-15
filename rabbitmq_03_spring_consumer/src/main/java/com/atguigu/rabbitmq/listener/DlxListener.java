package com.atguigu.rabbitmq.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

/**
 * 测试消息死信的3. 消息拒收
 */
@Component
public class DlxListener implements ChannelAwareMessageListener {
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();

        try {
            // 1. 接收转换消息
            System.out.println(new String(message.getBody()));
            // 2. 处理业务逻辑
            int i = 3 / 0; // 模拟业务出现异常
            // 手动签收
            channel.basicAck(deliveryTag, true);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("出现异常，拒绝接受...");
            // 4. 拒绝签收，不重新回到队列（requeue = false）
            channel.basicNack(deliveryTag, true, false);
        }
    }
}
