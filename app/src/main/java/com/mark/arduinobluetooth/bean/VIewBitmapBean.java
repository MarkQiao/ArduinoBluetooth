package com.mark.arduinobluetooth.bean;

import android.graphics.Rect;

import java.io.Serializable;

/**
 * @ClassName: VIewBean
 * @Description: VIewBean java类作用描述
 * @Author: mr.Josh
 * @CreateDate: 2020/4/24 5:35 PM
 * @Version: 1.0
 */
public class VIewBitmapBean  implements Serializable {
     private Rect mRectB;

    public VIewBitmapBean(Rect mRectB, DraggableInfo ml) {
        this.mRectB = mRectB;
        this.ml = ml;
    }

    public Rect getmRectB() {
        return mRectB;
    }

    public void setmRectB(Rect mRectB) {
        this.mRectB = mRectB;
    }

    public DraggableInfo getMl() {
        return ml;
    }

    public void setMl(DraggableInfo ml) {
        this.ml = ml;
    }

    private DraggableInfo ml;
}
