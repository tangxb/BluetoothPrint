package com.yq.tools;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yq.model.BluethBean;

import java.util.List;

public class MyListAdapter extends BaseAdapter {
    private Context mContext;
    private List<BluethBean> mDevices;
    private BluetoothDevice device;

    public MyListAdapter(Context context, List<BluethBean> devices) {
        mContext = context;
        mDevices = devices;
    }

    @Override
    public int getCount() {
        if (mDevices == null) {
            return 0;
        }
        return mDevices.size();
    }

    @Override
    public Object getItem(int position) {

        return null;
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv_device = new TextView(mContext);
        tv_device.setTextSize(16);
        tv_device.setPadding(8, 15, 8, 15);
        device = mDevices.get(position).getBluetoothDevice();
        tv_device.setText(device.getName() + "（" + device.getAddress() + "）");
        return tv_device;
    }

}
