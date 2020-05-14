package com.mark.arduinobluetooth.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @ClassName: PhoneViewAdapter
 * @Description: PhoneViewAdapter java类作用描述
 * @Author: mr.Josh
 * @CreateDate: 2020/5/13 9:35 AM
 * @Version: 1.0
 */
public class PhoneViewAdapter extends FragmentPagerAdapter {

    private List<Fragment> list;

    public PhoneViewAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
