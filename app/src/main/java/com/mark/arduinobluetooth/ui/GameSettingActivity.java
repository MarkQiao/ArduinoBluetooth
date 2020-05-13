package com.mark.arduinobluetooth.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mark.arduinobluetooth.BeasActivity;
import com.mark.arduinobluetooth.GlobalConstant;
import com.mark.arduinobluetooth.R;
import com.mark.arduinobluetooth.db.DBUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author wangqiao
 */
public class GameSettingActivity extends BeasActivity {


    @BindView(R.id.config_leftArrow)
    ImageView config_leftArrow;
    @BindView(R.id.config_upArrow)
    ImageView config_upArrow;
    @BindView(R.id.config_rightArrow)
    ImageView config_rightArrow;
    @BindView(R.id.config_downArrow)
    ImageView config_downArrow;
    @BindView(R.id.config_leftButton)
    ImageView config_leftButton;
    @BindView(R.id.config_upButton)
    ImageView config_upButton;
    @BindView(R.id.config_rightButton)
    ImageView config_rightButton;
    @BindView(R.id.config_downButton)
    ImageView config_downButton;

    @BindView(R.id.config_leftArrow_command)
    TextView configLeftArrowCommand;
    @BindView(R.id.config_leftArrow_commandEditText)
    EditText configLeftArrowCommandEditText;

    @BindView(R.id.config_upArrow_command)
    TextView configUpArrowCommand;
    @BindView(R.id.config_upArrow_commandEditText)
    EditText configUpArrowCommandEditText;

    @BindView(R.id.config_rightArrow_command)
    TextView configRightArrowCommand;
    @BindView(R.id.config_rightArrow_commandEditText)
    EditText configRightArrowCommandEditText;

    @BindView(R.id.config_downArrow_command)
    TextView configDownArrowCommand;
    @BindView(R.id.config_downArrow_commandEditText)
    EditText configDownArrowCommandEditText;

    @BindView(R.id.config_selectButton_command)
    TextView configSelectButtonCommand;
    @BindView(R.id.config_selectButton_commandEditText)
    EditText configSelectButtonCommandEditText;

    @BindView(R.id.config_startButton_command)
    TextView configStartButtonCommand;
    @BindView(R.id.config_startButton_commandEditText)
    EditText configStartButtonCommandEditText;

    @BindView(R.id.config_leftButton_command)
    TextView configLeftButtonCommand;
    @BindView(R.id.config_leftButton_commandEditText)
    EditText configLeftButtonCommandEditText;

    @BindView(R.id.config_upButton_command)
    TextView configUpButtonCommand;
    @BindView(R.id.config_upButton_commandEditText)
    EditText configUpButtonCommandEditText;

    @BindView(R.id.config_rightButton_command)
    TextView configRightButtonCommand;
    @BindView(R.id.config_rightButton_commandEditText)
    EditText configRightButtonCommandEditText;

