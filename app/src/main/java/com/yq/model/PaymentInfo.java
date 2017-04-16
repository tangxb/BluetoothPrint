package com.yq.model;

/**
 * Created by mac on 16/11/23.
 */

public class PaymentInfo {

    private String hmph; //户名编号
    private String hm;   //户名
    private String CMDS0;  //上月抄表
    private String CMDS;   //本月抄表
    private String SYSL1;  //实用水量
    private String WYJ;    //违约金
    private String SCJY;   //上次节余
    private String JE;     //水费金额

    public PaymentInfo() {

    }

    public String getHmph() {
        return hmph;
    }

    public void setHmph(String hmph) {
        this.hmph = hmph;
    }

    public String getHm() {
        return hm;
    }

    public void setHm(String hm) {
        this.hm = hm;
    }

    public String getCMDS0() {
        return CMDS0;
    }

    public void setCMDS0(String CMDS0) {
        this.CMDS0 = CMDS0;
    }

    public String getCMDS() {
        return CMDS;
    }

    public void setCMDS(String CMDS) {
        this.CMDS = CMDS;
    }

    public String getSYSL1() {
        return SYSL1;
    }

    public void setSYSL1(String SYSL1) {
        this.SYSL1 = SYSL1;
    }

    public String getWYJ() {
        return WYJ;
    }

    public void setWYJ(String WYJ) {
        this.WYJ = WYJ;
    }

    public String getSCJY() {
        return SCJY;
    }

    public void setSCJY(String SCJY) {
        this.SCJY = SCJY;
    }

    public String getJE() {
        return JE;
    }

    public void setJE(String JE) {
        this.JE = JE;
    }
}
