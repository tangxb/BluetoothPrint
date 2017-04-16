package com.yq.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smtlibrary.Indicator.RVPIndicator;
import com.yq.adapt.Adapter;
import com.yq.fragment.item.DownloadDataFragment;
import com.yq.fragment.item.DownloadQfFragment;
import com.yq.yqwater.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mac on 16/11/19.
 */

public class DownFragment extends Fragment {


    @Bind(R.id.indicator1)
    RVPIndicator mIndicator;
    @Bind(R.id.viewpager1)
    ViewPager mViewPager;


    private List<Fragment> mTabContents;
    private Adapter mAdapter;
    private List<String> mDatas;
    private int mType;

    public static DownFragment newInstance(int type) {
        DownFragment f = new DownFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mType = getArguments().getInt("type");
        }
//        Intent intent = getActivity().getIntent();
//        userName = intent.getStringExtra("userName");
//        Log.d("m520",userName);
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.down, container, false);
        ButterKnife.bind(this, view);
        initData1();
        initView();
        return view;
    }

    /**
     * 下载的
     */
    private void initData1() {
        mDatas = Arrays.asList(getActivity().getResources().getStringArray(R.array.home_tabs1));
        mTabContents = new ArrayList<>();
        mTabContents.add(DownloadDataFragment.newInstance());
        mTabContents.add(DownloadQfFragment.newInstance());
        mAdapter = new Adapter(getActivity().getSupportFragmentManager(), mTabContents);
    }

    private void initView() {
        mIndicator.setTabItemTitles(mDatas);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mIndicator.setViewPager(mViewPager, 0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