    @BindView(R.id.config_downButton_command)
    TextView configDownButtonCommand;
    @BindView(R.id.config_downButton_commandEditText)
    EditText configDownButtonCommandEditText;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_game_setting;
    }

    @Override
    protected void initView() {
        //绑定处理
        ButterKnife.bind(this);
        setTitle("");
        config_leftArrow.setColorFilter(getResources().getColor(R.color.black));
        config_upArrow.setColorFilter(getResources().getColor(R.color.black));
        config_rightArrow.setColorFilter(getResources().getColor(R.color.black));
        config_downArrow.setColorFilter(getResources().getColor(R.color.black));

        config_leftButton.setColorFilter(getResources().getColor(R.color.arduinoOrange2));
        config_upButton.setColorFilter(getResources().getColor(R.color.arduinoBrown1));
        config_rightButton.setColorFilter(getResources().getColor(R.color.arduinoYellow2));
        config_downButton.setColorFilter(getResources().getColor(R.color.arduinoGray1));
        getViewValue();

    }

    //按钮点击事件处理
    @OnClick({R.id.config_leftArrow_command, R.id.config_upArrow_command, R.id.config_rightArrow_command, R.id.config_downArrow_command,
            R.id.config_selectButton_command, R.id.config_startButton_command,
            R.id.config_upButton_command, R.id.config_leftButton_command, R.id.config_rightButton_command, R.id.config_downButton_command})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.config_leftArrow_command:
                Operate(configLeftArrowCommand, configLeftArrowCommandEditText);
                break;
            case R.id.config_upArrow_command:
                Operate(configUpArrowCommand, configUpArrowCommandEditText);
                break;
            case R.id.config_rightArrow_command:
                Operate(configRightArrowCommand, configRightArrowCommandEditText);
                break;
            case R.id.config_downArrow_command:
                Operate(configDownArrowCommand, configDownArrowCommandEditText);
                break;
            case R.id.config_selectButton_command:
                Operate(configSelectButtonCommand, configSelectButtonCommandEditText);
                break;
            case R.id.config_startButton_command:
                Operate(configStartButtonCommand, configStartButtonCommandEditText);
                break;
            case R.id.config_upButton_command:
                Operate(configUpButtonCommand, configUpButtonCommandEditText);
                break;
            case R.id.config_leftButton_command:
                Operate(configLeftButtonCommand, configLeftButtonCommandEditText);
                break;
            case R.id.config_rightButton_command:
                Operate(configRightButtonCommand, configRightButtonCommandEditText);
                break;
            case R.id.config_downButton_command:
                Operate(configDownButtonCommand, configDownButtonCommandEditText);
                break;
            default:
                break;
        }
    }

    private void initEsitTextHide() {
        ShowOrHideView(configLeftArrowCommand, configLeftArrowCommandEditText, true);
        ShowOrHideView(configUpArrowCommand, configUpArrowCommandEditText, true);
        ShowOrHideView(configRightArrowCommand, configRightArrowCommandEditText, true);
        ShowOrHideView(configDownArrowCommand, configDownArrowCommandEditText, true);
        ShowOrHideView(configSelectButtonCommand, configSelectButtonCommandEditText, true);
        ShowOrHideView(configStartButtonCommand, configStartButtonCommandEditText, true);
        ShowOrHideView(configLeftButtonCommand, configLeftButtonCommandEditText, true);
        ShowOrHideView(configUpButtonCommand, configUpButtonCommandEditText, true);
        ShowOrHideView(configRightButtonCommand, configRightButtonCommandEditText, true);
        ShowOrHideView(configDownButtonCommand, configDownButtonCommandEditText, true);

    }

    @SuppressLint("ClickableViewAccessibility")
    private void Operate(TextView tv, EditText et) {
        initEsitTextHide();
        ShowOrHideView(tv, et, false);
        showSoftInputFromWindow(et);

        et.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_DONE) {
                tv.setText(et.getText().toString());
                ShowOrHideView(tv, et, true);
                setViewValue();
            }
            return false;
        });
    }

    private void getViewValue() {
        configLeftArrowCommand.setText(DBUtil.getValueBean(GlobalConstant.LeftArrow).getValue());
        configUpArrowCommand.setText(DBUtil.getValueBean(GlobalConstant.UpArrow).getValue());
        configRightArrowCommand.setText(DBUtil.getValueBean(GlobalConstant.RightArrow).getValue());
        configDownArrowCommand.setText(DBUtil.getValueBean(GlobalConstant.DownArrow).getValue());
        configSelectButtonCommand.setText(DBUtil.getValueBean(GlobalConstant.SelectButton).getValue());
        configStartButtonCommand.setText(DBUtil.getValueBean(GlobalConstant.StartButton).getValue());
        configLeftButtonCommand.setText(DBUtil.getValueBean(GlobalConstant.leftButton).getValue());
        configUpButtonCommand.setText(DBUtil.getValueBean(GlobalConstant.UpButton).getValue());
        configRightButtonCommand.setText(DBUtil.getValueBean(GlobalConstant.RightButton).getValue());
        configDownButtonCommand.setText(DBUtil.getValueBean(GlobalConstant.DownButton).getValue());
    }

    private void setViewValue() {
        DBUtil.saveValueBean(GlobalConstant.LeftArrow, configLeftArrowCommand.getText().toString());
        DBUtil.saveValueBean(GlobalConstant.UpArrow, configUpArrowCommand.getText().toString());
        DBUtil.saveValueBean(GlobalConstant.RightArrow, configRightArrowCommand.getText().toString());
        DBUtil.saveValueBean(GlobalConstant.DownArrow, configDownArrowCommand.getText().toString());

        DBUtil.saveValueBean(GlobalConstant.SelectButton, configSelectButtonCommand.getText().toString());
        DBUtil.saveValueBean(GlobalConstant.StartButton, configStartButtonCommand.getText().toString());

        DBUtil.saveValueBean(GlobalConstant.leftButton, configLeftButtonCommand.getText().toString());
        DBUtil.saveValueBean(GlobalConstant.UpButton, configUpButtonCommand.getText().toString());
        DBUtil.saveValueBean(GlobalConstant.RightButton, configRightButtonCommand.getText().toString());
        DBUtil.saveValueBean(GlobalConstant.DownButton, configDownButtonCommand.getText().toString());

    }

    /**
     * EditText获取焦点并显示软键盘
     */
    public void showSoftInputFromWindow(EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        //显示软键盘
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, 0);
    }

    private void ShowOrHideView(TextView tv, EditText et, boolean tvisshow) {
        tv.setVisibility(tvisshow ? View.VISIBLE : View.GONE);
        et.setVisibility(tvisshow ? View.GONE : View.VISIBLE);
    }

}