package com.mark.arduinobluetooth;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ScrollingActivity extends AppCompatActivity {
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Animation fabOpen;
    private Animation fabOnClick;
    FloatingActionButton fab;
    private BluetoothAdapter mBluetoothAdapter;
    private RecyclerView u, s;
    private ProgressBar mProgressBar;
    private int MY_PERMISSION_REQUEST_CONSTANT = 1024;
    LoanDaquanAdapter mItemSelectDailogAdapters;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        if (Build.VERSION.SDK_INT >= 6.0) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSION_REQUEST_CONSTANT);
        }
        init();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSION_REQUEST_CONSTANT) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //permission granted!
            }
        }

    }

    public void init() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //设置收缩展开toolbar字体颜色
        setSupportActionBar(toolbar);
        collapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        fab = (FloatingActionButton) findViewById(R.id.bt_scan_fab);
        mProgressBar = ((ProgressBar) findViewById(R.id.devicesScanProgress));
        fabOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fabOnClick = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                startScanBluth();
                fab.startAnimation(fabOnClick);
            }
        });
        checkBluetooth();

        this.u = ((RecyclerView) findViewById(R.id.pairedDevicesRecyclerView));
        LinearLayoutManager localLinearLayoutManager = new LinearLayoutManager(this);
        this.u.setLayoutManager(localLinearLayoutManager);
        this.u.setNestedScrollingEnabled(false);

        this.s = ((RecyclerView) findViewById(R.id.availableDevicesRecyclerView));
        localLinearLayoutManager = new LinearLayoutManager(this);
        this.s.setLayoutManager(localLinearLayoutManager);
        this.s.setNestedScrollingEnabled(false);
        mItemSelectDailogAdapters = new LoanDaquanAdapter(DeviceList, this);
        this.s.setAdapter(mItemSelectDailogAdapters);
        t();

    }


    /**
     * 注册异步搜索蓝牙设备的广播
     */
    private void startDiscovery() {
        // 设置广播信息过滤
        IntentFilter localIntentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        localIntentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        localIntentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

// 注册广播接收器，接收并处理搜索结果
        registerReceiver(receiver, localIntentFilter);
// 寻找蓝牙设备，android会将查找到的设备以广播形式发出去
        startScanBluth();
    }

    /**
     * 搜索蓝牙的方法
     */
    private void startScanBluth() {
        // 判断是否在搜索,如果在搜索，就取消搜索
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
        // 开始搜索
        mBluetoothAdapter.startDiscovery();
    }
    private List<BluetoothDevice> mAvailableDeviceList = new ArrayList();
    /**
     * 广播接收器
     */
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                if ((!DeviceList.contains(device)) && (!mAvailableDeviceList.contains(device)))
                {
                    mAvailableDeviceList.add(device);
                    Log.d("------->", "-->" + device.getName() + ", " + device.getAddress());
                    mItemSelectDailogAdapters.setData(mAvailableDeviceList);
                }
            }
            if(BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)){
                showProgressBar();
            }
            if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){
                hideProgressBar();
            }
          /*  do
            {
            } while (!BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action));*/


        }
    };


    private List<BluetoothDevice> DeviceList = new ArrayList();

    private void t() {
        Object localObject = this.mBluetoothAdapter.getBondedDevices();
        if (((Set) localObject).size() > 0) {
            localObject = ((Set) localObject).iterator();
            while (((Iterator) localObject).hasNext()) {
                BluetoothDevice localBluetoothDevice = (BluetoothDevice) ((Iterator) localObject).next();
                Log.d("MainActivity", "paired device: " + localBluetoothDevice.getName() + ", " + localBluetoothDevice.getAddress() + "," + localBluetoothDevice.getBluetoothClass().getMajorDeviceClass());
                if (!this.DeviceList.contains(localBluetoothDevice)) {
                    this.DeviceList.add(localBluetoothDevice);
                }
            }
        }
        LoanDaquanAdapter mItemSelectDailogAdapter = new LoanDaquanAdapter(DeviceList, this);
        this.u.setAdapter(mItemSelectDailogAdapter);
    }

    //检测蓝牙
    private void checkBluetooth() {
        if (!this.mBluetoothAdapter.isEnabled()) {
            n();
        }
        showProgressBar();
        while (this.mBluetoothAdapter.isDiscovering()) {
            return;
        }
        this.mBluetoothAdapter.startDiscovery();
    }

    private void hideProgressBar() {
        this.mProgressBar.setVisibility(View.GONE);
    }

    private void showProgressBar() {
        this.mProgressBar.setVisibility(View.VISIBLE);
    }

    //打开蓝牙
    private void n() {
        startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), 69);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem paramMenuItem) {

        switch (paramMenuItem.getItemId()) {
            default:
                new showBottomDialog(this).show();
                return super.onOptionsItemSelected(paramMenuItem);
        }

    }

    protected void onResume() {
        startDiscovery();
        this.fab.startAnimation(this.fabOpen);
        if (this.mBluetoothAdapter != null) {
            if (!this.mBluetoothAdapter.isEnabled()) {
            }
        } else {
            return;
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            //取消注册,防止内存泄露（onDestroy被回调代不代表Activity被回收？：具体回收看系统，由GC回收，同时广播会注册到系统
            //管理的ams中，即使activity被回收，reciver也不会被回收，所以一定要取消注册），
            unregisterReceiver(receiver);
        }
    }

}
