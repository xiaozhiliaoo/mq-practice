package org.study.mq.rabbitMQ.async;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 业务处理
 */
public class Business {

    //用户注册
    public void userRegister(){
        //校验用户填写的信息是否完整

        //将用户及相关信息保存到数据库

        //注册成功后发送一条消息表示要发送邮件
        AbstractApplicationContext ctx =
                new ClassPathXmlApplicationContext("async-context.xml");
        RabbitTemplate template = ctx.getBean(RabbitTemplate.class);
        Mail mail = new Mail();
        mail.setTo("12345678@qq.com");
        mail.setSubject("我的一封邮件");
        mail.setContent("我的邮件内容");
        template.convertAndSend(mail);
        ctx.close();
    }

    public static void main(final String... args) throws Exception {
        Business business = new Business();
        business.userRegister();
    }
}
