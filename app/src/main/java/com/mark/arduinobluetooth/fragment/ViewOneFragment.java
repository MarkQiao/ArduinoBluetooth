package com.mark.arduinobluetooth.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mark.arduinobluetooth.R;
import com.mark.arduinobluetooth.adapter.ViewAdapter;
import com.mark.arduinobluetooth.bean.DraggableInfo;
import com.mark.arduinobluetooth.ui.PhoneViewActivity;
import com.mark.arduinobluetooth.util.Tools;


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ViewOneFragment extends Fragment {

    private int[] pic1 = new int[]{R.mipmap.svg_new_close, R.mipmap.svg_new_home, R.mipmap.offered_exit,
            R.mipmap.svg_new_back, R.mipmap.svg_new_setting, R.mipmap.svg_new_source, 0,
            R.mipmap.offered_menu, R.mipmap.offered_out, R.mipmap.offered_mute};


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView mRecyclerView = view.findViewById(R.id.rv);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 5));
        mRecyclerView.setHasFixedSize(true);

        final List<DraggableInfo> mData = new ArrayList<>();
        for (int i = 0; i < pic1.length; i++) {
            if (pic1[i] == 0) {
                mData.add(new DraggableInfo("Text", 0, i, 1));
            } else {
                mData.add(new DraggableInfo("", pic1[i], i, 0));
            }
        }

        final ViewAdapter mAdapter = new ViewAdapter(getActivity(), mData);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemLongClickListener((parent, view1, position1, id) -> {
            view1.setTag(mAdapter.getItem(position1));
            Tools.startDrag(view1);
            ((PhoneViewActivity) getActivity()).setDragInfo(mAdapter.getItem(position1));
            return false;
        });
    }

}
