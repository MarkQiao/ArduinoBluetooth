package com.mark.arduinobluetooth.adapter;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mark.arduinobluetooth.R;

import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
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

    public void setData(List<BluetoothDevice> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
    @SuppressLint("MissingPermission")
    @Override
    public void onBindViewHolder(LoanDaquanAdapter.ViewHolder holder, final int position) {
        final BluetoothDevice mItemTypeBean = mList.get(position);
        if (mItemTypeBean.getName() == null) {
            holder.mAvailableDeviceTitle.setText("未知");
        } else {
            holder.mAvailableDeviceTitle.setText(mItemTypeBean.getName());
        }

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
                    mOnItemClickListener.onItemClick(mItemTypeBean);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return mOnItemClickListener.onLongClick(mItemTypeBean);
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
        void onItemClick(BluetoothDevice devices);

        boolean onLongClick(BluetoothDevice devices);
    }

}
