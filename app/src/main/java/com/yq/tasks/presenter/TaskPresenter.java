package com.yq.tasks.presenter;

import com.yq.model.UpCbjBean;

/**
 * Created by gbh on 16/12/7.
 */

public interface TaskPresenter {

    /**
     * 登录
     *
     * @param id
     * @param pwd
     */
    void loginData(String id, String pwd);

    /**
     * 查询
     *
     * @param bqh
     * @param dh
     * @param date
     */
    void queryData(String bqh, String dh, String date);

    /**
     * 下载数据
     *
     * @param id
     * @param fbh
     */
    void downData(String id, String fbh);

    /**
     * 获取用户信息
     *
     * @param id
     * @param fbh
     */
    void getUserArrearsInfo(String id, String fbh);

    /**
     * 上传电话
     *
     * @param hmph
     * @param Dh
     */
    void uploadPhone(String hmph, String Dh);

    /**
     * 上传缴费
     *
     * @param id
     * @param je
     * @param sid
     */
    void upPayData(String id, String je, String sid);

    /**
     * 上传性质
     *
     * @param id
     * @param xz
     */
    void upXzData(String id, String xz);


    /**
     * 上传抄表记录
     *
     * @param cbj
     */
    void upDzbqData(UpCbjBean cbj, String userId);
}
