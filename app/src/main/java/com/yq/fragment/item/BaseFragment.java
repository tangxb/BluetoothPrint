package com.yq.fragment.item;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.smtlibrary.dialog.SweetAlertDialog;
import com.smtlibrary.utils.PreferenceUtils;
import com.yq.tasks.presenter.TaskPresenter;
import com.yq.tasks.presenter.TaskPresenterImpl;
import com.yq.tasks.views.UView;


public class BaseFragment extends Fragment implements UView {

    protected TaskPresenter taskPresenter;
    private SweetAlertDialog sweetAlertDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sweetAlertDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.setTitleText("正在加载中...");
        taskPresenter = new TaskPresenterImpl(this);
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
        Toast.makeText(getActivity(), "上传成功", Toast.LENGTH_SHORT).show();
    }
}
