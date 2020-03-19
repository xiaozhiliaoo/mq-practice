package org.study.mq.rocketMQ.order;

public class OrderMessage {

    private int id;//订单ID

    private String status;//订单状态

    private int sendOrder;//订单消息发送顺序

    private String content;//订单内容

    public int getId() {
        return id;
    }

    public OrderMessage setId(int id) {
        this.id = id;

        return this;
    }

    public String getStatus() {
        return status;
    }

    public OrderMessage setStatus(String status) {
        this.status = status;

        return this;
    }

    public int getSendOrder() {
        return sendOrder;
    }

    public OrderMessage setSendOrder(int sendOrder) {
        this.sendOrder = sendOrder;

        return this;
    }

    public String getContent() {
        return content;
    }

    public OrderMessage setContent(String content) {
        this.content = content;

        return this;
    }

    @Override
    public String toString() {
        return "订单消息{" +
                "订单ID=" + id +
                ", 发送顺序=" + sendOrder +
                ", 订单状态='" + status + '\'' +
                ", 订单内容='" + content + '\'' +
                '}';
    }
}
