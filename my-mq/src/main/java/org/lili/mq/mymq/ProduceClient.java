package org.lili.mq.mymq;

/**
 * Created by niwei on 2018/2/13.
 */
public class ProduceClient {

    public static void main(String[] args) throws Exception {
        MqClient client = new MqClient();

        client.produce("Hello World");
    }

}
