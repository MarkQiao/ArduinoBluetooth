package com.mark.arduinobluetooth.dialog;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.mark.arduinobluetooth.R;
import com.mark.arduinobluetooth.util.Utils;

/**
 * @author MarkQiao
 * @create 2019/3/25
 * @Describe
 */
public class showBottomDialog {
    Dialog mLoadingDialog;
    Context mcontext;

    public showBottomDialog(final Context context) {
        this.mcontext = context;
        // 获取视图
        mLoadingDialog = new Dialog(context, R.style.DialogTheme);
        //2、设置布局
        View view = View.inflate(context, R.layout.bottom_sheets_main, null);
        mLoadingDialog.setContentView(view);
        Window window = mLoadingDialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.BOTTOM);
        //设置弹出动画
        window.setWindowAnimations(R.style.main_menu_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mLoadingDialog.show();
        TextView versionName = view.findViewById(R.id.build_version_subtitle);
        Utils.getInstance();
        versionName.setText(Utils.getVersionName(context));
        mLoadingDialog.findViewById(R.id.share_item).setOnClickListener(view1 -> {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setType("text/plain"); //分享的是文本类型
            shareIntent.putExtra(Intent.EXTRA_TEXT, Utils.getAppName(context) + " " + Uri.parse(new StringBuilder().append("https://github.com/MarkQiao/ArduinoBluetooth.git").toString()).toString());//分享出去的内容
            mcontext.startActivity(Intent.createChooser(shareIntent, "Share"));
            close();
        });
        mLoadingDialog.findViewById(R.id.google_play_item).setOnClickListener(view1 -> {
            goToMarket(mcontext,mcontext.getPackageName());
            close();
        });

    }


    /**
     * 第一种方法
     * @param context
     * @param packageName
     */
    public static void goToMarket(Context context, String packageName) {
        try {
            Uri uri = Uri.parse("market://details?id=" + packageName);
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(context, "您的手机没有安装Android应用市场", Toast.LENGTH_SHORT).show();
        }
    }

    public void show() {
        mLoadingDialog.show();
    }

    public void close() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }
}
