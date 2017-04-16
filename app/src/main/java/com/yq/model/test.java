package com.yq.model;

import java.util.List;

/**
 * @author tank
 * @time 2017/3/23  14:23
 * @desc ${TODD}
 */


public class test {
    /**
     * ret : ok
     * msg : 成功
     * totals : 0
     * DATA : [{"姓名":"系统主管","操作员编码":"100000","口令":"G\\\u0007\u0018Tpp\r;"}]
     */

    private String ret;
    private String msg;
    private int totals;
    private List<DATABean> DATA;

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

    public List<DATABean> getDATA() {
        return DATA;
    }

    public void setDATA(List<DATABean> DATA) {
        this.DATA = DATA;
    }

    public static class DATABean {
        /**
         * 姓名 : 系统主管
         * 操作员编码 : 100000
         * 口令 : G\Tpp;
         */

        private String 姓名;
        private String 操作员编码;
        private String 口令;

        public String get姓名() {
            return 姓名;
        }

        public void set姓名(String 姓名) {
            this.姓名 = 姓名;
        }

        public String get操作员编码() {
            return 操作员编码;
        }

        public void set操作员编码(String 操作员编码) {
            this.操作员编码 = 操作员编码;
        }

        public String get口令() {
            return 口令;
        }

        public void set口令(String 口令) {
            this.口令 = 口令;
        }
    }
}
