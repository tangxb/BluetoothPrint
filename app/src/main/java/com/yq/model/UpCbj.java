package com.yq.model;

import com.smtlibrary.utils.JsonUtils;

import java.util.List;

/**
 * Created by gbh on 16/12/9.
 */

public class UpCbj {
    private String totals;
    private List<UpCbjBean> DATA;

    public UpCbj(List<UpCbjBean> DATA) {
        this.totals = "1";
        this.DATA = DATA;
    }

    @Override
    public String toString() {
        return JsonUtils.serialize(this);
    }
}
