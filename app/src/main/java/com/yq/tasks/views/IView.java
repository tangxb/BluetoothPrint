package com.yq.tasks.views;

import com.yq.model.CbData;
import com.yq.model.UserArrears;

/**
 * Created by gbh on 16/12/7.
 */

public interface IView extends BaseView {

    void onDownSuccess(CbData cbData);

    void onDownUserInfo(UserArrears userArrears);
}
