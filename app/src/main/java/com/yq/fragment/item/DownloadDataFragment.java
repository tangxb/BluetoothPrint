package com.yq.fragment.item;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.smtlibrary.dialog.SweetAlertDialog;
import com.smtlibrary.utils.LogUtils;
import com.smtlibrary.utils.PreferenceUtils;
import com.yq.model.CbData;
import com.yq.model.Cbj;
import com.yq.model.UserArrears;
import com.yq.tasks.presenter.TaskPresenterImpl;
import com.yq.tasks.views.IView;
import com.yq.utils.TimeUtils;
import com.yq.yqwater.MainActivity;
import com.yq.yqwater.MeApplcition;
import com.yq.yqwater.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yq.yqwater.R.id.bt_sean;
import static com.yq.yqwater.R.id.et_yerdate;


/**
 * Created by mac on 16/11/19.
 */

public class DownloadDataFragment extends Fragment implements View.OnClickListener, IView, OnDateSetListener {


    @Bind(et_yerdate)
    TextView etYerdate;
    @Bind(bt_sean)
    Button btSean;
    @Bind(R.id.lv_mrd)
    ListView lvMrd;
    @Bind(R.id.et_cnumber)
    TextView etCnumber;
    private List<Cbj> dowcbj = new ArrayList<>();
    private SweetAlertDialog sweetAlertDialog;
    private TaskPresenterImpl taskPresenter;

    private String userName;
    private String usernumb;


    public static DownloadDataFragment newInstance() {
        return new DownloadDataFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sweetAlertDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.setTitleText("正在加载中...");
        taskPresenter = new TaskPresenterImpl(this);


    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.getmrd, container, false);
        ButterKnife.bind(this, view);
        //获取抄表员的 所有欠费用户
        etYerdate.setText(TimeUtils.getCurrentTimeyyyMM());
        userName = ((MainActivity) getActivity()).userName;
        usernumb = ((MainActivity) getActivity()).usernumb;
//        PreferenceUtils.putString(getActivity(), "userId", usernumb);
//        PreferenceUtils.putString(getActivity(), "userName", userName);
        etCnumber.setText(userName);


        return view;
    }


    @OnClick(R.id.bt_sean)
    public void onClick(View v) {
        switch (v.getId()) {
            case bt_sean:
                LogUtils.sysout("======usid", usernumb);
                final String fhb = etYerdate.getText().toString().trim();
                if (fhb.equals("")) {
                    Toast.makeText(getActivity(), "请填写数据!", Toast.LENGTH_SHORT).show();
                } else {
                    if (MeApplcition.mgr.selectAlluser(fhb) > 0) {
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                .setTitleText(fhb + "月份已下载!")
                                .setConfirmText("   确定  ")
                                .show();
                    } else
                        taskPresenter.downData(usernumb, fhb);
                }
                break;

        }
    }

    @Override
    public String userId() {
        return PreferenceUtils.getString(getActivity(), "userId", "110101");
    }

    @Override
    public void showPress() {
        sweetAlertDialog.show();
    }

    @Override
    public void hidePress() {
        sweetAlertDialog.dismiss();
    }

    @Override
    public void onFaile(String msg) {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText(msg)
                .setConfirmText("确认")
                .show();
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onDownSuccess(CbData cbData) {
        if (null != cbData.getDATA()) {
            for (int i = 0; i < cbData.getDATA().size(); i++) {
                CbData.CbjDetail b = cbData.getDATA().get(i);
                dowcbj.addAll(b.getGPOUPDATA());
            }
        }
        if (dowcbj.size() > 0) {
            MeApplcition.mgr.add(cbData);
            new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("下载并保存成功。")
                    .setConfirmText("确认")
                    .show();
        }

    }

    @Override
    public void onDownUserInfo(UserArrears userArrears) {

    }


    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        Date date = new Date(millseconds);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");//精确到分钟
        String str = format.format(date);
        etYerdate.setText(str);
    }

    @OnClick(et_yerdate)
    void getTime() {
        TimePickerDialog mDialogYearMonth = new TimePickerDialog.Builder()
                .setType(Type.YEAR_MONTH)
                .setThemeColor(getResources().getColor(R.color.colorPrimary))
                .setCallBack(this)
                .build();
        mDialogYearMonth.show(getActivity().getSupportFragmentManager(), "YEAR_MONTH");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
