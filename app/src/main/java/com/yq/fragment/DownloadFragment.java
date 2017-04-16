//package com.yq.fragment;
//
//import android.app.Activity;
//import android.content.Context;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v4.view.PagerAdapter;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.smtlibrary.dialog.SweetAlertDialog;
//import com.yq.model.CbData;
//import com.yq.model.Cbj;
//import com.yq.model.UserArrears;
//import com.yq.tasks.presenter.TaskPresenterImpl;
//import com.yq.tasks.views.IView;
//import com.yq.utils.Prices;
//import com.yq.view.MyViewPager;
//import com.yq.yqwater.MeApplcition;
//import com.yq.yqwater.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
///**
// * Created by mac on 16/11/19.
// */
//
//public class DownloadFragment extends Fragment implements View.OnClickListener, IView {
//
//    private MyViewPager viewpager;
//    private Button bt_getmrd;
//    private Button bt_getall;
//    private Button bt_getone;
//    private View getmrd;
//    private View getall;
//    private View getone;
//    private List<View> viewList;
//
//    private EditText et_cnumber;
//    private EditText et_yerdate;
//    private Button bt_sean;
//    private ListView lv_mrd;
//    private SCbjAdapter scbjadapter;
//    private List<Cbj> dowcbj = new ArrayList<Cbj>();
//    private Context context;
//    private Activity activity;
//    private EditText et_clientid;
//    private Button bt_clienfsean;
//    private ListView lv_clientfinfo;
//
//    private EditText et_userumber;
//    private EditText et_wmnumber;
//    private TextView show_userinfo;
//    private Button bt_usersean;
//    private SweetAlertDialog sweetAlertDialog;
//    private TaskPresenterImpl taskPresenter;
//
//    public static DownloadFragment newInstance() {
//        return new DownloadFragment();
//    }
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        context = getActivity();
//        activity = getActivity();
//        sweetAlertDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
//        sweetAlertDialog.setTitleText("正在加载中...");
//        taskPresenter = new TaskPresenterImpl(this);
//    }
//
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.download, container, false);
//        viewpager = (MyViewPager) view.findViewById(R.id.viewpager);
//        bt_getmrd = (Button) view.findViewById(R.id.bt_getmrd);
//        bt_getmrd.setTextColor(getResources().getColor(R.color.titile));
//        bt_getmrd.setOnClickListener(this);
//        bt_getall = (Button) view.findViewById(R.id.bt_getall);
//        bt_getall.setOnClickListener(this);
//        bt_getone = (Button) view.findViewById(R.id.bt_getone);
//        bt_getone.setOnClickListener(this);
//        getmrd = inflater.inflate(R.layout.getmrd, null);
//        getall = inflater.inflate(R.layout.getall, null);
//        getone = inflater.inflate(R.layout.getone, null);
//        viewList = new ArrayList<View>();
//        viewList.add(getmrd);
//        viewList.add(getall);
//        viewList.add(getone);
//        viewpager.setAdapter(pagerAdapter);
//        viewpager.setScrollble(false);
//
//        //获取抄表员的 所有欠费用户
//        et_cnumber = (EditText) getmrd.findViewById(R.id.et_cnumber);
//        et_yerdate = (EditText) getmrd.findViewById(R.id.et_yerdate);
//        bt_sean = (Button) getmrd.findViewById(R.id.bt_sean);
//        bt_sean.setOnClickListener(DownloadFragment.this);
//
//        lv_mrd = (ListView) getmrd.findViewById(R.id.lv_mrd);
//        scbjadapter = new SCbjAdapter(getActivity());
//        //lv_mrd.setAdapter(scbjadapter);
//
//        //获取缴费信息
//        et_clientid = (EditText) getall.findViewById(R.id.et_clientid);
//        bt_clienfsean = (Button) getall.findViewById(R.id.bt_clienfsean);
//        lv_clientfinfo = (ListView) getall.findViewById(R.id.lv_clientfinfo);
//
//        //获取用户欠费信息
//        et_userumber = (EditText) getone.findViewById(R.id.et_userumber);
//        et_wmnumber = (EditText) getone.findViewById(R.id.et_wmnumber);
//        show_userinfo = (TextView) getone.findViewById(R.id.show_userinfo);
//        bt_usersean = (Button) getone.findViewById(R.id.bt_usersean);
//        bt_usersean.setOnClickListener(this);
//
//        return view;
//    }
//
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.bt_getmrd:
//                viewpager.setCurrentItem(0);
//                bt_getmrd.setTextColor(getResources().getColor(R.color.titile));
//                bt_getall.setTextColor(Color.BLACK);
//                bt_getone.setTextColor(Color.BLACK);
//                break;
//            case R.id.bt_getall:
//                viewpager.setCurrentItem(1);
//                bt_getall.setTextColor(getResources().getColor(R.color.titile));
//                bt_getmrd.setTextColor(Color.BLACK);
//                bt_getone.setTextColor(Color.BLACK);
//
//                break;
//            case R.id.bt_getone:
//                viewpager.setCurrentItem(2);
//                bt_getone.setTextColor(getResources().getColor(R.color.titile));
//                bt_getall.setTextColor(Color.BLACK);
//                bt_getmrd.setTextColor(Color.BLACK);
//                break;
//            case R.id.bt_sean:
//                final String id = et_cnumber.getText().toString().trim();
//                final String fhb = et_yerdate.getText().toString().trim();
//                if (id.equals("") || fhb.equals("")) {
//                    Toast.makeText(getActivity(), "请填写数据!", Toast.LENGTH_SHORT).show();
//                } else {
//                    taskPresenter.downData(id, fhb);
//                }
//                break;
//            case R.id.bt_clienfsean:
//                break;
//            case R.id.bt_usersean:
//                String userid = et_userumber.getText().toString().trim();
//                String userfhb = et_wmnumber.getText().toString().trim();
//                if (userid.equals("") || userfhb.equals("")) {
//                    Toast.makeText(getActivity(), "请填写数据!", Toast.LENGTH_SHORT).show();
//                } else {
//                    taskPresenter.getUserArrearsInfo(userid, userfhb);
//                }
//                break;
//        }
//    }
//
//    PagerAdapter pagerAdapter = new PagerAdapter() {
//
//        public boolean isViewFromObject(View arg0, Object arg1) {
//            return arg0 == arg1;
//        }
//
//        public int getCount() {
//            return viewList.size();
//        }
//
//        public void destroyItem(ViewGroup container, int position,
//                                Object object) {
//            container.removeView(viewList.get(position));
//        }
//
//        public Object instantiateItem(ViewGroup container, int position) {
//            container.addView(viewList.get(position));
//            return viewList.get(position);
//        }
//    };
//
//
//    @Override
//    public void showPress() {
//        sweetAlertDialog.show();
//    }
//
//    @Override
//    public void hidePress() {
//        sweetAlertDialog.dismiss();
//    }
//
//    @Override
//    public void onFaile(String msg) {
//        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
//                .setTitleText(msg)
//                .setConfirmText("确认")
//                .show();
//    }
//
//    @Override
//    public void onSuccess() {
//
//    }
//
//    @Override
//    public void onDownSuccess(CbData cbData) {
//        if (null != cbData.getDATA()) {
//            for (int i = 0; i < cbData.getDATA().size(); i++) {
//                CbData.CbjDetail b = cbData.getDATA().get(i);
//                dowcbj.addAll(b.getGPOUPDATA());
//            }
//        }
//        if (dowcbj.size() > 0) {
//            MeApplcition.mgr.add(cbData);
//            new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
//                    .setTitleText("下载并保存成功。")
//                    .setConfirmText("确认")
//                    .show();
//        }
//
//    }
//
//    @Override
//    public void onDownUserInfo(UserArrears userArrears) {
//        if (userArrears.getDATA().size() > 0) {
//            et_userumber.setText("");
//            et_wmnumber.setText("");
//            UserArrears.User ua = userArrears.getDATA().get(0);
//            String info = "客户编号:" + ua.getHmph() + "\n" +
//                    "总金额 : " + Prices.m2(Double.parseDouble(ua.getZje())) + "\n"
//                    + "欠费月份 : " + ua.getQfyf() + "\n" + "违约金 : "
//                    + Prices.m2(Double.parseDouble(ua.getWyj())) + "\n" + "水费金额 : "
//                    + Prices.m2(Double.parseDouble(ua.getJe()));
//            show_userinfo.setText(info);
//        }
//    }
//
//    class SCbjAdapter extends BaseAdapter {
//        private Context context;
//        private LayoutInflater mInflater = null;
//
//        public SCbjAdapter(Context context) {
//            this.context = context;
//            this.mInflater = LayoutInflater.from(context);
//        }
//
//        public int getCount() {
//            return dowcbj.size();
//        }
//
//        public Object getItem(int position) {
//            return position;
//        }
//
//        public long getItemId(int position) {
//            return position;
//        }
//
//        public View getView(int position, View convertView, ViewGroup parent) {
//            ViewHolder holder = null;
//            if (convertView == null) {
//                holder = new ViewHolder();
//                convertView = mInflater.inflate(R.layout.cbjinfo, null);
//                holder.tv_huming = (TextView) convertView.findViewById(R.id.tv_huming);
//                holder.tv_dizhi = (TextView) convertView.findViewById(R.id.tv_dizhi);
//                holder.tv_sybiaoshu = (TextView) convertView.findViewById(R.id.tv_sybiaoshu);
//                holder.tv_syshuiliang = (TextView) convertView.findViewById(R.id.tv_syshuiliang);
//                holder.tv_bybiaoshu = (TextView) convertView.findViewById(R.id.tv_bybiaoshu);
//                holder.tv_byshuiliang = (TextView) convertView.findViewById(R.id.tv_byshuiliang);
//                convertView.setTag(holder);
//            } else {
//                holder = (ViewHolder) convertView.getTag();
//            }
//            Cbj cjb = dowcbj.get(position);
//            holder.tv_huming.setText(cjb.getHm());
//            holder.tv_dizhi.setText(cjb.getDz());
//            holder.tv_sybiaoshu.setText(cjb.getCmds0());
//            holder.tv_syshuiliang.setText(cjb.getSysl0());
//            holder.tv_bybiaoshu.setText(cjb.getCmds1());
//            holder.tv_byshuiliang.setText(cjb.getSysl1());
//            return convertView;
//        }
//    }
//
//    static class ViewHolder {
//        public TextView tv_huming;
//        public TextView tv_dizhi;
//        public TextView tv_sybiaoshu;
//        public TextView tv_syshuiliang;
//        public TextView tv_bybiaoshu;
//        public TextView tv_byshuiliang;
//    }
//
//
//}
