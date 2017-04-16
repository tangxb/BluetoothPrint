package com.yq.fragment.item;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.smtlibrary.utils.LogUtils;
import com.yq.model.BluethBean;
import com.yq.model.Cbj;
import com.yq.tools.MyListAdapter;
import com.yq.utils.TimeUtils;
import com.yq.view.MyListView;
import com.yq.yqwater.MainActivity;
import com.yq.yqwater.MeApplcition;
import com.yq.yqwater.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yq.yqwater.R.id.bt_print;
import static com.yq.yqwater.R.id.bt_sourcebult;
import static com.yq.yqwater.R.id.bt_uppay;
import static com.yq.yqwater.R.id.et_paysnumber;
import static com.yq.yqwater.R.id.et_payuserid;
import static com.yq.yqwater.R.id.et_payuserje;

/**
 * Created by mac on 16/11/19.
 */

public class UploadJfFragment extends BaseFragment implements View.OnClickListener {

    @Bind(et_payuserid)
    EditText etPayuserid;
    @Bind(et_payuserje)
    EditText etPayuserje;
    @Bind(et_paysnumber)
    TextView etPaysnumber;
    @Bind(bt_uppay)
    Button btUppay;
    @Bind(R.id.bt_print)
    Button btPrint;//打印
    @Bind(R.id.lv_scanbul)
    MyListView lvScanbul;
    @Bind(R.id.bt_sourcebult)
    Button btSourcebult;//搜索蓝牙
    private String userName;
    private String usernumb;
    private ArrayList<BluethBean> searchDevicesList;
    private MyListAdapter mSearchAdapter;
    private BluetoothDevice device;
    private BluetoothSocket socket;//蓝牙连接socket
    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private OutputStream outputStream;
    private InputStream inputStream;
    private String prinshowinfo;
    private String payId;//户号
    private String payje;//缴费金额
    private String totalpayje;//缴费金额
    private String payTime;//缴费金额


    public static UploadJfFragment newInstance() {
        return new UploadJfFragment();
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.uppayment, container, false);
        ButterKnife.bind(this, view);
        userName = ((MainActivity) getActivity()).userName;
        usernumb = ((MainActivity) getActivity()).usernumb;
        etPaysnumber.setText(userName);

