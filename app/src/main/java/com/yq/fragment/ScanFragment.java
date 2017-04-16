package com.yq.fragment;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.rscja.deviceapi.RFIDWithLF;
import com.smtlibrary.dialog.SweetAlertDialog;
import com.smtlibrary.utils.LogUtils;
import com.smtlibrary.utils.PreferenceUtils;
import com.yq.fragment.item.BaseFragment;
import com.yq.model.BluethBean;
import com.yq.model.Cbj;
import com.yq.tools.MyListAdapter;
import com.yq.utils.PlayRing;
import com.yq.utils.Prices;
import com.yq.utils.TimeUtils;
import com.yq.view.MyListView;
import com.yq.yqwater.MainActivity;
import com.yq.yqwater.MeApplcition;
import com.yq.yqwater.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yq.utils.Prices.m2;
import static com.yq.yqwater.R.id.bt_inputDzbq;
import static com.yq.yqwater.R.id.bt_print;
import static com.yq.yqwater.R.id.bt_scanrfid;
import static com.yq.yqwater.R.id.bt_sourcebult;
import static com.yq.yqwater.R.id.et_benyuebs;
import static com.yq.yqwater.R.id.et_dzbq;
import static com.yq.yqwater.R.id.tv_dh;
import static com.yq.yqwater.R.id.tv_showscaninfo;

/**
 * Created by mac on 16/11/19.
 */

public class ScanFragment extends BaseFragment implements OnDateSetListener {

    public RFIDWithLF mLF;
    @Bind(R.id.selectTime)
    TextView selectTime;
    @Bind(R.id.yuefen)
    TextView yuefen;
    @Bind(et_dzbq)
    EditText etDzbq;
    @Bind(bt_inputDzbq)
    Button btInputDzbq;
    @Bind(bt_scanrfid)
    Button btScanrfid;
    @Bind(tv_showscaninfo)
    TextView tvShowscaninfo;
    @Bind(R.id.bybs)
    TextView bybs;
    @Bind(et_benyuebs)
    EditText etBenyuebs;
    @Bind(R.id.bt_save)
    Button btSave;
    @Bind(R.id.bt_payMoney)
    Button btPayMoney;
    @Bind(bt_sourcebult)
    Button btSourcebult;
    @Bind(bt_print)
    Button btPrint;
    @Bind(R.id.lv_scanbul)
    MyListView lvScanbul;
    @Bind(R.id.tv_showBankinfo)
    TextView tvShowBankinfo;
    @Bind(tv_dh)
    TextView tvDh;
    @Bind(R.id.phoneLayout)
    LinearLayout phoneLayout;
    private ScanThread scanThread;
    private static final UUID uuid = UUID
            .fromString("00001101-0000-1000-8000-00805F9B34FB");
    public static BluetoothDevice mdevice = null;
    public static String mDeviceAddress = "";

    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private List<BluetoothDevice> bondedDevicesList;
    private List<BluethBean> searchDevicesList;
    private MyListAdapter mSearchAdapter;
    private String prinshowinfo = "";
    private String dzpd = "";
    private Cbj cbj;
    private boolean iscontinue;
    private String yjMoney = "0";//预交钱
    private String beny;
    public static final int ZERO = 0;
    private String dzbq;
    private String userName;
    private String usernumb;
    private String scjy;//上次结余

