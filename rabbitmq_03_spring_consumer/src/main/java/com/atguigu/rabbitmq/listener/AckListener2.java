package com.atguigu.rabbitmq.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

/**
 * Consumer ACK机制：
 * 1. 设置手动签收。acknowledge="manual"
 * 2. 让监听器类实现ChannelAwareMessageListener接口
 * 3. 如果消息成功处理,则调用channel的 basicAck()签收
 * 4. 如果消息处理失败,则调用channel的basicNack()拒绝签收,broker重新发送给consumer
 */
@Component
public class AckListener2 implements ChannelAwareMessageListener {
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        Thread.sleep(1000);
        // 获取消息传递标记
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            // 1. 接收消息
            System.out.println(new String(message.getBody()));
            // 2. 处理业务逻辑
            System.out.println("处理业务逻辑...");
//            int i = 3 / 0; // 模拟业务异常
            // ③ 手动签收
            /*
                 第一个参数：表示收到的标签
                 第二个参数：如果为true表示可以签收所有的消息
             */
            channel.basicAck(deliveryTag, true);
        } catch (Exception e) {
            e.printStackTrace();
            // ④ 拒绝签收
             /*
                 第三个参数：requeue：重回队列。
                 设置为true,则消息重新回到queue,broker会重新发送该消息给消费端
             */
            channel.basicNack(deliveryTag, true, true);
        }
    }
}
