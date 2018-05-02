package com.page.home.model;

import com.framework.domain.response.BaseResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shucheng.qu on 2017/9/13.
 */

public class NoticeResult extends BaseResult {

    public Data data;

    public static class Data implements Serializable {
        public List<Datas> datas;

        public static List<Datas> mock() {
            List<Datas> list = new ArrayList<>();
            list.add(new Datas());
            list.add(new Datas());
            return list;
        }

        public static class Datas implements Serializable {
            public int id;
            public String title = "最美公务员";
            public String content = "最美公务员";
            public String createtime;
        }
    }
}
