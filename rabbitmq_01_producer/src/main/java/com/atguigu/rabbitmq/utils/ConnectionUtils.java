package com.atguigu.rabbitmq.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConnectionUtils {

    public static Connection getConnection() throws Exception{
        // 定义连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 设置服务地址
        factory.setHost("192.168.229.128");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        factory.setUsername("admin");
        factory.setPassword("123456");
        // 通过工厂获取连接
        Connection conn = factory.newConnection();
        return conn;
    }

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.getConnection();
        System.out.println("connection = " + connection);
        connection.close();
    }

}
