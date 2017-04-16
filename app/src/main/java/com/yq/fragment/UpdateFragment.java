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
import com.yq.fragment.item.UploadDhFragment;
import com.yq.fragment.item.UploadJfFragment;
import com.yq.fragment.item.UploadXzFragment;
import com.yq.yqwater.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mac on 16/11/19.
 */

public class UpdateFragment extends Fragment {


    @Bind(R.id.indicator)
    RVPIndicator mIndicator;
    @Bind(R.id.viewpager)
    ViewPager mViewPager;

    private List<Fragment> mTabContents;
    private Adapter mAdapter;
    private List<String> mDatas;

    public static UpdateFragment newInstance() {
        UpdateFragment f = new UpdateFragment();
        return f;
    }


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.update, container, false);
        ButterKnife.bind(this, view);
        initData();
        initView();
        return view;
    }

    private void initData() {
        mDatas = Arrays.asList(getActivity().getResources().getStringArray(R.array.home_tabs));
        mTabContents = new ArrayList<>();
        mTabContents.add(UploadDhFragment.newInstance());
        mTabContents.add(UploadXzFragment.newInstance());
        mTabContents.add(UploadJfFragment.newInstance());
        mAdapter = new Adapter(getActivity().getSupportFragmentManager(), mTabContents);
    }


    private void initView() {
        mIndicator.setTabItemTitles(mDatas);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mIndicator.setViewPager(mViewPager, 0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
