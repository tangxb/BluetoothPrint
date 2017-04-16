package com.yq.tasks.views;

import com.yq.model.LoginBean;

/**
 * @author tank
 * @time 2017/3/22  18:15
 * @desc ${TODD}
 */


public interface LoginView extends BaseView {

    void onLoginSuccess(LoginBean cbData);

}
