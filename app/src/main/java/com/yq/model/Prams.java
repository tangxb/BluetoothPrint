package com.yq.model;

import com.smtlibrary.utils.JsonUtils;

/**
 * Created by gbh on 16/12/7.
 *
 */

public class Prams {
    private String id;
    private String fbh;

    private String hmph;
    private String Dh;

    private String JE;
    private String sfy;

    private String FBH;
    private String ysxz;
//    //密码
//    private String pwd;
//
//    public String getPwd() {
//        return pwd;
//    }
//
//    public void setPwd(String pwd) {
//        this.pwd = pwd;
//    }
    //增加标签号
/*    private String dzbp;

    public String getDzbp() {
        return dzbp;
    }

    public void setDzbp(String dzbp) {
        this.dzbp = dzbp;
    }*/

    public Prams() {
        FBH = "";
    }

    public Prams(String id, String fbh) {
        this.id = id;
        this.fbh = fbh;
        FBH = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFbh() {
        return fbh;
    }

    public void setFbh(String fbh) {
        this.fbh = fbh;
    }

    public String getHmph() {
        return hmph;
    }

    public void setHmph(String hmph) {
        this.hmph = hmph;
    }

    public String getDh() {
        return Dh;
    }

    public void setDh(String dh) {
        Dh = dh;
    }

    public String getJE() {
        return JE;
    }

    public void setJE(String JE) {
        this.JE = JE;
    }

    public String getSfy() {
        return sfy;
    }

    public void setSfy(String sfy) {
        this.sfy = sfy;
    }

    public String getFBH() {
        return FBH;
    }

    public void setFBH(String FBH) {
        this.FBH = FBH;
    }

    public String getYsxz() {
        return ysxz;
    }

    public void setYsxz(String ysxz) {
        this.ysxz = ysxz;
    }

    @Override
    public String toString() {
        return JsonUtils.serialize(this);
    }
}
