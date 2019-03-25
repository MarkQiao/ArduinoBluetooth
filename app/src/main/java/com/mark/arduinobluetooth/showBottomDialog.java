package com.mark.arduinobluetooth;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

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
        TextView versionName= view.findViewById(R.id.build_version_subtitle);
        versionName.setText(Utils.getInstance().getVersionName(context));
        mLoadingDialog.findViewById(R.id.share_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.setType("text/plain"); //分享的是文本类型
                shareIntent.putExtra(Intent.EXTRA_TEXT, Utils.getInstance().getAppName(context)+" " + Uri.parse(new StringBuilder().append("https://github.com/MarkQiao/ArduinoBluetooth.git").toString()).toString());//分享出去的内容
                mcontext.startActivity( Intent.createChooser(shareIntent,"Share"));
                mLoadingDialog.dismiss();
            }
        });
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