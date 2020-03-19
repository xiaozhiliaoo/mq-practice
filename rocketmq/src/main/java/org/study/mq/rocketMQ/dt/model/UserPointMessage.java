package org.study.mq.rocketMQ.dt.model;

/**
 * 消息类，用于发送消息时的业务对象数据传递
 */
public class UserPointMessage {

    private String userId;

    private String userName;

    private Integer amount;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
