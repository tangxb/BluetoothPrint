package com.yq.model;

import java.util.List;

/**
 * Created by gbh on 16/12/2.
 */

public class CbData {
    private String ret;
    private String msg;
    private int totals;
    private List<CbjDetail> DATA;

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

    public List<CbjDetail> getDATA() {
        return DATA;
    }

    public void setDATA(List<CbjDetail> DATA) {
        this.DATA = DATA;
    }


    public  class CbjDetail {
        private String GROUPID;
        private String GROUPNAME;
        private String GROUPNUM;
        private List<Cbj> GPOUPDATA;

        public String getGROUPID() {
            return GROUPID;
        }

        public void setGROUPID(String GROUPID) {
            this.GROUPID = GROUPID;
        }

        public String getGROUPNAME() {
            return GROUPNAME;
        }

        public void setGROUPNAME(String GROUPNAME) {
            this.GROUPNAME = GROUPNAME;
        }

        public String getGROUPNUM() {
            return GROUPNUM;
        }

        public void setGROUPNUM(String GROUPNUM) {
            this.GROUPNUM = GROUPNUM;
        }

        public List<Cbj> getGPOUPDATA() {
            return GPOUPDATA;
        }

        public void setGPOUPDATA(List<Cbj> GPOUPDATA) {
            this.GPOUPDATA = GPOUPDATA;
        }
    }

}
