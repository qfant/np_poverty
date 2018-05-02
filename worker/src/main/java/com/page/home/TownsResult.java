package com.page.home;

import com.framework.domain.response.BaseResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenxi.cui on 2017/12/12.
 */

public class TownsResult extends BaseResult {

    public TownsData data;

    /**
     * townResult	列表	JsonArray
     * townResult [i].id	id	string
     * townResult [i].name	名称	String
     * townResult [i].villages	村	JsonArray
     * townResult [i]. villages[i].name	村名	String
     * townResult [i]. villages[i].id	村id	String
     */
    public static class TownsData implements BaseData {
        public List<TownBean> towns;
    }

    public static class TownBean implements BaseData {
        public String id;
        public String name;
        public int type;
        public ArrayList<TownBean> village;
    }
}
