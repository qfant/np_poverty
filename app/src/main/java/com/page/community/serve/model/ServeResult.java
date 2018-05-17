package com.page.community.serve.model;

import com.framework.domain.response.BaseResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shucheng.qu on 2017/8/24.
 */

public class ServeResult extends BaseResult {


    public Data data;

    public static class Data implements Serializable {
        public int totalNum;
        public List<WaterList> waterList;


        public static class WaterList implements Serializable {
            public String id;
            public String title;
            public String phone;
            public String address;
            public String pic = "";
            public String createtime = "";
            public String intro = "";
            public String type = "";
        }
    }
}
