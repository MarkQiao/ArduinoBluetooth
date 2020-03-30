package com.mark.arduinobluetooth.service;

import com.mark.arduinobluetooth.APP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author wangqiao
 */
public class ReceiveSocketService {

  public void receiveMessage(GetInfo mGetInfo) {

    if (APP.bluetoothSocket == null) {
      return;
    }
    try {
      InputStream inputStream = APP.bluetoothSocket.getInputStream();
      // 从客户端获取信息
      BufferedReader bff = new BufferedReader(new InputStreamReader(inputStream));
      String json;
      while (true) {
        while ((json = bff.readLine()) != null) {
          mGetInfo.ongetinfo(json);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


}
