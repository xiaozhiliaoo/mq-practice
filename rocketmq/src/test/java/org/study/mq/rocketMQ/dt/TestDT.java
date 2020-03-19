package org.study.mq.rocketMQ.dt;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.study.mq.rocketMQ.dt.service.UserService;

public class TestDT {

    private ApplicationContext container;

    @Before
    public void setup() {
        container = new ClassPathXmlApplicationContext("dt/spring-dt.xml");
    }

    @Test
    public void newUser() throws Exception {
        UserService userService = (UserService) container.getBean("userService");
        userService.newUserAndPoint("测试分布式事务", 2000);

        Thread.sleep(5000);
    }

}
