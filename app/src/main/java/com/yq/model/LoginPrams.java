package com.yq.model;

import com.smtlibrary.utils.JsonUtils;

/**
 * @author tank
 * @time 2017/3/23  9:21
 * @desc ${TODD}
 */


public class LoginPrams {
    private String id;//用户名
    private String psw;//密码


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }


    //    public LoginPrams() {
    //    }


    public LoginPrams(String id, String psw) {
        this.id = id;
        this.psw = psw;
    }


    @Override
    public String toString() {
        return JsonUtils.serialize(this);
    }
}
