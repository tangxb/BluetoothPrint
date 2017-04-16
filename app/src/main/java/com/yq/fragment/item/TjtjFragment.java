package com.yq.fragment.item;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.yq.utils.TimeUtils;
import com.yq.yqwater.MeApplcition;
import com.yq.yqwater.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author tank
 * @time 2017/3/8  17:38
 * @desc ${统计 -统计页面}
 */


public class TjtjFragment extends Fragment implements OnDateSetListener {

    @Bind(R.id.textView)
    TextView textView;
    @Bind(R.id.time)
    TextView time;
    @Bind(R.id.btn_tj)
    Button btnTj;
    @Bind(R.id.result_msg)
    TextView resultMsg;
    private TimePickerDialog mDialogYearMonth;

    public static TjtjFragment newInstance() {
        return new TjtjFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tjtj, container, false);
        ButterKnife.bind(this, view);
        time.setText(TimeUtils.getCurrentTimeyyyMM());
        return view;
    }


    @OnClick(R.id.btn_tj)
    void tj() {
        String cbye = time.getText().toString().trim();
        if (TextUtils.isEmpty(cbye) || cbye.length() != 6)
            return;
        int allUser = MeApplcition.mgr.selectAlluser(cbye);
        int allMoney = MeApplcition.mgr.selectAllYjMoney(cbye);
        int dayMoney = MeApplcition.mgr.selectDayAllYjMoney();
        int ycbUser = MeApplcition.mgr.selectChaoBiaoCount(cbye);
        int dayChaoBiao = MeApplcition.mgr.selectTodayChaoBiaoCount();
        int byysc = MeApplcition.mgr.uploadCount(cbye);//查询本地数据库上传总数

        String msg = "本月应抄表总户数: " + allUser + "\n" +
                "当日已抄表户数: " + dayChaoBiao + "\n" +
                "当日已收费金额: " + dayMoney + "元" + "\n" +
                "本月已抄表户数: " + ycbUser + "\n" +
                "本月未抄表户数: " + (allUser - ycbUser) + "\n" +
                "本月已收费金额: " + allMoney + "元" + "\n" + "\n" +
                "本月已上传总户数: " + byysc + "\n"+
                "本月未上传总户数: " + (ycbUser - byysc);

        resultMsg.setText(msg);
        resultMsg.setBackgroundResource(R.drawable.bt);
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        Date date = new Date(millseconds);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");//精确到分钟
        String str = format.format(date);
        time.setText(str);
    }

    @OnClick(R.id.time)
    void getTime() {
        mDialogYearMonth = new TimePickerDialog.Builder()
                .setType(Type.YEAR_MONTH)
                .setThemeColor(getResources().getColor(R.color.colorPrimary))
                .setCallBack(TjtjFragment.this)
                .build();
        mDialogYearMonth.show(getActivity().getSupportFragmentManager(), "YEAR_MONTH");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
