package com.page.map;

import com.framework.domain.response.BaseResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenxi.cui on 2017/9/30.
 */

public class NearbyResult extends BaseResult {
    public NearbyData data;


    public static class NearbyData implements BaseData {
        public List<NearbyItem> partyBranchList;
    }

    public static class NearbyItem implements BaseData {
        public String juli;
        public String manager;
        public String id;
        public double longitude;
        public double latitude;
        public String address;
        public String phone;
        public String name;
        public String intro;
    }
}
