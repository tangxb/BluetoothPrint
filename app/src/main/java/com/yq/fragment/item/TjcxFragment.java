package com.yq.fragment.item;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.smtlibrary.dialog.SweetAlertDialog;
import com.yq.model.Cbj;
import com.yq.model.QueryBean;
import com.yq.tasks.presenter.TaskPresenterImpl;
import com.yq.tasks.views.QueryView;
import com.yq.utils.TimeUtils;
import com.yq.yqwater.MeApplcition;
import com.yq.yqwater.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author tank
 * @time 2017/3/8  17:38
 * @desc ${统计 -查询页面}
 */


public class TjcxFragment extends Fragment implements OnDateSetListener, QueryView {
    @Bind(R.id.textView3)
    TextView textView3;
    @Bind(R.id.time3)
    TextView time3;
    @Bind(R.id.btn_tj)
    Button btnTj;
    @Bind(R.id.result_msg)
    TextView resultMsg;
    @Bind(R.id.textView1)
    TextView textView1;
    @Bind(R.id.time1)
    EditText time1;
    @Bind(R.id.textView2)
    TextView textView2;
    @Bind(R.id.time2)
    EditText time2;
    private TimePickerDialog mDialogYearMonth;
    private TaskPresenterImpl taskPresenter;
    private String msg;
    private SweetAlertDialog sweetAlertDialog;
    private String str1;
    private String cbye;
    private Cbj cbj;


    public static TjcxFragment newInstance() {
        return new TjcxFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tj, container, false);
        //        ButterKnife.bind(this, view);
        ButterKnife.bind(this, view);
        time3.setText(TimeUtils.getCurrentTimeyyyMM());
        sweetAlertDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.setTitleText("正在加载中...");
        taskPresenter = new TaskPresenterImpl(this);
        return view;
    }

    @OnClick(R.id.btn_tj)
    void tj() {
        //str1标签号
        str1 = time1.getText().toString().trim();

        //str2标签号
        String str2 = time2.getText().toString().trim();
        //        chaxunbt(str2);
        if (TextUtils.isEmpty(str1) && TextUtils.isEmpty(str2)) {
            Toast.makeText(getContext(), "请输入标签号或手机号码", Toast.LENGTH_SHORT).show();
            return;
        } else if (str1.length() < 10 && str2.length() < 11) {
            Toast.makeText(getContext(), "请输入正确的标签号或手机号码", Toast.LENGTH_SHORT).show();
            return;
        }
        cbye = time3.getText().toString().trim();
        if (TextUtils.isEmpty(cbye) || cbye.length() != 6) {
            return;
        }
        //        Log.d("m520", "标签号=" + str1 + "手机号=" + str2 + "月份=" + cbye);
        taskPresenter.queryData(str1, str2, cbye);
        //        if (cbData.getRet().equals("ok"))
        //            qView.onquerySuccess(cbData);
        //        else {
        //            qView.onFaile("标签号或手机号不存在!");
        //        }

    }

    //查询统计按钮
/*    private void chaxunbt(String cbye) {
//        if (TextUtils.isEmpty(cbye) || cbye.length() != 6)
//            return;
        int allUser = MeApplcition.mgr.selectAlluser(cbye);
        int allMoney = MeApplcition.mgr.selectAllYjMoney(cbye);
        int dayMoney = MeApplcition.mgr.selectDayAllYjMoney();
        int ycbUser = MeApplcition.mgr.selectChaoBiaoCount(cbye);
        int dayChaoBiao = MeApplcition.mgr.selectTodayChaoBiaoCount();

        String msg = "本月应抄表总户数: " + allUser + "\n" +
                "当日已抄表户数: " + dayChaoBiao + "\n" +
                "当日已收费金额: " + dayMoney + "元" + "\n" +
                "本月已抄表户数: " + ycbUser + "\n" +
                "本月未抄表户数: " + (allUser - ycbUser) + "\n" +
                "本月已收费金额: " + allMoney + "元";
        resultMsg.setText(msg);
        resultMsg.setBackgroundResource(R.drawable.bt);
    }*/

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        Date date = new Date(millseconds);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");//精确到分钟
        String str = format.format(date);
        time3.setText(str);
    }

    @OnClick(R.id.time3)
    void getTime() {
        mDialogYearMonth = new TimePickerDialog.Builder()
                .setType(Type.YEAR_MONTH)
                .setThemeColor(getResources().getColor(R.color.colorPrimary))
                .setCallBack(TjcxFragment.this)
                .build();
        mDialogYearMonth.show(getActivity().getSupportFragmentManager(), "YEAR_MONTH");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @Override
    public void onquerySuccess(QueryBean cbData) {
        msg = "";
        if (cbData != null) {
            List<QueryBean.DATABean> data = cbData.getDATA();
            for (int i = 0; i < data.size(); i++) {
                QueryBean.DATABean dataBean = data.get(i);
                String bcye = dataBean.getBcye();
                String bqsf = dataBean.getBqsf();
                String bybs = dataBean.getBybs();
                String jfje = dataBean.getJfje();
                String jfrq = dataBean.getJfrq();
                String sybs = dataBean.getSybs();
                String sysl = dataBean.getSysl();
//                String xzje = dataBean.getXzje();//销账金额
                String scye = dataBean.getScye();
                String ysqj = dataBean.getYsqj();
                String ysxz = dataBean.getYsxz();
                msg = "用水期间: " + ysqj + "\n" +
                        "本月表数: " + bybs + "\n" +
                        "上月表数: " + sybs + "\n" +
                        "收费水量: " + sysl + "\n" +
                        "上次结余: " + scye + "\n" +
                        "交费金额: " + jfje + "元" + "\n" +
                        "本期水费: " + bqsf + "元" + "\n" +
                        "本次余额: " + bcye + "元" + "\n" +
                        "用水性质: " + ysxz+"\n"+
                        "缴费日期: " + jfrq + "\n";
            }
            if (TextUtils.isEmpty(msg)) {
                cbj = MeApplcition.mgr.queryTheCursor(str1);//查询标签是否存在
                if (null != cbj){
                    msg = "本月没有记录!";
                }else{
                    msg = "您输入的标签号或手机号不存在!";
                }
            }
            resultMsg.setText(msg);
            resultMsg.setBackgroundResource(R.drawable.bt);

        } else {
            Toast.makeText(getContext(), "查询失败,请重试！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public String userId() {
        return null;
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
}
