package com.yq.fragment.item;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yq.yqwater.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yq.yqwater.R.id.bt_upphone;
import static com.yq.yqwater.R.id.et_userid;
import static com.yq.yqwater.R.id.et_userphone;

/**
 * Created by mac on 16/11/19.
 */

public class UploadDhFragment extends BaseFragment implements View.OnClickListener {

    @Bind(et_userid)
    EditText etUserid;
    @Bind(et_userphone)
    EditText etUserphone;
    @Bind(bt_upphone)
    Button btUpphone;


    public static UploadDhFragment newInstance() {
        return new UploadDhFragment();
    }


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.upphonenumber, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @OnClick(R.id.bt_upphone)
    public void onClick(View v) {
        switch (v.getId()) {
            case bt_upphone:
                String upid = etUserid.getText().toString().trim();
                String upnumber = etUserphone.getText().toString().trim();
                if (TextUtils.isEmpty(upid) || TextUtils.isEmpty(upnumber)) {
                    Toast.makeText(getActivity(), "请填写数据!", Toast.LENGTH_SHORT).show();
                    return;
                }
                taskPresenter.uploadPhone(upid, upnumber);
                break;
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
        etUserid.setText("");
        etUserphone.setText("");
    }
}
