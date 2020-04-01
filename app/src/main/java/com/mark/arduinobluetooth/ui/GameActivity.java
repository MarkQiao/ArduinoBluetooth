package com.mark.arduinobluetooth.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mark.arduinobluetooth.APP;
import com.mark.arduinobluetooth.R;
import com.mark.arduinobluetooth.controls.ArrowButtonControl;
import com.mark.arduinobluetooth.controls.BEnum;
import com.mark.arduinobluetooth.controls.ControllerButtonControl;
import com.mark.arduinobluetooth.service.SendSocketService;

import java.io.IOException;

/**
 * @author wangqiao
 */
public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    ArrowButtonControl controller_arrowLeft, controller_arrowTop, controller_arrowBottom, controller_arrowRight;
    ControllerButtonControl controller_buttonLeft, controller_buttonBottom, controller_buttonTop, controller_buttonRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getIntent().getStringExtra("DeviceName"));
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

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
        try {
            APP.bluetoothSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.controller_arrowTop:
                SendSocketService.sendMessage("up");
                break;
            case R.id.controller_arrowBottom:
                SendSocketService.sendMessage("Bottom");
                break;
            case R.id.controller_arrowLeft:
                SendSocketService.sendMessage("Left");
                break;
            case R.id.controller_arrowRight:
                SendSocketService.sendMessage("Right");
                break;
            case R.id.controller_buttonTop:
                SendSocketService.sendMessage("buttonTop");
                break;
            case R.id.controller_buttonBottom:
                SendSocketService.sendMessage("buttonBottom");
                break;
            case R.id.controller_buttonLeft:
                SendSocketService.sendMessage("buttonLeft");
                break;
            case R.id.controller_buttonRight:
                SendSocketService.sendMessage("buttonRight");
                break;
            default:
                break;
        }
    }
}
