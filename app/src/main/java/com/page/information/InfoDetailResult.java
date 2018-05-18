package com.page.information;

import com.framework.domain.response.BaseResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shucheng.qu on 2017/8/30.
 */

public class InfoDetailResult extends BaseResult {


    public InfoDetailData data;

    public static class InfoDetailData implements Serializable {
        public InfoDetail infoDetialResult;
    }

    public static class InfoDetail implements Serializable {
        public String address;
        public int id;
        public String phone;
        public String name;
    }
}
