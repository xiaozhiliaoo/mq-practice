package org.study.mq.rabbitMQ.async;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import java.io.IOException;

public class MailMessageListener implements MessageListener {

    public void onMessage(Message message) {
        String body = new String(message.getBody());
        ObjectMapper mapper = new ObjectMapper();
        try {
            Mail mail = mapper.readValue(body, Mail.class);
            System.out.println("接收到邮件消息：" + mail);

            sendEmail(mail);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendEmail(Mail mail) {
        //调用 JavaMail API 发送邮件
    }
}
