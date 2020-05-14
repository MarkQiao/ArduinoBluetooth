package com.mark.arduinobluetooth;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @ClassName: BeasActivity
 * @Description: BeasActivity java类作用描述
 * @Author: mr.Josh
 * @CreateDate: 2020/5/13 11:17 AM
 * @Version: 1.0
 */
public abstract class BeasActivity extends AppCompatActivity {
    private ActionBar actionBar;

    protected abstract int getLayoutId();

    protected abstract void initView();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        initView();
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
            default:
                break;
        }
        return true;
    }

    public void setTitle(String str) {
        if (actionBar != null) {
            actionBar.setTitle(str);
        }
    }
}
