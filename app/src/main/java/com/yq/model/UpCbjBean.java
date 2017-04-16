package com.yq.model;

import android.text.TextUtils;

/**
 * Created by gbh on 16/12/9.
 */

public class UpCbjBean {

    private String hmph;
    private String Cmds1;
    private String Rq;
    private String JD;
    private String Wd;
    private int TPYE;
    private String FBH;

    public UpCbjBean() {
        JD = "0";
        Wd = "0";
        TPYE = 0;
    }

    public String getHmph() {
        return hmph;
    }

    public void setHmph(String hmph) {
        this.hmph = hmph;
    }

    public String getCmds1() {
        return Cmds1;
    }

    public void setCmds1(String cmds1) {
        Cmds1 = cmds1;
    }

    public String getRq() {
        return Rq;
    }

    public void setRq(String rq) {
        Rq = rq;
    }

    public String getJD() {
        return JD;
    }

    public void setJD(String JD) {
        this.JD = JD;
    }

    public String getWd() {
        return Wd;
    }

    public void setWd(String wd) {
        Wd = wd;
    }

    public int getTPYE() {
        return TPYE;
    }

    public void setTPYE(int TPYE) {
        this.TPYE = TPYE;
    }

    public String getFBH() {
        return TextUtils.isEmpty(FBH) ? "01" : FBH;
    }

    public void setFBH(String FBH) {
        this.FBH = FBH;
    }
}
