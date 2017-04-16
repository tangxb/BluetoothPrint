package com.yq.tasks.views;

/**
 * Created by gbh on 16/12/7.
 */

public interface BaseView {

    String userId();

    void showPress();

    void hidePress();

    void onFaile(String msg);

    void onSuccess();
}
