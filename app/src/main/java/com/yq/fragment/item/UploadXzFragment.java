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

import static com.yq.yqwater.R.id.et_userxz;
import static com.yq.yqwater.R.id.et_xzuserid;

/**
 * Created by mac on 16/11/19.
 */

public class UploadXzFragment extends BaseFragment implements View.OnClickListener {

    @Bind(et_xzuserid)
    EditText etXzuserid;
    @Bind(et_userxz)
    EditText etUserxz;
    @Bind(R.id.bt_upxz)
    Button btUpxz;

    public static UploadXzFragment newInstance() {
        return new UploadXzFragment();
    }


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.upxz, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.bt_upxz)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_upxz:
                String xzId = etXzuserid.getText().toString().trim();
                String xzValue = etUserxz.getText().toString().trim();

                if (TextUtils.isEmpty(xzId) || TextUtils.isEmpty(xzValue)) {
                    Toast.makeText(getActivity(), "请填写数据!", Toast.LENGTH_SHORT).show();
                    return;
                }
                taskPresenter.upXzData(xzId, xzValue);
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
        etXzuserid.setText("");
        etUserxz.setText("");
    }
}
