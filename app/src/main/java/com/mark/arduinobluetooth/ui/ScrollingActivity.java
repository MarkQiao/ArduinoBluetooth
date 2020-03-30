package com.mark.arduinobluetooth.ui;

import android.Manifest;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import com.mark.arduinobluetooth.R;
import com.mark.arduinobluetooth.adapter.LoanDaquanAdapter;
import com.mark.arduinobluetooth.dialog.CommonDialog;
import com.mark.arduinobluetooth.dialog.showBottomDialog;
import com.mark.arduinobluetooth.util.BluetoothUtils;
import com.mark.arduinobluetooth.util.factory.ThreadPoolProxyFactory;

import java.util.ArrayList;
import java.util.List;

public class ScrollingActivity extends AppCompatActivity {
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Animation fabOpen;
    private Animation fabOnClick;
    FloatingActionButton fab;

    private RecyclerView u, s;
    private ProgressBar mProgressBar;
    private int MY_PERMISSION_REQUEST_CONSTANT = 1024;
    LoanDaquanAdapter mItemSelectDailogAdapters;
    LoanDaquanAdapter mItemSelectDailogAdapter;

    ProgressDialog dialogs;

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
        dialogs = new ProgressDialog(ScrollingActivity.this);
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
                BluetoothUtils.getInstance().startScanBluth();
                //        SendSocketService.sendMessage("111111");
                fab.startAnimation(fabOnClick);
            }
        });
        BluetoothUtils.getInstance().checkBluetooth(ScrollingActivity.this);
        showProgressBar();
        this.u = ((RecyclerView) findViewById(R.id.pairedDevicesRecyclerView));
        LinearLayoutManager localLinearLayoutManager = new LinearLayoutManager(this);
        this.u.setLayoutManager(localLinearLayoutManager);
        this.u.setNestedScrollingEnabled(false);

        this.s = ((RecyclerView) findViewById(R.id.availableDevicesRecyclerView));
        localLinearLayoutManager = new LinearLayoutManager(this);
        this.s.setLayoutManager(localLinearLayoutManager);
        this.s.setNestedScrollingEnabled(false);
        mItemSelectDailogAdapters = new LoanDaquanAdapter(new ArrayList<BluetoothDevice>(), this);
        this.s.setAdapter(mItemSelectDailogAdapters);
        mItemSelectDailogAdapters.setOnItemClickListener(new LoanDaquanAdapter.OnItemClickListener() {


            @Override
            public void onItemClick(BluetoothDevice devices) {
                BluetoothUtils.getInstance().createBond(devices);
            }

            @Override
            public boolean onLongClick(BluetoothDevice devices) {
                return false;
            }


        });


        mItemSelectDailogAdapter = new LoanDaquanAdapter(BluetoothUtils.getInstance().getSaveDeviceList(), this);
        this.u.setAdapter(mItemSelectDailogAdapter);
        mItemSelectDailogAdapter.setOnItemClickListener(new LoanDaquanAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final BluetoothDevice devices) {
                showProgressDialog("正在连接......");

                /**
                 * 开启蓝牙服务端
                 */
                ThreadPoolProxyFactory.getNormalThreadPoolProxy().execute(new Runnable() {
                    @Override
                    public void run() {
                        BluetoothUtils.getInstance().connect(ScrollingActivity.this, devices, dialogs);

                    }
                });
                //        TypeDialog dialog = new TypeDialog(ScrollingActivity.this);
                //        dialog.show();
            }

            @Override
            public boolean onLongClick(BluetoothDevice devices) {
                initDialog(devices);
                return false;
            }

        });

    }

    private void initDialog(final BluetoothDevice devices) {
        final CommonDialog dialog = new CommonDialog(this);
        dialog.setMessage("确定要删除：" + devices.getName())
                .setImageResId(R.mipmap.ic_launcher)
                .setSingle(true).setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
            @Override
            public void onPositiveClick() {
                dialog.dismiss();
                BluetoothUtils.getInstance().unpairDevice(devices);
                Toast.makeText(ScrollingActivity.this, "确定", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNegtiveClick() {
                dialog.dismiss();
                Toast.makeText(ScrollingActivity.this, "取消", Toast.LENGTH_SHORT).show();
            }
        }).show();
    }

    private List<BluetoothDevice> mAvailableDeviceList = new ArrayList();


    /**
     * 广播接收器
     */
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
           Log.d("------------>",action);
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                showProgressBar();
            }
            if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {  //完成查询
                hideProgressBar();
            }
            if (action != null) {
                switch (action) {
                    case BluetoothAdapter.ACTION_STATE_CHANGED:
                        int blueState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0);
                        switch (blueState) {
                            case BluetoothAdapter.STATE_TURNING_ON:
                                Toast.makeText(context, "蓝牙正在打开", Toast.LENGTH_SHORT).show();
                                break;
                            case BluetoothAdapter.STATE_ON:
                                Toast.makeText(context, "蓝牙已经打开", Toast.LENGTH_SHORT).show();
                                break;
                            case BluetoothAdapter.STATE_TURNING_OFF:
                                Toast.makeText(context, "蓝牙正在关闭", Toast.LENGTH_SHORT).show();
                                break;
                            case BluetoothAdapter.STATE_OFF:
                                Toast.makeText(context, "蓝牙已经关闭", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        break;

                    case BluetoothDevice.ACTION_ACL_CONNECTED:
                        Toast.makeText(context, "蓝牙设备已连接", Toast.LENGTH_SHORT).show();
                        break;

                    case BluetoothDevice.ACTION_ACL_DISCONNECTED:
                        Toast.makeText(context, "蓝牙设备已断开", Toast.LENGTH_SHORT).show();
                        break;
                }

            }


            if (BluetoothDevice.ACTION_FOUND.equals(action)) {

                if ((!BluetoothUtils.getInstance().getSaveDeviceList().contains(device)) && (!mAvailableDeviceList.contains(device))) {
                    mAvailableDeviceList.add(device);
                    Log.d("------->", "-->" + device.getName() + ", " + device.getAddress());
                    mItemSelectDailogAdapters.setData(mAvailableDeviceList);
                }
            }      //状态改变时
            else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                //        device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                switch (device.getBondState()) {
                    case BluetoothDevice.BOND_BONDING://正在配对
                        Log.d("BlueToothTestActivity", "正在配对......");
                        showProgressDialog("正在配对......");
                        break;
                    case BluetoothDevice.BOND_BONDED://配对结束
                        Log.d("BlueToothTestActivity", "完成配对");
                        if (dialogs != null) {
                            dialogs.dismiss();
                        }
                        mItemSelectDailogAdapter.setData(BluetoothUtils.getInstance().getSaveDeviceList());
                        //            onRegisterBltReceiver.onBltEnd(device);
                        break;
                    case BluetoothDevice.BOND_NONE://取消配对/未配对
                        Log.d("BlueToothTestActivity", "取消配对");
                        if (dialogs != null) {
                            dialogs.dismiss();
                        }
                        mItemSelectDailogAdapter.setData(BluetoothUtils.getInstance().getSaveDeviceList());
                    default:
                        break;
                }
            }

        }
    };

    private void showProgressDialog(String str) {
        if (dialogs != null) {
            dialogs.setMessage(str);
            dialogs.show();
        }
    }

    private void hideProgressDialog() {
        if (dialogs != null) {
            dialogs.dismiss();
        }
    }

    private void hideProgressBar() {
        this.mProgressBar.setVisibility(View.GONE);
        //    BluetoothUtils.getInstance().stopSearthBltDevice();
    }

    private void showProgressBar() {
        this.mProgressBar.setVisibility(View.VISIBLE);
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

    @Override
    protected void onResume() {
        BluetoothUtils.getInstance().startDiscovery(ScrollingActivity.this, receiver);
        this.fab.startAnimation(this.fabOpen);
        if (BluetoothUtils.mBluetoothAdapter != null) {
            if (!BluetoothUtils.mBluetoothAdapter.isEnabled()) {
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
