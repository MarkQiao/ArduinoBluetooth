package com.mark.arduinobluetooth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.mark.arduinobluetooth.R;
import com.mark.arduinobluetooth.bean.DraggableInfo;
import com.mark.arduinobluetooth.widget.DraggableButton;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @ClassName: ViewAdapter
 * @Description: ViewAdapter java类作用描述
 * @Author: mr.Josh
 * @CreateDate: 2020/5/12 9:41 PM
 * @Version: 1.0
 */
public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.ViewHolder> {

    private Context mContext;
    private List<DraggableInfo> mData = new ArrayList<>();

    public ViewAdapter(Context mContext, List<DraggableInfo> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private DraggableButton mButton;

        ViewHolder(View itemView) {
            super(itemView);
            mButton = itemView.findViewById(R.id.bt);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewAdapter.ViewHolder holder, final int position) {
        if (mData.get(position).getType() == 1) {
            holder.mButton.setText(mData.get(position).getText());
        } else {
            holder.mButton.setImageResource(mData.get(position).getPic());
        }

        holder.itemView.setOnLongClickListener(v -> {
            mOnItemLongClickListener.onItemLongClick(null, v, position, position);

            return false;
        });
    }

    public DraggableInfo getItem(int position) {
        return mData.get(Math.max(0, position));
    }

    private AdapterView.OnItemLongClickListener mOnItemLongClickListener;

    public void setOnItemLongClickListener(AdapterView.OnItemLongClickListener listener) {
        this.mOnItemLongClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