        searchDevicesList = new ArrayList<BluethBean>();
        mSearchAdapter = new MyListAdapter(getActivity(), searchDevicesList);
        lvScanbul.setAdapter(mSearchAdapter);
        lvScanbul.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                BluethBean b = searchDevicesList.get(position);
                device = b.getBluetoothDevice();
                if (b.isAdd()) {
//                    close();//关闭蓝牙连接socket
                    connect(device);//蓝牙设备的连接
                } else {
                    try {
                        // 配对
                        Method createBondMethod = BluetoothDevice.class.getMethod("createBond");
                        createBondMethod.invoke(device);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        btPrint.setEnabled(false);
        btSourcebult.setEnabled(false);
        // 注册用以接收到已搜索到的蓝牙设备的receiver
        IntentFilter mFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        mFilter.addAction(BluetoothDevice.ACTION_FOUND);
        mFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        mFilter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        mFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        mFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        // 注册广播接收器，接收并处理搜索结果
        getActivity().registerReceiver(receiver, mFilter);

        if (null != bluetoothAdapter) {
            //得到所有已经配对的蓝牙适配器对象
            Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
            if (devices.size() > 0) {
                //用迭代
                for (Iterator iterator = devices.iterator(); iterator.hasNext(); ) {
                    //得到BluetoothDevice对象,也就是说得到配对的蓝牙适配器
                    BluetoothDevice device = (BluetoothDevice) iterator.next();
                    //得到远程蓝牙设备的地址
                    LogUtils.sysout("mytag", device.getAddress());
                    searchDevicesList.add(new BluethBean(device, true));
                    mSearchAdapter.notifyDataSetChanged();
                }
            }

        }
        return view;
    }

    //蓝牙设备的连接（客户端）
    private void connect(BluetoothDevice device) {
        // 固定的UUID
        final String SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB";
        UUID uuid = UUID.fromString(SPP_UUID);
        try {
            socket = device.createRfcommSocketToServiceRecord(uuid);
            socket.connect();
            outputStream = socket.getOutputStream();
            inputStream = socket.getInputStream();
            btPrint.setEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        userName = ((MainActivity) getActivity()).userName;
//        usernumb = ((MainActivity) getActivity()).usernumb;
//        etPaysnumber.setText(userName);
//    }

    @OnClick({bt_uppay,bt_sourcebult, bt_print})
    public void onClick(View v) {
        switch (v.getId()) {
            case bt_uppay:
                payId = etPayuserid.getText().toString().trim();
                payje = etPayuserje.getText().toString().trim();
                //                String payNumber = etPaysnumber.getText().toString().trim();
                if (TextUtils.isEmpty(payId) || TextUtils.isEmpty(payje) || TextUtils.isEmpty(usernumb)) {
                    Toast.makeText(getActivity(), "请填写数据!", Toast.LENGTH_SHORT).show();
                    return;
                }
                int yjmoney = MeApplcition.mgr.selectYjMoney(payId);//先获取数据库里面的金额
                //                Log.d("m520","数据库原有金额"+yjmoney);
                totalpayje = String.valueOf(yjmoney + Integer.parseInt(payje));
                //                Log.d("m520","所有预交金额"+payje);
                MeApplcition.mgr.updateYjMoney(payId, totalpayje);//先保存到数据库
                taskPresenter.upPayData(payId, totalpayje, usernumb);
                payTime = TimeUtils.getCurrentTime();
                break;
            //搜索蓝牙
            case bt_sourcebult:
                Toast.makeText(getActivity(), "正在收索,请稍等!", Toast.LENGTH_LONG).show();
                checkBluet();
                break;
            //打印
            case bt_print:

                if (TextUtils.isEmpty(payId)) {
                    Toast.makeText(getContext(), "缴费成功后才能打印", Toast.LENGTH_SHORT).show();
                } else {
                    Cbj cbj = MeApplcition.mgr.queryByHmphCursor(payId);
                    prinshowinfo = "                   预交水费小票" + "\n\n" +
                            "户号:" + payId + "\n" +
                            "户名:" + cbj.getHm() + "\n" +
                            "地址:" + cbj.getDz() + "\n" +
                            "预交金额:" + payje + "\n" +
                            "收费人员:" + userName + "\n" +
                            "收费时间:" + payTime + "\n" +
                            "鸦鹊岭自来水厂" + "\n" +
                            "电话:7741045" + "\n\n\n";

                    try {
                        outputStream.write(prinshowinfo.getBytes("GBK"));
                        outputStream.flush();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //TODO
                    close();//关闭蓝牙连接socket
                    break;
                }
        }
    }

    private void checkBluet() {
        try {

            if (!bluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                getActivity().startActivityForResult(enableBtIntent, 1);
            } else {
                bluetoothAdapter.startDiscovery();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        if (device != null) {
            close();
            //            System.out.println("清空!!!");
        }
        getActivity().unregisterReceiver(receiver);
        super.onDestroy();
    }

    private void close() {
        if (null == socket)
            return;
        if (socket.isConnected()) {
            try {
                outputStream.close();
                inputStream.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onSuccess() {
        Toast.makeText(getActivity(), "上传成功", Toast.LENGTH_SHORT).show();
        etPayuserid.setText("");
//        etPaysnumber.setText("");
        etPayuserje.setText("");
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            System.out.println("onReceiveonReceive");
            String action = intent.getAction();
            // 获得已经搜索到的蓝牙设备
            if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                BluetoothDevice device = intent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // 搜索到的不是已经绑定的蓝牙设备
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    // 防止重复添加
                    if (searchDevicesList.indexOf(device) == -1)
                        searchDevicesList.add(new BluethBean(device, false));
                    mSearchAdapter.notifyDataSetChanged();
                }
                // 搜索完成
            } else if (action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {

            } else if (action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)) {
                // 状态改变的广播
                device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String name = device.getName();
                if (TextUtils.isEmpty(name) || device.getName().equalsIgnoreCase(name)) {
                    int connectState = device.getBondState();
                    switch (connectState) {
                        case BluetoothDevice.BOND_NONE:  //10
                            Toast.makeText(getActivity(), "取消配对：" + device.getName(), Toast.LENGTH_SHORT).show();
                            break;
                        case BluetoothDevice.BOND_BONDING:  //11
                            Toast.makeText(getActivity(), "正在配对：" + device.getName(), Toast.LENGTH_SHORT).show();
                            break;
                        case BluetoothDevice.BOND_BONDED:   //12
                            Toast.makeText(getActivity(), "完成配对：" + device.getName(), Toast.LENGTH_SHORT).show();
                            try {
                                // 连接
                                connect(device);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                }
            }
        }
    };
}
