package com.mark.arduinobluetooth.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mark.arduinobluetooth.R;

/**
 * @ClassName: TypeDialog
 * @Description: TypeDialog java类作用描述
 * @CreateDate: 2020/3/27 6:46 PM
 * @Version: 1.0
 */
public class TypeDialog extends Dialog {

    ImageView controllerImage, switchImage, dimmerImage, terminalImage;
    RelativeLayout controller, switchOnOff, dimmer, terminal;
    Context mContext;
    private Animation fabOpen;

    public TypeDialog(@NonNull Context context) {
        super(context, R.style.CustomDialog);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_connection_mode);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(true);
        //初始化界面控件
        initView();
        //初始化界面控件的事件
        initEvent();
    }

    /**
     * 初始化界面控件
     */
    private void initView() {
        fabOpen = AnimationUtils.loadAnimation(mContext, R.anim.fab_open);
        controllerImage = findViewById(R.id.controllerImage);
        switchImage = findViewById(R.id.switchImage);
        dimmerImage = findViewById(R.id.dimmerImage);
        terminalImage = findViewById(R.id.terminalImage);

        controller = findViewById(R.id.controller);
        switchOnOff = findViewById(R.id.switchOnOff);
        dimmer = findViewById(R.id.dimmer);
        terminal = findViewById(R.id.terminal);

        controllerImage.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(mContext, R.color.tomato)));
        switchImage.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(mContext, R.color.tomato)));
        dimmerImage.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(mContext, R.color.tomato)));
        terminalImage.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(mContext, R.color.tomato)));
        controllerImage.setAnimation(fabOpen);
        switchImage.setAnimation(fabOpen);
        dimmerImage.setAnimation(fabOpen);
        terminalImage.setAnimation(fabOpen);
    }

    private void initEvent() {
        controller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBottomListener.onNegtiveClick(0);
            }
        });
        switchOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBottomListener.onNegtiveClick(1);
            }
        });
        dimmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBottomListener.onNegtiveClick(2);
            }
        });
        terminal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBottomListener.onNegtiveClick(3);
            }
        });
    }

    /**
     * 设置确定取消按钮的回调
     */
    public OnClickBottomListener onClickBottomListener;

    public void setOnClickBottomListener(OnClickBottomListener onClickBottomListener) {
        this.onClickBottomListener = onClickBottomListener;
    }

    public interface OnClickBottomListener {
        /**
         * 点击取消按钮事件
         */
        void onNegtiveClick(int type);
    }

}