    public static ScanFragment newInstance() {
        return new ScanFragment();
    }


    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(getActivity(), "请重新读卡!", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    if (msg.obj.toString() == null) {
                        return;
                    }
                    String dzbq = HexToInt(msg.obj.toString()) + "";
                    int len = dzbq.length();
                    while (len < 10) {
                        dzbq = "0" + dzbq;
                        len++;
                    }
                    etDzbq.setText(dzbq);
                    //                    dzbq = "0000480101";
                    cbj = MeApplcition.mgr.queryTheCursor(dzbq, yuefen.getText().toString().trim());
                    if (null == cbj) {
                        Toast.makeText(getActivity(), "用户信息不存在!", Toast.LENGTH_SHORT).show();
                    } else {
                        PlayRing.ring(getActivity());
                        bybs.setText(cbj.getIsChaoBiao() == 0 ? "未抄表" : "已抄表");
                        etBenyuebs.setText("");
                        yjMoney = "0";
                        btSave.setEnabled(true);
                        prinshowinfo = "ID卡编号:" + cbj.getDzbq() + "\n"
                                + "户名:" + cbj.getHm() + "\n"
                                + "上月表数:" + cbj.getCmds0() + "\n"
                                + "上月水量:" + cbj.getSysl0() + "\n"
                                + "上月结余:" + scjy;
                        dzpd = cbj.getDzbq();
                        tvShowscaninfo.setText(prinshowinfo);
                        if (cbj.getDk().equals("1")) {
                            showBankError();
                        }
                        phoneLayout.setVisibility(View.VISIBLE);
                        tvDh.setText(TextUtils.isEmpty(cbj.getDh()) ? "" : cbj.getDh());
                    }
                    break;
            }
        }
    };

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        Log.d("m520","onCreateView被执行了...");
        View view = inflater.inflate(R.layout.scan, container, false);
        ButterKnife.bind(this, view);
        btSave.setEnabled(false);
        System.out.println("onCreateView");
        yuefen.setText(TimeUtils.getCurrentTimeyyyMM());
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
                    connect(device);
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


        //从SharedPreferences取出上月余额
        return view;
    }


    @Override
    public void onPause() {
//        Log.d("m520","onPause被执行了...");
        super.onPause();
    }

    @Override
    public void onStop() {
//        Log.d("m520","onStop被执行了...");
        super.onStop();
    }

    @Override
    public void onDestroy() {
//        Log.d("m520","onDestroy被执行了...");
        if (device != null) {
            close();
            System.out.println("清空!!!");
        }
        getActivity().unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
//        Log.d("m520","onCreate被执行了...");
        try {
            mLF = RFIDWithLF.getInstance();
            System.out.println("获取成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean ret = mLF.init();
        if (ret) {
            System.out.println("打开成功");
        } else {
            System.out.println("打开失败");
        }
        super.onCreate(savedInstanceState);
    }

    @OnClick({bt_inputDzbq, bt_scanrfid, R.id.bybs, R.id.bt_save, R.id.bt_payMoney, bt_sourcebult, bt_print, R.id.selectTime, R.id.yuefen})
    public void onClick(View v) {
        switch (v.getId()) {
            case bt_scanrfid:
                scan();
                break;
            case bt_print:
                beny = etBenyuebs.getText().toString().trim();
                if (TextUtils.isEmpty(beny)) {
                    Toast.makeText(getActivity(), "请填写本月表数!", Toast.LENGTH_SHORT).show();
                } else {
                    if (null == cbj)
                        return;
                    bybs.setText(beny);
                    //计算水量
                    int sl = (Integer.parseInt(beny) - Integer.parseInt(cbj.getCmds0()));
                    //修改数量 Integer.parseInt(cbj.getDds())*2
                    //                    sl = sl <= Integer.parseInt(cbj.getDds())*2 ? 2 * Integer.parseInt(cbj.getDds()) : sl;

                    //yfs抄表月数返回是0时,判断当月用水量是否小于底吨数,是的话就取底吨数,否者按实际的算
                    if (ZERO == cbj.getYfs())
                        sl = sl <= Integer.parseInt(cbj.getDds()) ? Integer.parseInt(cbj.getDds()) : sl;
                    else {
                        sl = sl <= Integer.parseInt(cbj.getDds()) * cbj.getYfs() ? Integer.parseInt(cbj.getDds()) * cbj.getYfs() : sl;
                    }

                    double money = Prices.getMoney(sl, cbj);
                    String byjy = m2(Double.parseDouble(yjMoney) + Double.parseDouble(scjy) - money);
                    prinshowinfo = "\n" + "用户编号:" + cbj.getHmph() + "\n" +
                            "电子标签号:" + cbj.getDzbq() + "\n" +
                            "户名:" + cbj.getHm() + "\n" +
                            "本月表数:" + beny + "\n" +
                            "上月表数:" + cbj.getCmds0() + "\n" +
                            "本月水量:" + sl + "\n" +
                            "单价:" + Prices.getPrice(cbj.getYsxz()) + "\n" +
                            "上月结余:" + scjy + "\n" +
                            "本月预交:" + yjMoney + "\n" +
                            "本月应收:" + m2(money) + "\n" +
                            "本月结余:" + byjy + "\n" +
                            "收费人:" + getUserName() + "\n" +
                            "收费时间:" + TimeUtils.getCurrentTime() + "\n\n\n";
                    try {
                        outputStream.write(prinshowinfo.getBytes("GBK"));
                        outputStream.flush();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                //TODO
//                close();//关闭蓝牙连接socket
                break;
            case bt_sourcebult:
                Toast.makeText(getActivity(), "正在收索,请稍等!", Toast.LENGTH_LONG).show();
                checkBluet();
                break;

            //查询抄表按钮
            case bt_inputDzbq:
                etBenyuebs.setText("");
                 dzbq = etDzbq.getText().toString().trim();
                if (TextUtils.isEmpty(dzbq)) {
                    Toast.makeText(getActivity(), "请填写标签号!", Toast.LENGTH_SHORT).show();
                    return;
                }
                cbj = MeApplcition.mgr.queryTheCursor(dzbq, yuefen.getText().toString().trim());
                if (null == cbj) {
                    Toast.makeText(getActivity(), "用户信息不存在!", Toast.LENGTH_SHORT).show();
                } else {
                    bybs.setText(cbj.getIsChaoBiao() == 0 ? "未抄表" : "已抄表");
                    yjMoney = "0";
                    btSave.setEnabled(true);
                    scjy = cbj.getScjy();
                    
                    prinshowinfo = "用户编号:" + cbj.getHmph() + "\n" +
                            "ID卡编号:" + cbj.getDzbq() + "\n" +
                            "户名:" + cbj.getHm() + "\n" +
                            "上月表数:" + cbj.getCmds0() + "\n" +
                            "上月水量:" + cbj.getSysl0() + "\n" +
                            "上月结余:" + scjy;
                    dzpd = cbj.getDzbq();
                    tvShowscaninfo.setText(prinshowinfo);
                    tvShowBankinfo.setVisibility(cbj.getDk().equals("1") ? View.VISIBLE : View.GONE);
                    phoneLayout.setVisibility(View.VISIBLE);
                    tvDh.setText(TextUtils.isEmpty(cbj.getDh()) ? "" : cbj.getDh());
//                    Log.d("m520", "保存前的值yfs=" + cbj.getYfs()+"----dzbq="+dzbq);
                }
                break;

            //保存
            case R.id.bt_save:
                //0000501048
                //                Log.d("m520",cbj.getDds());
                beny = etBenyuebs.getText().toString().trim();
                if (TextUtils.isEmpty(beny)) {
                    Toast.makeText(getActivity(), "请填写本月表数!", Toast.LENGTH_SHORT).show();
                    return;
                }
                yjMoney = "0";
                if (cbj.getIsChaoBiao() != 0) {
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("已经抄表，是否需要重抄")
                            .setConfirmText("    否    ")
                            .setCancelText("    是    ")
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    iscontinue = true;
                                    beny = etBenyuebs.getText().toString().trim();
                                    setSaveText();
                                    sweetAlertDialog.dismiss();
                                }
                            }).setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                            iscontinue = false;
                        }
                    }).show();
                    if (!iscontinue)
                        return;
                }

                setSaveText();

                break;
            //
            case R.id.bt_payMoney:
                if (TextUtils.isEmpty(etBenyuebs.getText().toString().trim())) {
                    Toast.makeText(getActivity(), "请填写本月表数!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (null == cbj)
                    return;
                if (cbj.getDk().equals("1")) {
                    showBankError();
                } else {
                    intputMoney();
                }
                break;
            case R.id.selectTime:
                getTime();
                break;
            case R.id.yuefen:
                getTime();
                break;

        }
    }



    /**
     * 保存
     */
    private void setSaveText() {
        if (cbj.getIsChaoBiao() == 0) {
            if (Integer.parseInt(cbj.getCmds0()) > Integer.parseInt(beny)) {
                showError("本月表码小于上月表码，请检查!");
                return;
            }
        }

        if (cbj.getCmds0().equals(beny) && cbj.getBtbh().equals("0")) {
            showError("水量为0且不在报停状态");
        }

        if (cbj.getCmds0().equals(beny) && cbj.getBtbh().equals("1")) {
            showError("水量为0且在报停状态!");
        }

        int sl = (Integer.parseInt(beny) - Integer.parseInt(cbj.getCmds0()));
        if (sl > 10) {
            if ((sl / Double.parseDouble(cbj.getSysl0()) >= 1.3 ||
                    sl / Double.parseDouble(cbj.getSysl0()) <= 0.7)) {
                PlayRing.ring(getActivity());
                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("温馨提示")
                        .setContentText("水量异常,上月水量:" + cbj.getSysl0() + "本月水量:" + sl)
                        .setConfirmText("  确认  ")
                        .setCancelText("  取消  ")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                showText();
                            }
                        }).setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                }).show();
            } else
                showText();
        } else
            showText();
    }

    private void showText() {
        int sl = (Integer.parseInt(beny) - Integer.parseInt(cbj.getCmds0()));
        //修改数量 Integer.parseInt(cbj.getDds())*2
        //        sl = sl <= Integer.parseInt(cbj.getDds()) * 2 ? 2 * Integer.parseInt(cbj.getDds()) : sl;

        //yfs抄表月数返回是0时,判断当月用水量是否小于底吨数,是的话就取底吨数,否者按实际的算
        if (ZERO == cbj.getYfs()) {
//            Log.d("m520", "等于零的时候yfs=" + cbj.getYfs()+"----dzbq="+dzbq);
            sl = sl <= Integer.parseInt(cbj.getDds()) ? Integer.parseInt(cbj.getDds()) : sl;
        } else {
//            int value = Integer.parseInt(cbj.getDds());
//            int key = cbj.getYfs();
//            sl = sl <= value * key ? value * key : sl;

            //sl = sl <= Integer.parseInt(cbj.getDds()) * Integer.parseInt(cbj.getYfs()) ? Integer.parseInt(cbj.getDds()) * Integer.parseInt(cbj.getYfs()): sl;
//            Log.d("m520111", "不等于零的时候yfs=" + cbj.getYfs()+"----dzbq="+dzbq);
            sl = sl <= Integer.parseInt(cbj.getDds()) * cbj.getYfs() ? Integer.parseInt(cbj.getDds()) * cbj.getYfs() : sl;
        }

        String money = m2(Prices.getMoney(sl, cbj));
        Double yy = Double.parseDouble(scjy) - Prices.getMoney(sl, cbj);
        prinshowinfo = "用户编号:" + cbj.getHmph() + "\n" +
                "户名:" + cbj.getHm() + "\n" +
                "本月表数:" + beny + "\n" +
                "上月表数:" + cbj.getCmds0() + "\n" +
                "本月水量:" + sl + "\n" +
                "单价:" + Prices.getPrice(cbj.getYsxz()) + "\n" +
                "上月结余:" + scjy + "\n" +
                "本月预交:" + yjMoney + "\n" +
                "本月应收:" + money + "\n" +
                "本月结余:" + (yy < 0 ? "用户欠费" : Prices.m2(yy));

        bybs.setText(beny);
        tvShowscaninfo.setText(prinshowinfo);
        tvShowBankinfo.setVisibility(cbj.getDk().equals("1") ? View.VISIBLE : View.GONE);
        //
        MeApplcition.mgr.updatebney(cbj.getDzbq(), beny, Prices.m2(yy), 0, String.valueOf(sl), cbj.getCbye());
        tvDh.setText(TextUtils.isEmpty(cbj.getDh()) ? "" : cbj.getDh());
    }


    private void showError(String msg) {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("温馨提示")
                .setContentText(msg)
                .setConfirmText("确认")
                .show();
    }


    private void showBankError() {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("温馨提示")
                .setContentText("该户为银行卡扣款，是否需要现场缴费")
                .setConfirmText("    否    ")
                .setCancelText("    是    ")
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        intputMoney();
                    }
                })
                .show();
    }

    private void intputMoney() {
        final EditText editText = new EditText(getActivity());
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        new AlertDialog.Builder(getActivity()).setTitle("请输入金额")
                .setView(editText)
                .setNegativeButton("取消", null)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        yjMoney = editText.getText().toString().trim();
                        if (TextUtils.isEmpty(yjMoney)) {
                            Toast.makeText(getActivity(), "您还没有输入金额", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        String beny = etBenyuebs.getText().toString().trim();
                        int sl = (Integer.parseInt(beny) - Integer.parseInt(cbj.getCmds0()));
                        double money = Prices.getMoney(sl, cbj);
                        String byjy = m2(Double.parseDouble(yjMoney) + Double.parseDouble(scjy) - money);
                        prinshowinfo = "用户编号:" + cbj.getHmph() + "\n" +
                                "户名:" + cbj.getHm() + "\n" +
                                "本月表数:" + beny + "\n" +
                                "上月表数:" + cbj.getCmds0() + "\n" +
                                "水量:" + sl + "\n" +
                                "单价:" + Prices.getPrice(cbj.getYsxz()) + "\n" +
                                "上月结余:" + scjy + "\n" +
                                "本月预交:" + yjMoney + "\n" +
                                "本月应收:" + m2(money) + "\n" +
                                "本月结余:" + byjy;
                        tvShowscaninfo.setText(prinshowinfo);
                        btSourcebult.setEnabled(true);
                        int payje = MeApplcition.mgr.selectBydzbqYjMoney(dzbq);//先获取数据库里面的金额
//                        Log.d("m520","查询界面数据库原有金额"+payje);
                        yjMoney=String.valueOf(payje+Integer.parseInt(yjMoney));
//                        Log.d("m520","查询界面所有预交金额"+yjMoney);

                        MeApplcition.mgr.updatebney(dzpd, beny, byjy, Double.parseDouble(yjMoney), String.valueOf(sl), cbj.getCbye());

//                        taskPresenter.upPayData(cbj.getHmph(), yjMoney, PreferenceUtils.getString(getActivity(), "userId", "110101"));
                        usernumb = ((MainActivity) getActivity()).usernumb;
                        taskPresenter.upPayData(cbj.getHmph(), yjMoney, usernumb);

                    }
                }).show();
    }


    public void scan() {
        boolean bContinuous = false;
        int iBetween = 0;
        if (scanThread == null || !bContinuous || scanThread.isStop()) {
            scanThread = new ScanThread(bContinuous, iBetween, 0);
            scanThread.start();
        } else {
            scanThread.cancel();
        }
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        Date date = new Date(millseconds);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");//精确到分钟
        String str = format.format(date);
        yuefen.setText(str);
    }

    private void getTime() {
        TimePickerDialog mDialogYearMonth = new TimePickerDialog.Builder()
                .setType(Type.YEAR_MONTH)
                .setThemeColor(getResources().getColor(R.color.colorPrimary))
                .setCallBack(this)
                .build();
        mDialogYearMonth.show(getActivity().getSupportFragmentManager(), "YEAR_MONTH");
    }

    private String getUserName() {
        String name = PreferenceUtils.getString(getActivity(), "userName", "");
        if (!TextUtils.isEmpty(name))
            return name;
//        final String[] arrayUserId = getActivity().getResources().getStringArray(R.array.userId);
//        final String[] arrayUserName = getActivity().getResources().getStringArray(R.array.userNames);
//        int index = 0;
//        for (int i = 0; i < arrayUserId.length; i++) {
//            if (PreferenceUtils.getString(getActivity(), "userId", "110101").equals(arrayUserId[i])) {
//                index = i;
//                break;
//            }
//        }
//
//        return arrayUserName[index];
        userName = ((MainActivity) getActivity()).userName;
        return userName;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(tv_dh)
    public void onClick() {
        //意图：想干什么事
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        //url:统一资源定位符
        //uri:统一资源标示符（更广）
        intent.setData(Uri.parse("tel:" + tvDh.getText().toString().trim()));
        //开启系统拨号器
        startActivity(intent);
    }

    public void onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_F9) {
            scan();
        }
    }


    private class ScanThread extends Thread {
        boolean mIsAuto;
        int mTime;
        int mTagType;
        boolean threadStop = true;

        public ScanThread(boolean isAuto, int time, int tagType) {
            mIsAuto = isAuto;
            mTime = time;
            mTagType = tagType;
            threadStop = false;
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            super.run();

            do {
                Message msg = Message.obtain();
                Object result = null;

                switch (mTagType) {
                    case 0:
                        result = mLF.readDataWithIDCard(0);
                        break;
                    default:
                        break;
                }

                if (result == null || result.toString().equals("-1")) {
                    msg.what = 0;
                    msg.arg2 = mTagType;
                } else {
                    msg.what = 1;
                    msg.arg2 = mTagType;
                    msg.obj = result;
                    System.out.println("result === " + result);
                    System.out.println("mTagType === " + mTagType);
                }
                mHandler.sendMessage(msg);

                if (mIsAuto) {
                    try {
                        sleep(mTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } while (mIsAuto && !threadStop);

            threadStop = true;
        }

        public boolean isStop() {
            return threadStop;
        }

        public void cancel() {
            threadStop = true;
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

    private BluetoothDevice device;
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

    private OutputStream outputStream;
    private InputStream inputStream;
    private BluetoothSocket socket;

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

    private void unpairDevice(BluetoothDevice device) {
        try {
            Method m = device.getClass()
                    .getMethod("removeBond", (Class[]) null);
            m.invoke(device, (Object[]) null);
        } catch (Exception e) {
            e.printStackTrace();
        }
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


    //16进制转10进制
    public static int HexToInt(String strHex) {
        int nResult = 0;
        if (!IsHex(strHex))
            return nResult;
        String str = strHex.toUpperCase();
        if (str.length() > 2) {
            if (str.charAt(0) == '0' && str.charAt(1) == 'X') {
                str = str.substring(2);
            }
        }
        int nLen = str.length();
        for (int i = 0; i < nLen; ++i) {
            char ch = str.charAt(nLen - i - 1);
            try {
                nResult += (GetHex(ch) * GetPower(16, i));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return nResult;
    }

    //计算16进制对应的数值
    public static int GetHex(char ch) throws Exception {
        if (ch >= '0' && ch <= '9')
            return (int) (ch - '0');
        if (ch >= 'a' && ch <= 'f')
            return (int) (ch - 'a' + 10);
        if (ch >= 'A' && ch <= 'F')
            return (int) (ch - 'A' + 10);
        throw new Exception("error param");
    }

    //计算幂
    public static int GetPower(int nValue, int nCount) throws Exception {
        if (nCount < 0)
            throw new Exception("nCount can't small than 1!");
        if (nCount == 0)
            return 1;
        int nSum = 1;
        for (int i = 0; i < nCount; ++i) {
            nSum = nSum * nValue;
        }
        return nSum;
    }

    //判断是否是16进制数
    public static boolean IsHex(String strHex) {
        int i = 0;
        if (strHex.length() > 2) {
            if (strHex.charAt(0) == '0' && (strHex.charAt(1) == 'X' || strHex.charAt(1) == 'x')) {
                i = 2;
            }
        }
        for (; i < strHex.length(); ++i) {
            char ch = strHex.charAt(i);
            if ((ch >= '0' && ch <= '9') || (ch >= 'A' && ch <= 'F') || (ch >= 'a' && ch <= 'f'))
                continue;
            return false;
        }
        return true;
    }


}
