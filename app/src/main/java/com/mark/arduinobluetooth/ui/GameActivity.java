package com.mark.arduinobluetooth.ui;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.mark.arduinobluetooth.APP;
import com.mark.arduinobluetooth.GlobalConstant;
import com.mark.arduinobluetooth.R;
import com.mark.arduinobluetooth.controls.ArrowButtonControl;
import com.mark.arduinobluetooth.controls.BEnum;
import com.mark.arduinobluetooth.controls.ControllerButtonControl;
import com.mark.arduinobluetooth.db.DBUtil;
import com.mark.arduinobluetooth.service.SendSocketService;
import com.mark.arduinobluetooth.util.MessageWrap;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;

/**
 * @author wangqiao
 */
public class GameActivity extends AppCompatActivity implements View.OnClickListener {


    ArrowButtonControl controller_arrowLeft, controller_arrowTop, controller_arrowBottom, controller_arrowRight;
    ControllerButtonControl controller_buttonLeft, controller_buttonBottom, controller_buttonTop, controller_buttonRight;
    Button centerButton1, centerButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //绑定处理
        ButterKnife.bind(this);
        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getIntent().getStringExtra("DeviceName"));
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        EventBus.getDefault().register(this);
        centerButton1 = findViewById(R.id.centerButton1);
        centerButton2 = findViewById(R.id.centerButton2);

        controller_arrowLeft = findViewById(R.id.controller_arrowLeft);
        controller_arrowTop = findViewById(R.id.controller_arrowTop);
        controller_arrowBottom = findViewById(R.id.controller_arrowBottom);
        controller_arrowRight = findViewById(R.id.controller_arrowRight);


        controller_arrowLeft.setSymbol(BEnum.left);
        controller_arrowTop.setSymbol(BEnum.up);
        controller_arrowBottom.setSymbol(BEnum.down);
        controller_arrowRight.setSymbol(BEnum.right);


        controller_arrowLeft.setBackgroundColor(getResources().getColor(R.color.joystick_darker_background));
        controller_arrowLeft.setBackgroundPressedColor(getResources().getColor(R.color.joystick_darker_pressed_background));
        controller_arrowLeft.setForegroundColor(getResources().getColor(R.color.black));
        controller_arrowLeft.setForegroundPressedColor(getResources().getColor(R.color.white));

        controller_arrowTop.setBackgroundColor(getResources().getColor(R.color.joystick_darker_background));
        controller_arrowTop.setBackgroundPressedColor(getResources().getColor(R.color.joystick_darker_pressed_background));
        controller_arrowTop.setForegroundColor(getResources().getColor(R.color.black));
        controller_arrowTop.setForegroundPressedColor(getResources().getColor(R.color.white));

        controller_arrowBottom.setBackgroundColor(getResources().getColor(R.color.joystick_darker_background));
        controller_arrowBottom.setBackgroundPressedColor(getResources().getColor(R.color.joystick_darker_pressed_background));
        controller_arrowBottom.setForegroundColor(getResources().getColor(R.color.black));
        controller_arrowBottom.setForegroundPressedColor(getResources().getColor(R.color.white));

        controller_arrowRight.setBackgroundColor(getResources().getColor(R.color.joystick_darker_background));
        controller_arrowRight.setBackgroundPressedColor(getResources().getColor(R.color.joystick_darker_pressed_background));
        controller_arrowRight.setForegroundColor(getResources().getColor(R.color.black));
        controller_arrowRight.setForegroundPressedColor(getResources().getColor(R.color.white));

        controller_buttonLeft = findViewById(R.id.controller_buttonLeft);
        controller_buttonBottom = findViewById(R.id.controller_buttonBottom);
        controller_buttonTop = findViewById(R.id.controller_buttonTop);
        controller_buttonRight = findViewById(R.id.controller_buttonRight);

        controller_buttonLeft.setSymbol(BEnum.left);
        controller_buttonTop.setSymbol(BEnum.up);
        controller_buttonBottom.setSymbol(BEnum.down);
        controller_buttonRight.setSymbol(BEnum.right);

        controller_buttonLeft.setBackgroundColor(getResources().getColor(R.color.joystick_background));
        controller_buttonLeft.setBackgroundPressedColor(getResources().getColor(R.color.joystick_pressed_background));
        controller_buttonLeft.setForegroundColor(getResources().getColor(R.color.arduinoOrange2));
        controller_buttonLeft.setForegroundPressedColor(getResources().getColor(R.color.white));

        controller_buttonTop.setBackgroundColor(getResources().getColor(R.color.joystick_background));
        controller_buttonTop.setBackgroundPressedColor(getResources().getColor(R.color.joystick_pressed_background));
        controller_buttonTop.setForegroundColor(getResources().getColor(R.color.arduinoBrown1));
        controller_buttonTop.setForegroundPressedColor(getResources().getColor(R.color.white));

        controller_buttonBottom.setBackgroundColor(getResources().getColor(R.color.joystick_background));
        controller_buttonBottom.setBackgroundPressedColor(getResources().getColor(R.color.joystick_pressed_background));
        controller_buttonBottom.setForegroundColor(getResources().getColor(R.color.arduinoGray1));
        controller_buttonBottom.setForegroundPressedColor(getResources().getColor(R.color.white));

        controller_buttonRight.setBackgroundColor(getResources().getColor(R.color.joystick_background));
        controller_buttonRight.setBackgroundPressedColor(getResources().getColor(R.color.joystick_pressed_background));
        controller_buttonRight.setForegroundColor(getResources().getColor(R.color.arduinoYellow2));
        controller_buttonRight.setForegroundPressedColor(getResources().getColor(R.color.white));

        controller_arrowLeft.setOnClickListener(this);
        controller_arrowTop.setOnClickListener(this);
        controller_arrowBottom.setOnClickListener(this);
        controller_arrowRight.setOnClickListener(this);

        controller_buttonLeft.setOnClickListener(this);
        controller_buttonTop.setOnClickListener(this);
        controller_buttonBottom.setOnClickListener(this);
        controller_buttonRight.setOnClickListener(this);

        centerButton1.setOnClickListener(this);
        centerButton2.setOnClickListener(this);
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
                Intent intent = new Intent(this, GameSettingActivity.class);
                this.startActivity(intent);
                break;
            default:
                break;
        }
        return true;
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMessage(MessageWrap message) {
        switch (message.type) {
            case BluetoothDevice.ACTION_ACL_DISCONNECTED:
                Snackbar bar = Snackbar.make(controller_arrowBottom, message.message, Snackbar.LENGTH_INDEFINITE)
                        .setAction("ok", v -> finish());
                bar.getView().setBackgroundColor(getResources().getColor(R.color.white));
                ((TextView)bar.getView().findViewById(R.id.snackbar_text)).setTextColor(Color.BLACK);
                bar.show();
                break;

            case "1241":
                Snackbar bars = Snackbar.make(controller_arrowBottom, message.message, Snackbar.LENGTH_INDEFINITE)
                        .setAction("ok", v ->
                                this.startActivity(new Intent(this, GameSettingActivity.class)));
                bars.getView().setBackgroundColor(getResources().getColor(R.color.white));
                ((TextView)bars.getView().findViewById(R.id.snackbar_text)).setTextColor(Color.BLACK);
                bars.show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.controller_arrowTop:
                SendSocketService.sendMessage(DBUtil.getValueBean(GlobalConstant.UpArrow).getValue());
                break;
            case R.id.controller_arrowBottom:
                SendSocketService.sendMessage(DBUtil.getValueBean(GlobalConstant.DownArrow).getValue());
                break;
            case R.id.controller_arrowLeft:
                SendSocketService.sendMessage(DBUtil.getValueBean(GlobalConstant.LeftArrow).getValue());
                break;
            case R.id.controller_arrowRight:
                SendSocketService.sendMessage(DBUtil.getValueBean(GlobalConstant.RightArrow).getValue());
                break;
            case R.id.centerButton1:
                SendSocketService.sendMessage(DBUtil.getValueBean(GlobalConstant.SelectButton).getValue());
                break;
            case R.id.centerButton2:
                SendSocketService.sendMessage(DBUtil.getValueBean(GlobalConstant.StartButton).getValue());
                break;
            case R.id.controller_buttonTop:
                SendSocketService.sendMessage(DBUtil.getValueBean(GlobalConstant.UpButton).getValue());
                break;
            case R.id.controller_buttonBottom:
                SendSocketService.sendMessage(DBUtil.getValueBean(GlobalConstant.DownButton).getValue());
                break;
            case R.id.controller_buttonLeft:
                SendSocketService.sendMessage(DBUtil.getValueBean(GlobalConstant.leftButton).getValue());
                break;
            case R.id.controller_buttonRight:
                SendSocketService.sendMessage(DBUtil.getValueBean(GlobalConstant.RightButton).getValue());
                break;
            default:
                break;
        }
    }
}
