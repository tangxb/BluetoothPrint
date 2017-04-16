package com.yq.tasks.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.smtlibrary.https.OkHttpUtils;
import com.smtlibrary.utils.JsonUtils;
import com.smtlibrary.utils.LogUtils;
import com.yq.model.CbData;
import com.yq.model.Dh;
import com.yq.model.LoginBean;
import com.yq.model.LoginPrams;
import com.yq.model.Prams;
import com.yq.model.QueryBean;
import com.yq.model.QueryPrams;
import com.yq.model.RetData;
import com.yq.model.UpCbj;
import com.yq.model.UpCbjBean;
import com.yq.model.UserArrears;
import com.yq.tasks.views.IView;
import com.yq.tasks.views.LoginView;
import com.yq.tasks.views.QueryView;
import com.yq.tasks.views.UView;
import com.yq.yqwater.MeApplcition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gbh on 16/12/7.
 */

public class TaskPresenterImpl implements TaskPresenter {

    private String baseUrl = "http://219.139.44.245:8086/webServiceXSD1/CbjServlet.action?method=";

    private IView iView;
    private UView uView;
    private LoginView lView;
    private QueryView qView;

    public TaskPresenterImpl(IView iView) {
        this.iView = iView;
    }

    public TaskPresenterImpl(UView uView) {
        this.uView = uView;
    }

    public TaskPresenterImpl(LoginView lView) {
        this.lView = lView;
    }

    public TaskPresenterImpl(QueryView qView) {
        this.qView = qView;
    }

    public TaskPresenterImpl() {

    }

    /**
     * 登录
     *
     * @param id
     * @param psw
     */
    @Override
    public void loginData(String id, String psw) {
        String url = baseUrl + "get_dl&&";
        lView.showPress();
        List<OkHttpUtils.Param> paramList = new ArrayList<>();
        paramList.add(new OkHttpUtils.Param("DATA", new LoginPrams(id, psw).toString()));
        OkHttpUtils.post(url, paramList, new OkHttpUtils.ResultCallBack<String>() {
            @Override
            public void onSuccess(String response) {
                //                Log.d("m520",response);
                lView.hidePress();
                try {
                    LoginBean cbData = JsonUtils.deserialize(response, LoginBean.class);
                    if (cbData.getRet().equals("ok"))
                        lView.onLoginSuccess(cbData);
                    else {
                        lView.onFaile("登录失败,请重试。");
                    }
                } catch (Exception e) {
                    //                    e.printStackTrace();
                    lView.onFaile("账号或密码错误,请重试!");
                }

            }

            @Override
            public void onFailure(Exception e) {
                lView.hidePress();
                lView.onFaile("网络异常,请重试。");
            }
        });
    }

    /**
     * 查询
     *
     * @param bqh
     * @param dh
     * @param date
     */
    @Override
    public void queryData(String bqh, String dh, String date) {

        String url = baseUrl + "get_jfmx&&";
        qView.showPress();
        List<OkHttpUtils.Param> paramList = new ArrayList<>();
        paramList.add(new OkHttpUtils.Param("DATA", new QueryPrams(bqh, dh, date).toString()));
        OkHttpUtils.post(url, paramList, new OkHttpUtils.ResultCallBack<String>() {
            @Override
            public void onSuccess(String response) {
//                Log.d("m520", response);
                qView.hidePress();
                try {
                    QueryBean  cbData = JsonUtils.deserialize(response, QueryBean.class);
                    if (cbData.getRet().equals("ok"))
                        qView.onquerySuccess(cbData);
                    else {
                        qView.onFaile("标签号或手机号不存在!");
                    }
                } catch (Exception e) {
                    //                    e.printStackTrace();
                    qView.onFaile("标签号或手机号不存在!");
                }

            }

            @Override
            public void onFailure(Exception e) {
                qView.hidePress();
                qView.onFaile("网络异常,请重试。");
            }
        });
    }

    /**
     * 获取用户欠费信息
     *
     * @param id
     * @param fbh
     */
    @Override
    public void getUserArrearsInfo(String id, String fbh) {
        String url = baseUrl + "get_qfxx&";
        iView.showPress();
        List<OkHttpUtils.Param> paramList = getParams(id, fbh);
        OkHttpUtils.post(url, paramList, new OkHttpUtils.ResultCallBack<String>() {
            @Override
            public void onSuccess(String response) {
                LogUtils.sysout("=====res", response);
                iView.hidePress();
                UserArrears cbData = JsonUtils.deserialize(response, UserArrears.class);
                if (cbData.getRet().equals("ok"))
                    iView.onDownUserInfo(cbData);
                else {
                    iView.onFaile("下载失败,请重试。");
                }

            }

            @Override
            public void onFailure(Exception e) {
                iView.hidePress();
                iView.onFaile("网络异常,请重试。");
            }
        });
    }

    /**
     * 下载数据
     *
     * @param id
     * @param fbh
     */
    @Override
    public void downData(String id, String fbh) {
        String url = baseUrl + "get_cbsj&";
        iView.showPress();
        List<OkHttpUtils.Param> paramList = getParams(id, fbh);
        OkHttpUtils.post(url, paramList, new OkHttpUtils.ResultCallBack<String>() {
            @Override
            public void onSuccess(String response) {
                LogUtils.sysout("=====res", response);
                iView.hidePress();
                try {
                    CbData cbData = JsonUtils.deserialize(response, CbData.class);
                    if (cbData.getRet().equals("ok"))
                        iView.onDownSuccess(cbData);
                    else {
                        iView.onFaile("下载失败,请重试。");
                    }
                } catch (Exception e) {
                    //                    e.printStackTrace();
                    iView.onFaile("下载失败,请重试。");
                }

            }

            @Override
            public void onFailure(Exception e) {
                iView.hidePress();
                iView.onFaile("网络异常,请重试。");
            }
        });
    }

