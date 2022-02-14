package com.atguigu.rabbitmq.work;

import com.atguigu.rabbitmq.utils.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Arrays;

public class Consumer1 {

    public static String QUEUE_NAME = "work_queue";

    public static void main(String[] args) throws Exception {
        // 创建一个连接工厂
        // 使用连接工厂创建一个连接
        Connection conn = ConnectionUtils.getConnection();
        // 使用连接创建通道
        Channel channel = conn.createChannel();
        System.out.println("channel = " + channel);
        // 创建一个队列
//         * @param queue 队列名
//         * @param durable 如果我们声明一个持久队列，则为 true（该队列将在服务器重新启动后继续存在）
//         * @param exclusive 如果我们声明一个独占队列，则为 true（仅限于此连接）
//         * @param autoDelete 如果我们声明一个自动删除队列，则为 true（服务器将在不再使用时将其删除）
//         * @param arguments 队列的其他属性（构造参数）
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        // 接收消息
        DefaultConsumer callBack = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("body = " + new String(body));
                System.out.println("================================");
            }
        };
//         * @param queue 队列名称
//         * @param autoAck 自动确认， 收到后才回确认，队列中才会删除消息
//         * @param callback 回调函数
        channel.basicConsume(QUEUE_NAME,true,callBack); // 异步
        // 关闭连接
//        channel.close();
//        conn.close();
    }

}
