package com.page.political;

import com.framework.domain.response.BaseResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shucheng.qu on 2017/8/30.
 */

public class WorkLogListResult extends BaseResult {


    public WorkLogData data;

    public static class WorkLogData implements Serializable {
        public List<WorkLogItem> workLogResult;
    }

    public static class WorkLogItem implements Serializable {
        public String title;
        public String address;
        public String content;
        public String id;
        public String pic1;
    }
}
