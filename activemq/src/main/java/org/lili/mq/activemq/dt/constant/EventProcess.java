package org.lili.mq.activemq.dt.constant;

public enum EventProcess {

    NEW("NEW", "新建"),
    PUBLISHED("PUBLISHED", "已发布"),
    PROCESSED("PROCESSED", "已处理"),
    ;

    private String value;
    private String desc;

    EventProcess(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
