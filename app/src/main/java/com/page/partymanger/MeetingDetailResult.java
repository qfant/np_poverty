package com.page.partymanger;

import com.framework.domain.response.BaseResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shucheng.qu on 2017/8/30.
 */

public class MeetingDetailResult extends BaseResult {


    public MeetingData data;

    public static class MeetingData implements Serializable {
        public int statementcount;
        public MeetingDetail meetingdetial;
        public List<MeetingMember> memberList;
    }

    public static class MeetingMember implements Serializable {
        public String username;
        public String headpic;

    }

    public static class MeetingDetail implements Serializable {
        public String address;
        public String theme;
        public String partybranch_id;
        public int id;
        public String name;
        public int department_id;
        public int type;
        public String startdate;
        public String enddate;
    }
}
