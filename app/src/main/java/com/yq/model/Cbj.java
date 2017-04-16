package com.yq.model;

import com.smtlibrary.utils.JsonUtils;

/**
 * Created by mac on 16/11/20.
 * 水费的算法就是　5吨保底不足按5吨计算×单价  ， 超过就是按单价*实际
 * 实际应收＝单价*实际读＋上月结余
 */

public class Cbj {
    /*
    GROUPID	区域编号
    GROUPNAME	区域编号名字
    Hm	户名
    Dz	地址
    Zsbh	终身编号
    cmds0	上月表数
    sysl0	上月水量
    cmds1	本月表数
    sysl1	本月水量
    je	金额
    scjy	上次节余
    dh	电话
    sbwz	水表位置
    ysxz	用水性质
    sh	生活
    xz	行政
    gy	工业
    jy	经营
    tz	特种
    qybh	区域编号
    qymc	区域名称
    zbbh	总表编号
    tzbh	表身号
    kj	口径
    sbpp	水表品牌
    sbxh	水表型号
    cbye	抄表月份
    sfy	收费员
    csdu	初始吨位
    rq	抄表日期
    qsf	欠水费
    fbh	分表号
    Jd	经度
    Wd	纬度
    dds 代表 底吨数

    btbh  代表 报停保户

    dzbq 代表标签号

    dk  代表银行扣款

    yjMoney  预交金额

    hmph  用户编号
    */

    private String GROUPID;
    private String GROUPNAME;
    private String hmph;
    private String Hm;
    private String Dz;
    private String Zsbh;
    private String cmds0;
    private String sysl0;
    private String cmds1;
    private String sysl1;
    private String je;
    private String scjy;
    private String dh;
    private String sbwz;
    private String ysxz;
    private String sh;
    private String xz;
    private String gy;
    private String jy;
    private String tz;
    private String qybh;
    private String qymc;
    private String zbbh;
    private String tzbh;
    private String kj;
    private String sbpp;
    private String sbxh;
    private String cbye;
    private String sfy;
    private String csdu;
    private String rq;
    private String qsf;
    private String fbh;
    private String Jd;
    private String Wd;
    private String dds;
    private String btbh;
    private String dzbq;
    private String dk;
    private int yjMoney;
    private int isUpload;
    private int isChaoBiao;
    private int yfs;//新增抄表月数
//    private String zjm;


    public int getYfs() {
        return yfs;
    }

    public void setYfs(int yfs) {
        this.yfs = yfs;
    }

    public Cbj() {

    }

    public String getDzbq() {
        return dzbq;
    }

    public void setDzbq(String dzbq) {
        this.dzbq = dzbq;
    }

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

    public String getHm() {
        return Hm;
    }

    public void setHm(String hm) {
        Hm = hm;
    }

    public String getDz() {
        return Dz;
    }

    public void setDz(String dz) {
        Dz = dz;
    }

    public String getZsbh() {
        return Zsbh;
    }

    public void setZsbh(String zsbh) {
        Zsbh = zsbh;
    }

    public String getCmds0() {
        return cmds0;
    }

    public void setCmds0(String cmds0) {
        this.cmds0 = cmds0;
    }

    public String getSysl0() {
        return sysl0;
    }

    public void setSysl0(String sysl0) {
        this.sysl0 = sysl0;
    }

    public String getCmds1() {
        return cmds1;
    }

    public void setCmds1(String cmds1) {
        this.cmds1 = cmds1;
    }

    public String getSysl1() {
        return sysl1;
    }

    public void setSysl1(String sysl1) {
        this.sysl1 = sysl1;
    }

    public String getJe() {
        return je;
    }

    public void setJe(String je) {
        this.je = je;
    }

    public String getScjy() {
        return scjy;
    }

    public void setScjy(String scjy) {
        this.scjy = scjy;
    }

    public String getDh() {
        return dh;
    }

    public void setDh(String dh) {
        this.dh = dh;
    }

    public String getSbwz() {
        return sbwz;
    }

    public void setSbwz(String sbwz) {
        this.sbwz = sbwz;
    }

    public String getYsxz() {
        return ysxz;
    }

    public void setYsxz(String ysxz) {
        this.ysxz = ysxz;
    }

    public String getSh() {
        return sh;
    }

    public void setSh(String sh) {
        this.sh = sh;
    }

    public String getXz() {
        return xz;
    }

    public void setXz(String xz) {
        this.xz = xz;
    }

    public String getGy() {
        return gy;
    }

    public void setGy(String gy) {
        this.gy = gy;
    }

    public String getJy() {
        return jy;
    }

    public void setJy(String jy) {
        this.jy = jy;
    }

    public String getTz() {
        return tz;
    }

    public void setTz(String tz) {
        this.tz = tz;
    }

    public String getQybh() {
        return qybh;
    }

    public void setQybh(String qybh) {
        this.qybh = qybh;
    }

    public String getQymc() {
        return qymc;
    }

    public void setQymc(String qymc) {
        this.qymc = qymc;
    }

    public String getZbbh() {
        return zbbh;
    }

    public void setZbbh(String zbbh) {
        this.zbbh = zbbh;
    }

    public String getTzbh() {
        return tzbh;
    }

    public void setTzbh(String tzbh) {
        this.tzbh = tzbh;
    }

    public String getKj() {
        return kj;
    }

    public void setKj(String kj) {
        this.kj = kj;
    }

    public String getSbpp() {
        return sbpp;
    }

    public void setSbpp(String sbpp) {
        this.sbpp = sbpp;
    }

    public String getSbxh() {
        return sbxh;
    }

    public void setSbxh(String sbxh) {
        this.sbxh = sbxh;
    }

    public String getCbye() {
        return cbye;
    }

    public void setCbye(String cbye) {
        this.cbye = cbye;
    }

    public String getSfy() {
        return sfy;
    }

    public void setSfy(String sfy) {
        this.sfy = sfy;
    }

    public String getCsdu() {
        return csdu;
    }

    public void setCsdu(String csdu) {
        this.csdu = csdu;
    }

    public String getRq() {
        return rq;
    }

    public void setRq(String rq) {
        this.rq = rq;
    }

    public String getQsf() {
        return qsf;
    }

    public void setQsf(String qsf) {
        this.qsf = qsf;
    }

    public String getFbh() {
        return fbh;
    }

    public void setFbh(String fbh) {
        this.fbh = fbh;
    }

    public String getJd() {
        return Jd;
    }

    public void setJd(String jd) {
        Jd = jd;
    }

    public String getWd() {
        return Wd;
    }

    public void setWd(String wd) {
        Wd = wd;
    }

    public String getHmph() {
        return hmph;
    }

    public void setHmph(String hmph) {
        this.hmph = hmph;
    }

    public String getDds() {
        return dds;
    }

    public void setDds(String dds) {
        this.dds = dds;
    }

    public String getBtbh() {
        return btbh;
    }

    public void setBtbh(String btbh) {
        this.btbh = btbh;
    }

    public String getDk() {
        return dk;
    }

    public void setDk(String dk) {
        this.dk = dk;
    }

    public int getYjMoney() {
        return yjMoney;
    }

    public void setYjMoney(int yjMoney) {
        this.yjMoney = yjMoney;
    }

    public int getIsUpload() {
        return isUpload;
    }

    public void setIsUpload(int isUpload) {
        this.isUpload = isUpload;
    }

    public int getIsChaoBiao() {
        return isChaoBiao;
    }

    public void setIsChaoBiao(int isChaoBiao) {
        this.isChaoBiao = isChaoBiao;
    }

    @Override
    public String toString() {
        return JsonUtils.serialize(this);
    }
}
