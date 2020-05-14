package com.mark.arduinobluetooth.ui;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mark.arduinobluetooth.APP;
import com.mark.arduinobluetooth.R;
import com.mark.arduinobluetooth.db.DraggableDBUtil;
import com.mark.arduinobluetooth.db.DraggableInfoBean;
import com.mark.arduinobluetooth.service.SendSocketService;
import com.mark.arduinobluetooth.widget.DraggableButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.mark.arduinobluetooth.util.Utils.dp2px;

/**
 * @ClassName: ShowView
 * @Description: ShowView java类作用描述
 * @Author: mr.Josh
 * @CreateDate: 2020/4/24 6:08 PM
 * @Version: 1.0
 */
public class CustomLayoutActivity extends AppCompatActivity {
    List<DraggableInfoBean> list = new ArrayList<>();
    RelativeLayout viewPagerTab;

    int screenWidth;
    int screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getIntent().getStringExtra("DeviceName"));
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        setContentView(R.layout.activity_custom_layout);
        viewPagerTab = this.findViewById(R.id.rl_view);
        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        screenWidth = point.x;
        screenHeight = point.y;
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewPagerTab.removeAllViews();
        list.clear();
        List<DraggableInfoBean> dibList = DraggableDBUtil.getValueBean();
        list.addAll(dibList);
        updataView();
    }

    private void updataView() {
        for (int i = 0; i < list.size(); i++) {
            View v = LayoutInflater.from(CustomLayoutActivity.this).inflate(R.layout.item, null);
            DraggableButton sw = v.findViewById(R.id.bt);
            sw.setText(list.get(i).getText());
            sw.setImageResource(list.get(i).getPic());
            float viewx = (float) (list.get(i).getCenterX()* 1.3 - dp2px(30));
            float viewy = (float) (list.get(i).getCenterY() * 1.5 - dp2px(48)) - dp2px(12);
            v.setX(viewx);
            v.setY(viewy);
            final int finalI = i;
            v.setOnClickListener(v1 -> {
                Toast.makeText(CustomLayoutActivity.this, list.get(finalI).getText(), Toast.LENGTH_SHORT).show();
                SendSocketService.sendMessage("" + list.get(finalI).getText());
            });
            viewPagerTab.addView(v);
        }
    }

    /**
     * 复写：添加菜单布局
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_phone, menu);
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
                DraggableDBUtil.DelValue();
                Intent intent = new Intent(this, PhoneViewActivity.class);
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
}
