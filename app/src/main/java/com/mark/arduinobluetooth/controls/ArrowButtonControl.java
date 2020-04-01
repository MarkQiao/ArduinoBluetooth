package com.mark.arduinobluetooth.controls;

/**
 * @ClassName: ArrowButtonControl
 * @Description: ArrowButtonControl java类作用描述
 * @Author: mr.Josh
 * @CreateDate: 2020/4/1 11:53 AM
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

public class ArrowButtonControl
        extends RelativeLayout implements View.OnTouchListener {
    private ImageView LeftsectionButton;
    private ImageView LeftsectionSymbol;
    private BEnum c;
    private int d;
    private int e;
    private int f;
    private int g;

    public ArrowButtonControl(Context paramContext) {
        super(paramContext);
        init();
    }

    public ArrowButtonControl(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        init();
    }

    public ArrowButtonControl(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
        init();
    }

    private void init() {
        View localView = LayoutInflater.from(getContext()).inflate(R.layout.control_arrow_controller, this, true);
        LeftsectionButton = ((ImageView) localView.findViewById(R.id.controller_leftsection_button));
        LeftsectionSymbol = ((ImageView) localView.findViewById(R.id.controller_leftsection_symbol));
        LeftsectionButton.setOnTouchListener(this);
    }

    @Override
    public void setBackgroundColor(int paramInt) {
        this.d = paramInt;
        LeftsectionButton.setColorFilter(paramInt);
    }

    public void setBackgroundPressedColor(int paramInt) {
        this.e = paramInt;
    }

    public void setForegroundColor(int paramInt) {
        this.f = paramInt;
        LeftsectionSymbol.setColorFilter(paramInt);
    }

    public void setForegroundPressedColor(int paramInt) {
        this.g = paramInt;
    }

    public void setSymbol(BEnum paramb) {
        this.c = paramb;
        if (paramb == BEnum.up) {
            LeftsectionButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_label_black_48dp_up));
            LeftsectionSymbol.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand_less_white_24dp));
            return;
        }
        if (paramb == BEnum.down) {
            LeftsectionButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_label_black_48dp_down));
            LeftsectionSymbol.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand_more_white_24dp));
            return;
        }
        if (paramb == BEnum.left) {
            LeftsectionButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_label_black_48dp_left));
            LeftsectionSymbol.setImageDrawable(getResources().getDrawable(R.drawable.ic_chevron_left_white_24dp));
            return;
        }
        LeftsectionButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_label_black_48dp_right));
        LeftsectionSymbol.setImageDrawable(getResources().getDrawable(R.drawable.ic_chevron_right_white_24dp));
    }

    @Override
    public boolean onTouch(View paramView, MotionEvent paramMotionEvent) {
        if (paramMotionEvent.getAction() == 0) {
            LeftsectionButton.setColorFilter(e);
            LeftsectionSymbol.setColorFilter(g);
            return true;
        }
        if (paramMotionEvent.getAction() == 1) {
            LeftsectionButton.setColorFilter(d);
            LeftsectionSymbol.setColorFilter(f);
            this.callOnClick();
            return true;
        }
        if (paramMotionEvent.getAction() == 2) {
        }
        return false;
    }
}
