package org.lili.mq.activemq.dt;

import org.junit.Before;
import org.junit.Test;
import org.lili.mq.activemq.dt.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestDT {

    private ApplicationContext container;

    @Before
    public void setup() {
        container = new ClassPathXmlApplicationContext("dt/application.xml");
    }

    @Test
    public void newUser() throws InterruptedException {
        UserService userService = (UserService) container.getBean("userService");

        userService.newUser("测试", 1500);

        Thread.sleep(10000);
    }
}
