package com.page.map;

import com.framework.domain.response.BaseResult;

import java.util.List;

/**
 * Created by chenxi.cui on 2017/9/30.
 */

public class PartyBaranchResult extends BaseResult {
    public PartyBaranchData data;


    public static class PartyBaranchData implements BaseData {
        public String content;
    }

    public static class NearbyItem implements BaseData {
        public String juli;
        public String manager;
        public String id;
        public String managerid;
        public double longitude;
        public double latitude;
        public String address;
        public String phone;
        public String name;
        public String intro;
    }
}
