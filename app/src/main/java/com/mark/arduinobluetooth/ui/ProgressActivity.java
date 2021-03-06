package com.mark.arduinobluetooth.ui;

import android.graphics.Color;
import android.os.Handler;
import android.widget.SeekBar;
import android.widget.TextView;

import com.mark.arduinobluetooth.APP;
import com.mark.arduinobluetooth.BeasActivity;
import com.mark.arduinobluetooth.R;
import com.mark.arduinobluetooth.controls.AnalogController;
import com.mark.arduinobluetooth.service.ReceiveSocketService;
import com.mark.arduinobluetooth.service.SendSocketService;

import java.io.IOException;

import static com.mark.arduinobluetooth.util.factory.ThreadPoolProxyFactory.getNormalThreadPoolProxy;

/**
 * @author wangqiao
 */
public class ProgressActivity extends BeasActivity {
    TextView tv_show_seek;
    SeekBar mSeekBar;
    AnalogController bassController;
    int themeColors = Color.parseColor("#F08080");
    private int exitTime = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_progress;
    }

    @Override
    protected void initView() {
        setTitle(getIntent().getStringExtra("DeviceName"));

        tv_show_seek = findViewById(R.id.tv_show_seek);
        mSeekBar = findViewById(R.id.seekBar2);
        bassController = findViewById(R.id.controllerBass);
        bassController.setLabel("音量调节");
        bassController.circlePaint2.setColor(themeColors);
        bassController.linePaint.setColor(themeColors);
        bassController.invalidate();
        bassController.linePaint.setColor(themeColors);
        bassController.setOnProgressChangedListener(progress -> {
            if (exitTime != progress) {
                exitTime = progress;
                tv_show_seek.setText("" + progress);
                SendSocketService.sendMessage("" + progress);
            }
        });
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_show_seek.setText("" + progress);
                SendSocketService.sendMessage("" + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        //开启消息接收端
        getNormalThreadPoolProxy().execute(() -> new ReceiveSocketService().receiveMessage(str -> new Handler(getMainLooper()).post(() -> {
            try {
                mSeekBar.setProgress(Integer.parseInt(str));
            } catch (Exception e) {

            }

        })));

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
}
