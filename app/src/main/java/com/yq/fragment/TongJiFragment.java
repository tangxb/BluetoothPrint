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
import com.yq.fragment.item.TjcxFragment;
import com.yq.fragment.item.TjtjFragment;
import com.yq.yqwater.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author tank
 * @time 2017/3/8  16:38
 * @desc ${统计}
 */


public class TongJiFragment extends Fragment {

    @Bind(R.id.indicator2)
    RVPIndicator mIndicator;
    @Bind(R.id.viewpager2)
    ViewPager mViewPager;


    private List<Fragment> mTabContents;
    private Adapter mAdapter;
    private List<String> mDatas;
    private int mType;

    public static TongJiFragment newInstance() {
        TongJiFragment f = new TongJiFragment();
        return f;
    }


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tongji, container, false);
        ButterKnife.bind(this, view);
        initData1();
        initView();
        return view;
    }

    private void initData1() {
        mDatas = Arrays.asList(getActivity().getResources().getStringArray(R.array.home_tabs2));
        mTabContents = new ArrayList<>();
        mTabContents.add(TjcxFragment.newInstance());
        mTabContents.add(TjtjFragment.newInstance());
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