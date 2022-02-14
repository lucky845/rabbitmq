package com.atguigu.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:spring-rabbitmq.xml")
public class TestConsumer {

    // 直接运行即可接受已发送的消息
    @Test
    public void testConsumer(){
        while(true){

        }
    }

}
