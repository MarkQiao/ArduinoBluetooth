package com.mark.arduinobluetooth.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mark.arduinobluetooth.R;
import com.mark.arduinobluetooth.adapter.ViewAdapter;
import com.mark.arduinobluetooth.bean.DraggableInfo;
import com.mark.arduinobluetooth.ui.PhoneViewActivity;
import com.mark.arduinobluetooth.util.Tools;

import java.util.ArrayList;
import java.util.List;

public class ViewTwoFragment extends Fragment {

    private int[] pic2 = new int[]{R.mipmap.offered_play, R.mipmap.offered_stop, R.mipmap.offered_pause,
            R.mipmap.offered_pause2, R.mipmap.offered_previous, R.mipmap.offered_next, R.mipmap.offered_backward,
            R.mipmap.offered_forward, R.mipmap.offered_height, R.mipmap.offered_width};

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
        for (int i = 0; i < pic2.length; i++) {
            mData.add(new DraggableInfo("", pic2[i], i, 0));
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
