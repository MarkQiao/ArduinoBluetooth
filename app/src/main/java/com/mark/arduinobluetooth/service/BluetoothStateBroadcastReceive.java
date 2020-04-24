package com.mark.arduinobluetooth.service;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.mark.arduinobluetooth.util.MessageWrap;

import org.greenrobot.eventbus.EventBus;


/**
 * @author wangqiao
 */
public class BluetoothStateBroadcastReceive extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
        switch (action) {
            case BluetoothDevice.ACTION_ACL_CONNECTED:
                Toast.makeText(context, "蓝牙设备:" + device.getName() + "已链接", Toast.LENGTH_SHORT).show();
                break;
            case BluetoothDevice.ACTION_ACL_DISCONNECTED:
                EventBus.getDefault().post(MessageWrap.getInstance(BluetoothDevice.ACTION_ACL_DISCONNECTED, "蓝牙设备:" + device.getName() + "已断开"));
                Toast.makeText(context, "蓝牙设备:" + device.getName() + "已断开", Toast.LENGTH_SHORT).show();
                break;
            case BluetoothAdapter.ACTION_STATE_CHANGED:
                int blueState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0);
                switch (blueState) {
                    case BluetoothAdapter.STATE_OFF:
                        EventBus.getDefault().post(MessageWrap.getInstance(BluetoothAdapter.ACTION_STATE_CHANGED, "蓝牙已关闭"));
                        Toast.makeText(context, "蓝牙已关闭", Toast.LENGTH_SHORT).show();
                        break;
                    case BluetoothAdapter.STATE_ON:
                        Toast.makeText(context, "蓝牙已开启", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

}
