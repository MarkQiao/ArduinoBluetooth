package com.mark.arduinobluetooth.ui;

import android.bluetooth.BluetoothDevice;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mark.arduinobluetooth.APP;
import com.mark.arduinobluetooth.R;
import com.mark.arduinobluetooth.service.GetInfo;
import com.mark.arduinobluetooth.service.ReceiveSocketService;
import com.mark.arduinobluetooth.service.SendSocketService;
import com.mark.arduinobluetooth.util.MessageWrap;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

import static com.mark.arduinobluetooth.util.factory.ThreadPoolProxyFactory.getNormalThreadPoolProxy;

/**
 * @author wangqiao
 */
public class SendInfoActivity extends AppCompatActivity {

    private TextView showtv;
    private EditText et_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_info);

        setTitle(getIntent().getStringExtra("DeviceName"));
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        EventBus.getDefault().register(this);
        showtv = findViewById(R.id.tv_show);
        et_send = findViewById(R.id.et_send);
        init();
        //开启消息接收端
        getNormalThreadPoolProxy().execute(new Runnable() {
            @Override
            public void run() {
                new ReceiveSocketService().receiveMessage(new GetInfo() {
                    @Override
                    public void ongetinfo(final String str) {
                        new Handler(getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                update("服务:  ", str);
                            }
                        });
                    }
                });
            }
        });
    }


    /**
     * 复写：添加菜单布局
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * 复写：设置菜单监听
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //actionbar navigation up 按钮
            case android.R.id.home:
                finish();
                break;
            case R.id.action_add:
                Toast.makeText(this, "Add selected", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }


    private void init() {
        et_send.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (null != keyEvent && KeyEvent.KEYCODE_ENTER == keyEvent.getKeyCode()) {
                    switch (keyEvent.getAction()) {
                        case KeyEvent.ACTION_UP:
                            //做爱做的事情
                            if (!et_send.getText().toString().isEmpty()) {
                                SendSocketService.sendMessage(et_send.getText().toString());
                                update("本机:  ", et_send.getText().toString());
                            } else {
                                Snackbar.make(et_send, "输入不能为空", Snackbar.LENGTH_LONG).show();
                            }
                            return true;
                        default:
                            return true;
                    }
                }
                return false;
            }
        });
        et_send.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // et.getCompoundDrawables()得到一个长度为4的数组，分别表示左右上下四张图片
                Drawable drawable = et_send.getCompoundDrawables()[2];
                //如果右边没有图片，不再处理
                if (drawable == null)
                    return false;
                //如果不是按下事件，不再处理
                if (event.getAction() != MotionEvent.ACTION_UP)
                    return false;
                if (event.getX() > et_send.getWidth()
                        - et_send.getPaddingRight()
                        - drawable.getIntrinsicWidth()) {
                    et_send.setText("");
                }
                return false;
            }
        });

    }

    String INFOstr = "";

    private void update(String type, String str) {
        INFOstr += type + str + "\n";
        showtv.setText(INFOstr);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMessage(MessageWrap message) {
        switch (message.type) {
            case BluetoothDevice.ACTION_ACL_DISCONNECTED:
                Snackbar bar = Snackbar.make(et_send, message.message, Snackbar.LENGTH_INDEFINITE)
                        .setAction("ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        });
                bar.getView().setBackgroundColor(getResources().getColor(R.color.white));
                ((TextView) bar.getView().findViewById(android.support.design.R.id.snackbar_text)).setTextColor(getResources().getColor(R.color.black));
                bar.show();

                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        try {
            APP.bluetoothSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
