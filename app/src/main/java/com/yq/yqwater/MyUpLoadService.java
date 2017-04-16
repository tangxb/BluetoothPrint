package com.yq.yqwater;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;

import com.smtlibrary.utils.LogUtils;
import com.smtlibrary.utils.PreferenceUtils;
import com.yq.model.UpCbjBean;
import com.yq.tasks.presenter.TaskPresenter;
import com.yq.tasks.presenter.TaskPresenterImpl;

public class MyUpLoadService extends Service {

    private boolean isRun;
    private TaskPresenter taskPresenter;
    private String userId;

    public MyUpLoadService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        taskPresenter = new TaskPresenterImpl();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        isRun = true;
        userId = PreferenceUtils.getString(this, "userId", "110101");
        UploadData uploadData = new UploadData();
        uploadData.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        isRun = false;
        super.onDestroy();
    }


    class UploadData extends Thread {

        @Override
        public void run() {
            super.run();
            while (isRun) {
                try {
                    LogUtils.sysout("====upload:", getId());
                    Thread.sleep(5000);
                    if (null != MeApplcition.mgr) {
                        UpCbjBean cbj = MeApplcition.mgr.getFirstData();
                        if (!TextUtils.isEmpty(cbj.getHmph()))
                            taskPresenter.upDzbqData(cbj, userId);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

}
