package com.mark.arduinobluetooth.controls;

/**
 * @ClassName: ControllerButtonControl
 * @Description: ControllerButtonControl java类作用描述
 * @Author: mr.Josh
 * @CreateDate: 2020/4/1 10:33 AM
 * @Version: 1.0
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mark.arduinobluetooth.R;

public class ControllerButtonControl extends RelativeLayout implements View.OnTouchListener {
    private ImageView RightsectionButton;
    private ImageView RightsectionSymbol;
    private BEnum c;
    private int d;
    private int e;
    private int f;
    private int g;

    public ControllerButtonControl(Context paramContext) {
        super(paramContext);
        init();
    }

    public ControllerButtonControl(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        init();
    }

    public ControllerButtonControl(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
        init();
    }

    private void init() {
        View localView = LayoutInflater.from(getContext()).inflate(R.layout.control_button_controller, this, true);
        RightsectionButton = localView.findViewById(R.id.controller_rightsection_button);
        RightsectionSymbol = localView.findViewById(R.id.controller_rightsection_symbol);
        RightsectionButton.setOnTouchListener(this);
    }

    @Override
    public void setBackgroundColor(int paramInt) {
        this.d = paramInt;
        RightsectionButton.setColorFilter(paramInt);
    }

    public void setBackgroundPressedColor(int paramInt) {
        this.e = paramInt;
    }

    public void setForegroundColor(int paramInt) {
        this.f = paramInt;
        RightsectionSymbol.setColorFilter(paramInt);
    }

    public void setForegroundPressedColor(int paramInt) {
        this.g = paramInt;
    }

    public void setSymbol(BEnum paramd) {
        this.c = paramd;
        if (paramd == BEnum.up) {
            RightsectionSymbol.setImageDrawable(getResources().getDrawable(R.drawable.ic_change_history_white_24dp));
            return;
        }
        if (paramd == BEnum.down) {
            RightsectionSymbol.setImageDrawable(getResources().getDrawable(R.drawable.ic_panorama_fish_eye_white_24dp));
            return;
        }
        if (paramd == BEnum.left) {
            RightsectionSymbol.setImageDrawable(getResources().getDrawable(R.drawable.ic_crop_din_white_24dp));
            return;
        }
        RightsectionSymbol.setImageDrawable(getResources().getDrawable(R.drawable.ic_clear_white_24dp));
    }

    @Override
    public boolean onTouch(View paramView, MotionEvent paramMotionEvent) {
        if (paramMotionEvent.getAction() == 0) {
            RightsectionButton.setColorFilter(e);
            RightsectionSymbol.setColorFilter(g);
            return true;
        }
        if (paramMotionEvent.getAction() == 1) {
            RightsectionButton.setColorFilter(d);
            RightsectionSymbol.setColorFilter(f);
            this.callOnClick();
            return true;
        }
        if (paramMotionEvent.getAction() == 2) {
        }
        return false;
    }

}
