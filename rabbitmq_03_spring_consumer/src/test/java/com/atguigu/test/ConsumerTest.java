package com.atguigu.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:spring-rabbitmq.xml")
public class ConsumerTest {

    @Test
    public void testConsumer(){
        while(true){

        }
    }

}
