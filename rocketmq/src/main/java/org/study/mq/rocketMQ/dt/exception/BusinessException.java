package org.study.mq.rocketMQ.dt.exception;

/**
 * 通用的业务异常定义
 */
public class BusinessException extends RuntimeException {

    public BusinessException() {
        super();
    }

    /**
     * @param msg 异常信息
     */
    public BusinessException(String msg) {
        super(msg);
    }

    /**
     * @param e 异常
     */
    public BusinessException(Throwable e) {
        super(e);
    }

    /**
     * @param msg 异常信息
     * @param e   异常
     */
    public BusinessException(String msg, Throwable e) {
        super(msg, e);
    }
}
