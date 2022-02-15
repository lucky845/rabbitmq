package com.atguigu.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "exchange-boot";
    public static final String QUEUE_NAME1 = "queue-boot1";
    public static final String QUEUE_NAME2 = "queue-boot2";

    // 1 交换机
    @Bean("bootExchange")
    public Exchange bootExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE_NAME).durable(true).build();
    }

    //2.Queue 队列
    @Bean("bootQueue1")
    public Queue bootQueue1() {
        return QueueBuilder.durable(QUEUE_NAME1).build();
    }

    @Bean("bootQueue2")
    public Queue bootQueue2() {
        return QueueBuilder.durable(QUEUE_NAME2).build();
    }

    //3. 队列和交互机绑定关系 Binding
    /*
        1. 知道哪个队列
        2. 知道哪个交换机
        3. routing key
        noargs()：表示不指定参数
     */
    @Bean
    public Binding bindQueueExchange1(@Qualifier("bootQueue1") Queue queue,
                                      @Qualifier("bootExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("boot.#").noargs();
    }

    @Bean
    public Binding bindQueueExchange2(@Qualifier("bootQueue2") Queue queue,
                                      @Qualifier("bootExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("#.hehe").noargs();
    }
}
