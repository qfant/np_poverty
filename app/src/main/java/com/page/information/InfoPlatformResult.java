package com.page.information;

import com.framework.domain.response.BaseResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shucheng.qu on 2017/8/30.
 */

public class InfoPlatformResult extends BaseResult {


    public InfoData data;

    public static class InfoData implements Serializable {
        public int totalNum;
        public List<InfoItem> newsList;
        public List<InfoItem> infoList;
    }

    public static class InfoItem implements Serializable {
        public String address;
        public int id;
        public String phone;
        public String name;
    }
}
