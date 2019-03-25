package com.mark.arduinobluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author MarkQiao
 * @create 2019/2/16
 * @Describe
 */
public class LoanDaquanAdapter extends RecyclerView.Adapter<LoanDaquanAdapter.ViewHolder> {

    private List<BluetoothDevice> mList;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public enum ViewName {ITEM, ITEM_NAME}

    public LoanDaquanAdapter(List<BluetoothDevice> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public LoanDaquanAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_available_device, parent, false);
        return new LoanDaquanAdapter.ViewHolder(view);
    }
    public void setData(List<BluetoothDevice> list){
        this.mList=list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(LoanDaquanAdapter.ViewHolder holder, final int position) {
        BluetoothDevice mItemTypeBean = mList.get(position);
        holder.mAvailableDeviceTitle.setText(mItemTypeBean.getName());
        holder.mAvailableDeviceSubtitle.setText(mItemTypeBean.getAddress());

        if (256 == mItemTypeBean.getBluetoothClass().getMajorDeviceClass()) {
            holder.mItemImg.setImageResource(R.drawable.ic_laptop_black_24dp);
        } else if (1024 == mItemTypeBean.getBluetoothClass().getMajorDeviceClass()) {
            holder.mItemImg.setImageResource(R.drawable.ic_devices_headset);
        } else if (7936 == mItemTypeBean.getBluetoothClass().getMajorDeviceClass()) {
            holder.mItemImg.setImageResource(R.drawable.ic_devices_other_black_24dp);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, ViewName.ITEM_NAME, position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.deviceIcon)
        ImageView mItemImg;
        @BindView(R.id.availableDeviceTitle)
        TextView mAvailableDeviceTitle;
        @BindView(R.id.availableDeviceSubtitle)
        TextView mAvailableDeviceSubtitle;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


    }

    // 自定义点击事件
    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, ViewName viewName, int position);

//    void onItemLongClick(View view, int position);
    }
}