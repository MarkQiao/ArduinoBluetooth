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

public class ViewThereFragment extends Fragment {


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
        for (int i = 0; i < 10; i++) {
            mData.add(new DraggableInfo(String.valueOf(i), 0, i, 1));
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
