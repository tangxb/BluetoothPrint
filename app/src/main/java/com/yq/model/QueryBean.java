package com.yq.model;

import java.util.List;

/**
 * @author tank
 * @time 2017/3/29  14:31
 * @desc ${TODD}
 */


public class QueryBean {

    /**
     * ret : ok
     * msg : 成功
     * totals : 0
     * DATA : [{"sybs":"128","bcye":"0.20","ysxz":"服务100%","xzje":"96.00","bybs":"176","jfje":"96.00","sysl":"48","jfrq":"2017-03-19 09:50:00","ysqj":"201703","bqsf":"96.00"}]
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
         * sybs : 128
         * bcye : 0.20
         * ysxz : 服务100%
         * xzje : 96.00
         * bybs : 176
         * jfje : 96.00
         * sysl : 48
         * jfrq : 2017-03-19 09:50:00
         * ysqj : 201703
         * bqsf : 96.00
         */

        private String sybs;
        private String bcye;
        private String ysxz;
        private String xzje;
        private String bybs;
        private String jfje;
        private String sysl;
        private String jfrq;
        private String ysqj;
        private String bqsf;
        private String scye;

        public String getSybs() {
            return sybs;
        }

        public void setSybs(String sybs) {
            this.sybs = sybs;
        }

        public String getBcye() {
            return bcye;
        }

        public void setBcye(String bcye) {
            this.bcye = bcye;
        }

        public String getYsxz() {
            return ysxz;
        }

        public void setYsxz(String ysxz) {
            this.ysxz = ysxz;
        }

        public String getXzje() {
            return xzje;
        }

        public void setXzje(String xzje) {
            this.xzje = xzje;
        }

        public String getBybs() {
            return bybs;
        }

        public void setBybs(String bybs) {
            this.bybs = bybs;
        }

        public String getJfje() {
            return jfje;
        }

        public void setJfje(String jfje) {
            this.jfje = jfje;
        }

        public String getSysl() {
            return sysl;
        }

        public void setSysl(String sysl) {
            this.sysl = sysl;
        }

        public String getJfrq() {
            return jfrq;
        }

        public void setJfrq(String jfrq) {
            this.jfrq = jfrq;
        }

        public String getYsqj() {
            return ysqj;
        }

        public void setYsqj(String ysqj) {
            this.ysqj = ysqj;
        }

        public String getBqsf() {
            return bqsf;
        }

        public void setBqsf(String bqsf) {
            this.bqsf = bqsf;
        }

        public String getScye() {
            return scye;
        }

        public void setScye(String scye) {
            this.scye = scye;
        }
    }
}
