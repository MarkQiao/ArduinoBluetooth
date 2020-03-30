package com.mark.arduinobluetooth.util;

/**
 * @ClassName: MessageWrap
 * @Description: MessageWrap java类作用描述
 * @Author: mr.Josh
 * @CreateDate: 2020/3/30 3:59 PM
 * @Version: 1.0
 */
public class MessageWrap {
    public final String type;
    public final String message;

    public static MessageWrap getInstance(String type, String message) {
        return new MessageWrap(type, message);
    }

    private MessageWrap(String type, String message) {
        this.type = type;
        this.message = message;
    }
}
