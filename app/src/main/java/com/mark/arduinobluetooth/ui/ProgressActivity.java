package com.mark.arduinobluetooth.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;
import android.widget.TextView;

import com.mark.arduinobluetooth.R;
import com.mark.arduinobluetooth.service.SendSocketService;

/**
 * @author wangqiao
 */
public class ProgressActivity extends AppCompatActivity {
    TextView tv_show_seek;
    SeekBar mSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        setTitle(getIntent().getStringExtra("DeviceName"));
        tv_show_seek = findViewById(R.id.tv_show_seek);
        mSeekBar = findViewById(R.id.seekBar2);

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
    }
}
