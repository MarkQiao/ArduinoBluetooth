package com.mark.arduinobluetooth.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.mark.arduinobluetooth.R;

/**
 * @ClassName: TypeDialog
 * @Description: TypeDialog java类作用描述
 * @CreateDate: 2020/3/27 6:46 PM
 * @Version: 1.0
 */
public class TypeDialog extends Dialog {
  public TypeDialog(@NonNull Context context) {
    super(context, R.style.CustomDialog);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.dialog_type_layout);
    //按空白处不能取消动画
    setCanceledOnTouchOutside(true);
    //初始化界面控件
    initView();
//    getWindow().getDecorView().setPadding(0, 0, 0, 0);
    //初始化界面数据
    //    refreshView();
    //初始化界面控件的事件
    //    initEvent();
  }

  /**
   * 初始化界面控件
   */
  private void initView() {
//    negtiveBn = (Button) findViewById(R.id.negtive);
    //    positiveBn = (Button) findViewById(R.id.positive);
    //    titleTv = (TextView) findViewById(R.id.title);
    //    messageTv = (TextView) findViewById(R.id.message);
    //    imageIv = (ImageView) findViewById(R.id.image);
    //    columnLineView = findViewById(R.id.column_line);
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
    public void onNegtiveClick(int type);
  }

}
