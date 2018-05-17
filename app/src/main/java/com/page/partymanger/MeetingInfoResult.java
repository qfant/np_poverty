package com.page.partymanger;

import com.framework.domain.response.BaseResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shucheng.qu on 2017/8/30.
 */

public class MeetingInfoResult extends BaseResult {


    public MeetingData data;

    public static class MeetingData implements Serializable {
        public int totalNum;
        public List<MeetingInfoItem> statementList;
    }

    public static class MeetingInfoItem implements Serializable {
        public String address;
        public String startdate;
        public String enddate;
        public String name;
        public int id;
        public String headpic;
        public String title;
    }
}
