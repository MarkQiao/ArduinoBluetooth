package com.mark.arduinobluetooth.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

import com.mark.arduinobluetooth.APP;
import com.mark.arduinobluetooth.R;
import com.mark.arduinobluetooth.service.SendSocketService;

import java.io.IOException;

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

        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getIntent().getStringExtra("DeviceName"));
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

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




    /**
     * 复写：设置菜单监听s
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //actionbar navigation up 按钮
            case android.R.id.home:
                finish();
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
}
