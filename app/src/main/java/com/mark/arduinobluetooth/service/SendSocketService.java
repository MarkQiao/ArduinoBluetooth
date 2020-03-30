package com.mark.arduinobluetooth.service;

import android.text.TextUtils;

import com.mark.arduinobluetooth.APP;

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
