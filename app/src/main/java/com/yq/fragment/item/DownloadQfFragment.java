package com.yq.fragment.item;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.smtlibrary.dialog.SweetAlertDialog;
import com.smtlibrary.utils.PreferenceUtils;
import com.yq.model.CbData;
import com.yq.model.Cbj;
import com.yq.model.UserArrears;
import com.yq.tasks.presenter.TaskPresenterImpl;
import com.yq.tasks.views.IView;
import com.yq.utils.Prices;
import com.yq.yqwater.MeApplcition;
import com.yq.yqwater.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yq.yqwater.R.id.bt_usersean;
import static com.yq.yqwater.R.id.et_userumber;
import static com.yq.yqwater.R.id.et_wmnumber;
import static com.yq.yqwater.R.id.show_userinfo;


/**
 * Created by mac on 16/11/19.
 */

public class DownloadQfFragment extends Fragment implements IView {

    @Bind(et_userumber)
    EditText etUserumber;
    @Bind(et_wmnumber)
    EditText etWmnumber;
    @Bind(R.id.bt_usersean)
    Button btUsersean;
    @Bind(show_userinfo)
    TextView showUserinfo;
    private List<Cbj> dowcbj = new ArrayList<>();

    private SweetAlertDialog sweetAlertDialog;
    private TaskPresenterImpl taskPresenter;

    public static DownloadQfFragment newInstance() {
        return new DownloadQfFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sweetAlertDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.setTitleText("正在加载中...");
        taskPresenter = new TaskPresenterImpl(this);
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.getone, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(bt_usersean)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_usersean:
                String userid = etUserumber.getText().toString().trim();
                String userfhb = etWmnumber.getText().toString().trim();
                if (userid.equals("") || userfhb.equals("")) {
                    Toast.makeText(getActivity(), "请填写数据!", Toast.LENGTH_SHORT).show();
                } else {
                    taskPresenter.getUserArrearsInfo(userid, userfhb);
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
        if (userArrears.getDATA().size() > 0) {
            etUserumber.setText("");
            etWmnumber.setText("");
            UserArrears.User ua = userArrears.getDATA().get(0);
            String info = "客户编号:" + ua.getHmph() + "\n" +
                    "总金额 : " + Prices.m2(Double.parseDouble(ua.getZje())) + "\n"
                    + "欠费月份 : " + ua.getQfyf() + "\n" + "违约金 : "
                    + Prices.m2(Double.parseDouble(ua.getWyj())) + "\n" + "水费金额 : "
                    + Prices.m2(Double.parseDouble(ua.getJe()));
            showUserinfo.setText(info);
        } else {
            showUserinfo.setText("无欠费信息。");
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
