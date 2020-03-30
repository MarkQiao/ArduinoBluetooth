package com.mark.arduinobluetooth;

import android.app.Application;
import android.bluetooth.BluetoothSocket;
import android.content.Context;


public class APP extends Application {

    public static BluetoothSocket bluetoothSocket;
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}
