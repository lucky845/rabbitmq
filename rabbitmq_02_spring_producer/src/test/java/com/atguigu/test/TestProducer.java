package com.atguigu.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:spring-rabbitmq.xml")
public class TestProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 测试简单模式发送消息
     * 参数一：默认交换机类型 direct
     * 参数二：路由键名为队列名称
     * 参数三：发送的消息内容
     */
    @Test
    public void testSimple() {
        String message = "测试简单模式 simple";
        rabbitTemplate.convertAndSend("", "simple-queue", message.getBytes());
    }

    /**
     * 测试广播模式发送消息
     * 参数一：交换机名
     * 参数二：路由键名（广播设置为空）
     * 参数三：发送的消息内容
     */
    @Test
    public void testFanout() {
        String message = "测试广播模式 fanout";
        rabbitTemplate.convertAndSend("fanout-exchange", "", message.getBytes());
    }

    /**
     * 测试通配模式发送消息
     * 参数一：交换机名称
     * 参数二：路由器键名
     * 参数三：发送的消息内容
     */
    @Test
    public void testTopic(){
        String message = "测试通配模式 topic";
        rabbitTemplate.convertAndSend("topic-exchange","123.error",message.getBytes());
        rabbitTemplate.convertAndSend("topic-exchange","order.123",message.getBytes());
        rabbitTemplate.convertAndSend("topic-exchange","info.123.456",message.getBytes());
    }

}
