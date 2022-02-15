package com.atguigu.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:spring-rabbitmq.xml")
public class ProducerTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 确认模式：
     * 步骤：
     * 1. 确认模式开启：ConnectionFactory中开启publisher-confirms="true"
     * 2. 在rabbitTemplate定义ConfirmCallBack回调函数
     */
    @Test
    public void testConfirm() {
        // 定义确认回调函数
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                if (ack) {
                    //接收成功
                    System.out.println("接收成功消息" + cause);
                } else {
                    //接收失败
                    System.out.println("接收失败消息" + cause);
                    //做一些处理,让消息再次发送。
                }
            }
        });
        // 发送消息
        rabbitTemplate.convertAndSend("exchange-confirm", "confirm", "测试确认模式发送成功的消息");
        rabbitTemplate.convertAndSend("exchange-confirm11", "confirm", "测试确认模式发送失败的消息");
    }

    /**
     * 回退模式： 当消息发送给Exchange后,Exchange路由到Queue失败时 才会执行 ReturnCallBack
     * 步骤：
     * 1. 开启回退模式：publisher-returns="true"
     * 2. 设置ReturnCallBack
     * 3. 设置Exchange处理消息的模式：
     * 1). 如果消息没有路由到Queue,则丢弃消息（默认）
     * 2). 如果消息没有路由到Queue,返回给消息发送方ReturnCallBack
     * rabbitTemplate.setMandatory(true);
     */
    @Test
    public void testReturn() {
        // 1.设置交换机处理失败消息的模式
        // Mandatory为true时,消息通过交换器无法匹配到队列会返回给生产者为false时,匹配不到会直接被丢弃
        rabbitTemplate.setMandatory(true);
        // 2.设置Return回调函数
        rabbitTemplate.setReturnCallback((Message message, int replyCode, String replyText, String exchange, String routingKey) -> {
            System.out.println("return 执行了....");

            System.out.println("message = " + message);
            System.out.println("replyCode = " + replyCode);
            System.out.println("replyText = " + replyText);
            System.out.println("exchange = " + exchange);
            System.out.println("routingKey = " + routingKey);

            //处理
        });
        // 3.发送消息
        rabbitTemplate.convertAndSend("exchange-confirm", "confirm", "测试回退模式发送成功的消息");
    }

    /**
     * 测试消费端限流的消息
     */
    @Test
    public void send() {
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("exchange-confirm", "confirm", "测试消费端限流...");
        }
    }

    /**
     * 测试TTL过期 1
     */
    @Test
    public void testTTL1() {
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("exchange-ttl", "ttl.haha", "测试TTL过期...");
        }
    }

    /**
     * 测试TTL过期 2
     * TTL：过期时间
     * 1. 队列统一过期
     * 2. 消息单独过期
     * 如果设置了消息的过期时间,也设置了队列的过期时间,它以时间短的为准。
     */
    @Test
    public void testTTL2() {
        // 消息后处理对象，设置一些消息的参数
        MessagePostProcessor messagePostProcessor = new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                // 1. 设置message的信息
                // 2. 设置消息的过期时间，6秒后过期
                message.getMessageProperties().setExpiration("6000");
                return message;
            }
        };
        // 消息单独过期
        rabbitTemplate.convertAndSend("exchange-ttl", "ttl.hehe", "测试TTl消息过期...", messagePostProcessor);
    }

    /**
     * 发送测试死信消息：
     * 1. 过期时间
     * 2. 长度限制
     * 3. 消息拒收
     */
    @Test
    public void testDlx() {
        // 1. 测试过期时间，消息死信
//        rabbitTemplate.convertAndSend("test-exchange-dlx", "test.dlx.hehe", "我是一条消息,我会死吗？");

        // 2. 测试长度限制后，消息死信
//        for (int i = 0; i < 20; i++) {
//            rabbitTemplate.convertAndSend("test-exchange-dlx", "test.dlx.haha", "我是一条消息,我会死吗？");
//        }

        // 3. 测试消息拒收后，消息死信
        rabbitTemplate.convertAndSend("test-exchange-dlx", "test.dlx.haha", "我是一条消息,我会死吗？");
    }

    /**
     * 测试延迟队列
     *
     * @throws InterruptedException
     */
    @Test
    public void testDelay() throws InterruptedException {
        //1.发送订单消息。 将来是在订单系统中,下单成功后,发送消息
        rabbitTemplate.convertAndSend("exchange-delay",
                "delay.msg", "订单信息：id=1,time=2020年10月17日11：41：47");

        //2.打印倒计时10秒
        for (int i = 10; i > 0; i--) {
            System.out.println(i + "...");
            Thread.sleep(1000);
        }
    }


}
