package com.mark.arduinobluetooth.service;

import android.text.TextUtils;
import android.util.Log;

import com.mark.arduinobluetooth.APP;
import com.mark.arduinobluetooth.util.MessageWrap;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.io.OutputStream;


/**
 * @author wangqiao
 */
public class SendSocketService {

    /**
     * 发送文本消息
     *
     * @param message
     */
    public static void sendMessage(String message) {
        Log.d("-------->", message);
        if (message.equals("") || message.isEmpty()) {
            Log.d("-------->", "1");
            EventBus.getDefault().post(MessageWrap.getInstance("1241", "发送指令不能为空，请设置后进行操作"));
            return;
        }
        Log.d("-------->", "2");
        if (APP.bluetoothSocket == null || TextUtils.isEmpty(message)) {
            return;
        }
        try {
            message += "\n";
            OutputStream outputStream = APP.bluetoothSocket.getOutputStream();
            outputStream.write(message.getBytes("utf-8"));
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
