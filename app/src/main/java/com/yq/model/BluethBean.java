package com.yq.model;

import android.bluetooth.BluetoothDevice;

/**
 * Created by gbh on 16/12/3.
 */

public class BluethBean {
    private BluetoothDevice bluetoothDevice;
    private boolean isAdd;

    public BluethBean(BluetoothDevice bluetoothDevice, boolean isAdd) {
        this.bluetoothDevice = bluetoothDevice;
        this.isAdd = isAdd;
    }

    public BluetoothDevice getBluetoothDevice() {
        return bluetoothDevice;
    }

    public void setBluetoothDevice(BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
    }

    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }
}
