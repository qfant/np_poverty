package com.page.political;

import com.framework.domain.param.BaseParam;

/**
 * Created by chenxi.cui on 2018/5/16.
 */

public class SignParam extends BaseParam {
    public double longitude;
    public double latitude;
    public int id;

    public SignParam(double latitude, double longitude) {
        super();
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
