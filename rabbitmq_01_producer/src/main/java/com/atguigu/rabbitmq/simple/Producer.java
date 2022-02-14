package com.atguigu.rabbitmq.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {

    public static void main(String[] args) throws Exception {
        // 创建一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.229.128");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        factory.setUsername("admin");
        factory.setPassword("123456");
        // 使用连接工厂创建一个连接
        Connection conn = factory.newConnection();
        // 使用连接创建通道
        Channel channel = conn.createChannel();
        System.out.println("channel = " + channel);
        // 创建一个队列
//         * @param queue 队列名
//         * @param durable 如果我们声明一个持久队列，则为 true（该队列将在服务器重新启动后继续存在）
//         * @param exclusive 如果我们声明一个独占队列，则为 true（仅限于此连接）
//         * @param autoDelete 如果我们声明一个自动删除队列，则为 true（服务器将在不再使用时将其删除）
//         * @param arguments 队列的其他属性（构造参数）
        channel.queueDeclare("simplequeue",true,false,false,null);
        // 发消息
        String msg = "hello rabbitmq";
//         * @param exchange 将消息发布到的交易所
//         * @param routingKey 队列名
//         * @param props 消息的其他属性 - 路由标头等
//         * @param body 消息体
        channel.basicPublish("","simplequeue",null,msg.getBytes());
        // 关闭连接
        channel.close();
        conn.close();
    }

}
