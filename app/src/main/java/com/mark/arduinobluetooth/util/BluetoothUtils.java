package com.mark.arduinobluetooth.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.util.Log;

import com.mark.arduinobluetooth.APP;
import com.mark.arduinobluetooth.ui.GameActivity;
import com.mark.arduinobluetooth.ui.ProgressActivity;
import com.mark.arduinobluetooth.ui.SendInfoActivity;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author wangqiao
 */
public class BluetoothUtils {

    public static volatile BluetoothUtils instance;
    public static BluetoothAdapter mBluetoothAdapter;

    public static BluetoothUtils getInstance() {
        if (instance == null) {
            synchronized (BluetoothUtils.class) {
                if (instance == null) {
                    instance = new BluetoothUtils();
                    mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                }
            }
        }
        return instance;
    }

    /**
     * 尝试配对和连接
     *
     * @param btDev
     */
    public void createBond(BluetoothDevice btDev) {
        if (btDev.getBondState() == BluetoothDevice.BOND_NONE) {
            //如果这个设备取消了配对，则尝试配对
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                btDev.createBond();
            }
        } else if (btDev.getBondState() == BluetoothDevice.BOND_BONDED) {
            //如果这个设备已经配对完成，则尝试连接
            //      connect(btDev, handler);
        }
    }


    /**
     * 注册异步搜索蓝牙设备的广播
     */
    public void startDiscovery(Activity mContext, BroadcastReceiver receiver) {
        // 设置广播信息过滤
        IntentFilter localIntentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        localIntentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        localIntentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        //搜索发现设备
        localIntentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        //状态改变
        localIntentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        //行动扫描模式改变了
        localIntentFilter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        //动作状态发生了变化
        localIntentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        mContext.registerReceiver(receiver, localIntentFilter);
        // 寻找蓝牙设备，android会将查找到的设备以广播形式发出去
        startScanBluth();
    }

    /**
     * 搜索蓝牙的方法
     */
    public void startScanBluth() {
        // 判断是否在搜索,如果在搜索，就取消搜索
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
        // 开始搜索
        mBluetoothAdapter.startDiscovery();
    }

    //检测蓝牙
    public void checkBluetooth(Activity mactivity) {
        if (!mBluetoothAdapter.isEnabled()) {
            OpenBluetooth(mactivity);
        }
        while (mBluetoothAdapter.isDiscovering()) {
            return;
        }
        mBluetoothAdapter.startDiscovery();
    }

    //停止搜索蓝牙设备

    public boolean stopSearthBltDevice() {
        //暂停搜索设备
        if (mBluetoothAdapter != null) {
            return mBluetoothAdapter.cancelDiscovery();
        }
        return false;
    }


    public void unpairDevice(BluetoothDevice device) {
        try {
            Method m = device.getClass()
                    .getMethod("removeBond", (Class[]) null);
            m.invoke(device, (Object[]) null);
        } catch (Exception e) {
            Log.e("---", e.getMessage());
        }
    }


    /**
     * 打开蓝牙
     */
    public void OpenBluetooth(Activity mactivity) {
        mactivity.startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), 69);
    }

    /**
     * 获取保存上的蓝牙设备
     */
    public List<BluetoothDevice> getSaveDeviceList() {
        List<BluetoothDevice> DeviceList = new ArrayList();
        Object localObject = mBluetoothAdapter.getBondedDevices();
        if (((Set) localObject).size() > 0) {
            localObject = ((Set) localObject).iterator();
            while (((Iterator) localObject).hasNext()) {
                BluetoothDevice localBluetoothDevice = (BluetoothDevice) ((Iterator) localObject).next();
                Log.d("MainActivity", "paired device: " + localBluetoothDevice.getName() + ", " + localBluetoothDevice.getAddress() + "," + localBluetoothDevice.getBluetoothClass().getMajorDeviceClass());
                if (!DeviceList.contains(localBluetoothDevice)) {
                    DeviceList.add(localBluetoothDevice);
                }
            }
        }
        return DeviceList;
    }

    /**
     * 配对成功后的蓝牙套接字
     */
    private BluetoothSocket mBluetoothSocket;
    String uuidValue = "00001101-0000-1000-8000-00805F9B34FB";

    UUID mactekHartModemUuid = UUID.fromString(uuidValue);

    /***
     * 蓝牙连接代码,项目中连接会使用封装的工具类，在这里提取重写
     */
    public void connect(Context mcontext, int type, BluetoothDevice bluetoothDevice, ProgressDialog dialog) {
        try {
//            bluetoothDevice.connectGatt()

            mBluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(mactekHartModemUuid);
            if (mBluetoothSocket != null) {
                APP.bluetoothSocket = mBluetoothSocket;
                if (mBluetoothAdapter.isDiscovering()) {
                    mBluetoothAdapter.cancelDiscovery();  //定制搜索
                }
                if (!mBluetoothSocket.isConnected()) {
                    mBluetoothSocket.connect();
                }
                dialog.dismiss();
                Intent intent = null;
                switch (type) {
                    case 0:
                        intent = new Intent(mcontext, GameActivity.class);
                        break;
                    case 1:
                        intent = new Intent(mcontext, ProgressActivity.class);
                        break;
                    case 2:
                        intent = new Intent(mcontext, ProgressActivity.class);
                        break;
                    case 3:
                        intent = new Intent(mcontext, SendInfoActivity.class);
                        break;
                    default:
                        break;
                }
                //        EventBus.getDefault().post(new BluRxBean(connectsuccess, bluetoothDevice)); //链接成功
                //                Intent intent = new Intent(mcontext, SendInfoActivity.class);
                intent.putExtra("DeviceName", bluetoothDevice.getName());
                mcontext.startActivity(intent);
            }
        } catch (IOException e) {
            Log.e("-------->", e.getMessage());
            dialog.dismiss();
            try {
                APP.bluetoothSocket.close();
                mBluetoothSocket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }

}
