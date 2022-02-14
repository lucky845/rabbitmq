package com.atguigu.rabbitmq.work;

import com.atguigu.rabbitmq.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Producer {

    public static String QUEUE_NAME = "work_queue";

    public static void main(String[] args) throws Exception {
        // 获取连接
        Connection conn = ConnectionUtils.getConnection();
        // 使用连接创建通道
        Channel channel = conn.createChannel();
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        for (int i = 0; i < 10; i++) {
            String body = "hello rabbit " + i;
            channel.basicPublish("",QUEUE_NAME,null,body.getBytes());
        }
        channel.close();
        conn.close();
    }

}
