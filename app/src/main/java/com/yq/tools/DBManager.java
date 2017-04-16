package com.yq.tools;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yq.model.CbData;
import com.yq.model.Cbj;
import com.yq.model.UpCbjBean;
import com.yq.utils.TimeUtils;

/**
 * Created by mac on 16/11/30.
 */

public class DBManager {

    private DBHelper helper;
    private SQLiteDatabase db;

    public DBManager(Context context) {
        helper = new DBHelper(context);
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        db = helper.getWritableDatabase();
    }


    public void add(CbData cbData) {
        try {
            if (null != db) {
                db.close();
                db = helper.getWritableDatabase();
            }
            db.beginTransaction();  //开始事务
            if (db.isOpen()) {
                for (CbData.CbjDetail b : cbData.getDATA()) {
                    ContentValues grValue = new ContentValues();
                    grValue.put("GROUPID", b.getGROUPID());
                    grValue.put("GROUPNUM", b.getGROUPNUM());
                    grValue.put("GROUPNAME", b.getGROUPNAME());
                    db.insertOrThrow("gpinfo", null, grValue);
                    for (Cbj cbj : b.getGPOUPDATA()) {
                        ContentValues values = new ContentValues();
                        values.put("dzbq", cbj.getDzbq());
                        values.put("hmph", cbj.getHmph());
                        values.put("cmds0", cbj.getCmds0());
                        values.put("cmds1", cbj.getCmds1());
                        values.put("hm", cbj.getHm());
                        values.put("rq", cbj.getRq());
                        values.put("dz", cbj.getDz());
                        values.put("qsf", cbj.getQsf());
                        values.put("sysl0", cbj.getSysl0());
                        values.put("sysl1", cbj.getSysl1());
                        values.put("dh", cbj.getDh());

                        values.put("ysxz", cbj.getYsxz());
                        values.put("scjy", cbj.getScjy());
                        values.put("dds", cbj.getDds());
                        values.put("btbh", cbj.getBtbh());
                        values.put("dk", cbj.getDk());
                        values.put("sfy", cbj.getSfy());
                        values.put("cbye", cbj.getCbye());
                        values.put("Jd", cbj.getJd());
                        values.put("Wd", cbj.getWd());
                        values.put("fbh", cbj.getFbh());
                        values.put("yfs", cbj.getYfs());
                        db.insertOrThrow("sbinfo", null, values);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.setTransactionSuccessful();
            db.endTransaction();
        }
    }

    /**
     * 查询抄表
     * @param dzbp
     * @param cbye
     * @return
     */
    public Cbj queryTheCursor(String dzbp, String cbye) {
        Cbj cbj = new Cbj();
        Cursor c = db.rawQuery("SELECT * FROM sbinfo where dzbq = ? and cbye = ?", new String[]{dzbp, cbye});
        while (c.moveToNext()) {
            getData(cbj, c);
            return cbj;
        }
        c.close();
        return null;
    }

    /**
     * 查询用户标签号
     * @param dzbp
     * @return
     */
    public Cbj queryTheCursor(String dzbp) {
        Cbj cbj = new Cbj();
        Cursor c = db.rawQuery("SELECT * FROM sbinfo where dzbq = ?", new String[]{dzbp});
        while (c.moveToNext()) {
            getData(cbj, c);
            return cbj;
        }
        c.close();
        return null;
    }
    /**
     * 根据用户编号查询信息
     * @param hmph
     * @return
     */
    public Cbj queryByHmphCursor(String hmph) {
        Cbj cbj = new Cbj();
        Cursor c = db.rawQuery("SELECT * FROM sbinfo where hmph = ?", new String[]{hmph});
        while (c.moveToNext()) {
            getData(cbj, c);
            return cbj;
        }
        c.close();
        return null;
    }

    private void getData(Cbj cbj, Cursor c) {
        cbj.setDzbq(c.getString(c.getColumnIndex("dzbq")));
        cbj.setDz(c.getString(c.getColumnIndex("dz")));
        cbj.setCmds0(c.getString(c.getColumnIndex("cmds0")));
        cbj.setHm(c.getString(c.getColumnIndex("hm")));
        cbj.setCmds1(c.getString(c.getColumnIndex("cmds1")));
        cbj.setRq(c.getString(c.getColumnIndex("rq")));
        cbj.setQsf(c.getString(c.getColumnIndex("qsf")));
        cbj.setHmph(c.getString(c.getColumnIndex("hmph")));
        cbj.setSysl0(c.getString(c.getColumnIndex("sysl0")));
        cbj.setSysl1(c.getString(c.getColumnIndex("sysl1")));
        cbj.setYsxz(c.getString(c.getColumnIndex("ysxz")));
        cbj.setCbye(c.getString(c.getColumnIndex("cbye")));
        cbj.setDh(c.getString(c.getColumnIndex("dh")));

        cbj.setScjy(c.getString(c.getColumnIndex("scjy")));
        cbj.setDds(c.getString(c.getColumnIndex("dds")));
        cbj.setBtbh(c.getString(c.getColumnIndex("btbh")));
        cbj.setDk(c.getString(c.getColumnIndex("dk")));
        cbj.setSfy(c.getString(c.getColumnIndex("sfy")));
        cbj.setIsChaoBiao(c.getInt(c.getColumnIndex("isChaoBiao")));
        cbj.setIsUpload(c.getInt(c.getColumnIndex("isUpload")));
        cbj.setYjMoney(c.getInt(c.getColumnIndex("yjMoney")));
        cbj.setYfs(c.getInt(c.getColumnIndex("yfs")));
    }


    /**
     * 获取一条数据
     *
     * @return
     */
    public UpCbjBean getFirstData() {
        UpCbjBean cbj = new UpCbjBean();
        if (db.isOpen()) {
            Cursor c = db.rawQuery("SELECT * FROM sbinfo where isUpload = 0 and isChaoBiao = 1 ", null);
            if (c.moveToFirst()) {
                cbj.setHmph(c.getString(c.getColumnIndex("hmph")));
                cbj.setRq(c.getString(c.getColumnIndex("rq")));
                cbj.setCmds1(c.getString(c.getColumnIndex("cmds1")));
                cbj.setFBH(c.getString(c.getColumnIndex("fbh")));
            }
            c.close();
        }
        return cbj;
    }

    /**
     * 修改上传标志
     *
     * @param hmph
     * @param rq
     */
    public void updateUpload(String hmph, String rq) {
        ContentValues values = new ContentValues();
        values.put("hmph", hmph);
        values.put("rq", rq);
        values.put("isUpload", 1);
        db.update("sbinfo", values, "hmph =? and rq = ?", new String[]{hmph, rq});
    }

    /**
     * 根据用户标签号查询预交的钱
     *
     * @return
     */
    public int selectBydzbqYjMoney(String dzbq) {

        if (db.isOpen()) {
            Cursor c = db.rawQuery("SELECT * FROM sbinfo where isUpload = 1 and isChaoBiao = 1 ", null);
            if (c.moveToFirst()) {
                return c.getInt(c.getColumnIndex("yjMoney"));

            }
            c.close();
        }
        return 0;
    }
    /**
     * 根据用户编号查询预交的钱
     *
     * @return
     */
    public int selectYjMoney(String hmph) {

        if (db.isOpen()) {
            Cursor c = db.rawQuery("SELECT * FROM sbinfo where isUpload = 1 and isChaoBiao = 1 ", null);
            if (c.moveToFirst()) {
                return c.getInt(c.getColumnIndex("yjMoney"));

            }
            c.close();
        }
        return 0;
    }


    /**
     * 修改预交水费
     *
     * @param hmph
     * @param jyMoney
     * @return
     */
    public boolean updateYjMoney(String hmph,String jyMoney) {
        ContentValues values = new ContentValues();
        values.put("yjMoney", jyMoney);
        db.update("sbinfo", values, "hmph =?", new String[]{hmph});
        return true;
    }


    /**
     * 修改本月表数
     *
     * @param dzbp
     * @param beny
     * @param bejy
     * @param jyMoney
     * @param sysl1
     * @param cbye
     * @return
     */
    public boolean updatebney(String dzbp, String beny, String bejy, double jyMoney, String sysl1, String cbye) {
        ContentValues values = new ContentValues();
        values.put("cmds1", beny);
        values.put("scjy", bejy);
        values.put("yjMoney", jyMoney);
        values.put("isChaoBiao", 1);
        values.put("sysl1", sysl1);
        values.put("rq", TimeUtils.getCurrentTimeRq());
        values.put("isUpload", 0);
        db.update("sbinfo", values, "dzbq =? and cbye = ?", new String[]{dzbp, cbye});
        return true;
    }


    /**
     * 查询数据总数
     *
     * @return
     */
    public int selectChaoBiaoCount(String cbye) {
        Cursor c = db.rawQuery("select * from sbinfo where isChaoBiao = 1  and cbye = '" + cbye + "'", null);
        return c.getCount();
    }
    /**
     * 查询本月已上传总户数
     *
     * @return
     */
    public int uploadCount(String cbye) {
        Cursor c = db.rawQuery("select * from sbinfo where isUpload = 1  and cbye = '" + cbye + "'", null);
        return c.getCount();
    }


    /**
     * 查询数据总数
     *
     * @return
     */
    public int selectTodayChaoBiaoCount() {
        String day = TimeUtils.getCurrentyyyMMdd();
        Cursor c = db.rawQuery("select * from sbinfo where isChaoBiao = 1  and rq like '%" + day + "%'", null);
        return c.getCount();
    }

    /**
     * 所有预交的钱
     *
     * @param cbye
     * @return
     */
    public int selectAllYjMoney(String cbye) {
        Cursor c = db.rawQuery("select sum(yjMoney) as num from sbinfo where isChaoBiao = 1 and cbye = '" + cbye + "'", null);
        while (c.moveToNext()) {
            return c.getInt(c.getColumnIndex("num"));
        }
        return 0;
    }

    /**
     * 当天所有预交的钱
     *
     * @return
     */
    public int selectDayAllYjMoney() {
        String day = TimeUtils.getCurrentyyyMMdd();
        //增加 or isChaoBiao = 0
        Cursor c = db.rawQuery("select sum(yjMoney) as num from sbinfo where isChaoBiao = 1 or isChaoBiao = 0 and rq like '%" + day + "%'", null);
        while (c.moveToNext()) {
            return c.getInt(c.getColumnIndex("num"));
        }
        return 0;
    }

    /**
     * @return
     */
    public int selectAlluser(String cbye) {
        Cursor c = db.rawQuery("select count(*) as num from sbinfo where cbye = '" + cbye + "' ", null);
        while (c.moveToNext()) {
            return c.getInt(c.getColumnIndex("num"));
        }
        return 0;
    }


    /**
     * 关闭数据库
     */
    public void closeDb() {
        if (null != db)
            db.close();
    }
}
