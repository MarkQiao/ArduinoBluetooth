package com.mark.arduinobluetooth.ui;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.mark.arduinobluetooth.BeasActivity;
import com.mark.arduinobluetooth.R;
import com.mark.arduinobluetooth.adapter.PhoneViewAdapter;
import com.mark.arduinobluetooth.bean.DraggableInfo;
import com.mark.arduinobluetooth.bean.VIewBitmapBean;
import com.mark.arduinobluetooth.db.DraggableDBUtil;
import com.mark.arduinobluetooth.db.DraggableInfoBean;
import com.mark.arduinobluetooth.fragment.ViewOneFragment;
import com.mark.arduinobluetooth.fragment.ViewThereFragment;
import com.mark.arduinobluetooth.fragment.ViewTwoFragment;
import com.mark.arduinobluetooth.widget.RemoteControlView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 */
public class PhoneViewActivity extends BeasActivity {

    private RemoteControlView remoteControlView;
    private List<Fragment> views;
    private ImageView IvOne, IvTwo, IvThere;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_phone_view;
    }

    @Override
    protected void initView() {
        setTitle("自定义按键");
        ViewPager viewPager = this.findViewById(R.id.viewpager);
        IvOne = this.findViewById(R.id.iv_one);
        IvTwo = this.findViewById(R.id.iv_two);
        IvThere = this.findViewById(R.id.iv_there);

        //得到view数据
        views = new ArrayList<>();
        views.add(new ViewOneFragment());
        views.add(new ViewTwoFragment());
        views.add(new ViewThereFragment());
        PhoneViewAdapter adapter = new PhoneViewAdapter(getSupportFragmentManager(), views);
        viewPager.setAdapter(adapter);
        remoteControlView = this.findViewById(R.id.rcv);
        setDot(true, false, false);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            //当页面滑动时
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            //当页面选中时
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        setDot(true, false, false);
                        break;
                    case 1:
                        setDot(false, true, false);
                        break;
                    case 2:
                        setDot(false, false, true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    //小点的变化方法，为true时背景设亮，为false时背景设暗
    private void setDot(boolean a, boolean b, boolean c) {
        if (a) {
            IvOne.setBackgroundResource(R.drawable.circle_blue);
        } else {
            IvOne.setBackgroundResource(R.drawable.circle_gray);
        }
        if (b) {
            IvTwo.setBackgroundResource(R.drawable.circle_blue);
        } else {
            IvTwo.setBackgroundResource(R.drawable.circle_gray);
        }
        if (c) {
            IvThere.setBackgroundResource(R.drawable.circle_blue);
        } else {
            IvThere.setBackgroundResource(R.drawable.circle_gray);
        }
    }


    public void setDragInfo(DraggableInfo mButton) {
        remoteControlView.setDragInfo(mButton);
    }

    public void isok(View view) {
        List<VIewBitmapBean> rec = remoteControlView.getmRectList();

        for (VIewBitmapBean vbb : rec) {
            Log.d("--------->", "-------rec.size()----->" + rec.size());
            DraggableInfoBean dib = new DraggableInfoBean();
            dib.setText(vbb.getMl().getText());
            dib.setPic(vbb.getMl().getPic());
            dib.setType(vbb.getMl().getType());
            dib.setId(vbb.getMl().getId());
            Log.d("--------->", "-------vbb.getmRectB().centerX()----->" + vbb.getmRectB().centerX());
            Log.d("--------->", "-------vbb.getmRectB().centerY()----->" + vbb.getmRectB().centerY());
            dib.setCenterX(vbb.getmRectB().centerX());
            dib.setCenterY(vbb.getmRectB().centerY());
            dib.setLeft(vbb.getmRectB().left);
            dib.setTop(vbb.getmRectB().top);
            dib.setRight(vbb.getmRectB().right);
            dib.setBottom(vbb.getmRectB().bottom);
            DraggableDBUtil.save(dib);
        }

        finish();
    }
}