    /**
     * 上传电话
     *
     * @param hmph
     * @param Dh
     */
    @Override
    public void uploadPhone(String hmph, String Dh) {
        String url = baseUrl + "Send_dh&";
        List<OkHttpUtils.Param> paramList = new ArrayList<>();
        Prams p = new Prams();
        p.setHmph(hmph);
        p.setDh(Dh);
        List<Prams> data = new ArrayList<>();
        data.add(p);
        paramList.add(new OkHttpUtils.Param("jsh", uView.userId()));
        paramList.add(new OkHttpUtils.Param("DATA", new Dh(data).toString()));
        LogUtils.sysout("=====req:", new Dh(data).toString());
        uView.showPress();
        OkHttpUtils.post(url, paramList, new OkHttpUtils.ResultCallBack<String>() {
            @Override
            public void onSuccess(String response) {
                LogUtils.sysout("=====res", response);
                uView.hidePress();
                RetData retData = JsonUtils.deserialize(response, RetData.class);
                if (retData.getRet().equals("ok")) {
                    uView.onSuccess();
                } else {
                    uView.onFaile("上传失败,请重试。");
                }

            }

            @Override
            public void onFailure(Exception e) {
                uView.hidePress();
                uView.onFaile("网络异常,请重试。");
            }
        });
    }

    /**
     * 上传缴费
     *
     * @param id
     * @param je
     * @param sid
     */
    @Override
    public void upPayData(String id, String je, String sid) {
        String url = "http://219.139.44.245:8086/webServiceXSD1/CbjServlet.action?method=get_sbjf&&";
        uView.showPress();
        List<OkHttpUtils.Param> paramList = new ArrayList<>();
        Prams p = new Prams();
        p.setId(id);
        p.setJE(je);
        p.setSfy(sid);
        paramList.add(new OkHttpUtils.Param("DATA", JsonUtils.serialize(p)));
        OkHttpUtils.post(url, paramList, new OkHttpUtils.ResultCallBack<String>() {
            @Override
            public void onSuccess(String response) {
                LogUtils.sysout("=====res", response);
                uView.hidePress();
                RetData retData = null;
                try {
                    retData = JsonUtils.deserialize(response, RetData.class);
                    if (retData.getRet().equals("ok")) {
                        uView.onSuccess();
                    } else {
                        uView.onFaile("上传失败,请重试。");
                    }
                } catch (Exception e) {
                    //                    e.printStackTrace();
                    uView.onFaile("上传失败,请重试。");
                }


            }

            @Override
            public void onFailure(Exception e) {
                uView.hidePress();
                uView.onFaile("网络异常,请重试。");
            }
        });
    }

    /**
     * 上传性质
     *
     * @param id 用户编号
     * @param xz 标签号
     */
    @Override
    public void upXzData(String id, String xz) {
        String url = baseUrl + "Send_xz&";
        uView.showPress();
        List<OkHttpUtils.Param> paramList = new ArrayList<>();
        Prams p = new Prams();
        p.setHmph(id);
        p.setFBH("");
        p.setYsxz(xz);
        //        p.setDzbp(dzbp);
        List<Prams> data = new ArrayList<>();
        data.add(p);
        paramList.add(new OkHttpUtils.Param("jsh", uView.userId()));
        paramList.add(new OkHttpUtils.Param("DATA", new Dh(data).toString()));
        LogUtils.sysout("=====update", new Dh(data).toString());
        OkHttpUtils.post(url, paramList, new OkHttpUtils.ResultCallBack<String>() {
            @Override
            public void onSuccess(String response) {
                //                LogUtils.sysout("=====res", response);
                Log.v("m520", response);
                uView.hidePress();
                RetData retData = JsonUtils.deserialize(response, RetData.class);
                if (retData.getRet().equals("ok")) {
                    uView.onSuccess();
                } else {
                    uView.onFaile("上传失败,请重试。");
                }

            }

            @Override
            public void onFailure(Exception e) {

                uView.hidePress();
                uView.onFaile("网络异常,请重试。");
            }
        });
    }

    /**
     * 上传抄表记录
     */
    @Override
    public void upDzbqData(final UpCbjBean cbj, String userId) {
        String url = baseUrl + "Send_data&";
        List<OkHttpUtils.Param> paramList = new ArrayList<>();
        List<UpCbjBean> data = new ArrayList<>();
        data.add(cbj);
        paramList.add(new OkHttpUtils.Param("jsh", userId));
        paramList.add(new OkHttpUtils.Param("DATA", new UpCbj(data).toString()));

        OkHttpUtils.post(url, paramList, new OkHttpUtils.ResultCallBack<String>() {
            @Override
            public void onSuccess(String response) {
                LogUtils.sysout("=====res", response);
                RetData retData = JsonUtils.deserialize(response, RetData.class);
                if (retData.getRet().equals("ok")) {
                    //在本地数据库中修改上传标志
                    MeApplcition.mgr.updateUpload(cbj.getHmph(), cbj.getRq());
                }

            }

            @Override
            public void onFailure(Exception e) {
            }
        });
    }


    @NonNull
    private List<OkHttpUtils.Param> getParams(String id, String fbh) {
        List<OkHttpUtils.Param> paramList = new ArrayList<>();
        paramList.add(new OkHttpUtils.Param("DATA", new Prams(id, fbh).toString()));
        return paramList;
    }
}
