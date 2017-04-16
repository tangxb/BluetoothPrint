package com.yq.yqwater;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.smtlibrary.utils.LogUtils;
import com.yq.tools.DBManager;

public class MeApplcition extends Application {

    public static RequestQueue rq;
    public static Context context;
    public static DBManager mgr;

    public void onCreate() {
        context = getApplicationContext();
        super.onCreate();
        LogUtils.LOG_DEBUG = BuildConfig.DEBUG;
        rq = Volley.newRequestQueue(getApplicationContext());
        System.out.println("MeApplcition");
    }

    public static RequestQueue getHttpQueue() {
        return rq;
    }

}
