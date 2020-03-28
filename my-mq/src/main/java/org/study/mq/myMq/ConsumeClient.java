package org.study.mq.myMq;

/**
 * Created by niwei on 2018/2/13.
 */
public class ConsumeClient {

    public static void main(String[] args) throws Exception {
        MqClient client = new MqClient();
        String message = client.consume();

        System.out.println("获取的消息为：" + message);
    }
}
