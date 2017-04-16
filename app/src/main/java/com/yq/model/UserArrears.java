package com.yq.model;

import java.util.List;

/**
 * Created by mac on 16/11/29.
 * 用户欠费
 */

public class UserArrears {

    private String ret;
    private String msg;
    private int totals;
    private List<User> DATA;

    public class User {

        private String zje; //总金额
        private String qfyf; //欠费月份
        private String wyj; //违约金
        private String je;  //水费金额
        private String hmph; //客户编号


        public String getZje() {
            return zje;
        }

        public void setZje(String zje) {
            this.zje = zje;
        }

        public String getQfyf() {
            return qfyf;
        }

        public void setQfyf(String qfyf) {
            this.qfyf = qfyf;
        }

        public String getWyj() {
            return wyj;
        }

        public void setWyj(String wyj) {
            this.wyj = wyj;
        }

        public String getJe() {
            return je;
        }

        public void setJe(String je) {
            this.je = je;
        }

        public String getHmph() {
            return hmph;
        }

        public void setHmph(String hmph) {
            this.hmph = hmph;
        }
    }

    public UserArrears(String ret, String msg, int totals, List<User> DATA) {
        this.ret = ret;
        this.msg = msg;
        this.totals = totals;
        this.DATA = DATA;
    }

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getTotals() {
        return totals;
    }

    public void setTotals(int totals) {
        this.totals = totals;
    }

    public List<User> getDATA() {
        return DATA;
    }

    public void setDATA(List<User> DATA) {
        this.DATA = DATA;
    }
}
