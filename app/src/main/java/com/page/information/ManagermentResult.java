package com.page.information;

import com.framework.domain.response.BaseResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shucheng.qu on 2017/8/30.
 */

public class ManagermentResult extends BaseResult {


    public List<ManagermentItem>  data;

    public static class ManagermentData implements Serializable {
        public int totalNum;
        public List<ManagermentItem> infoListResult;
    }

    public static class ManagermentItem implements Serializable {
        public int id;
        public String name;
    }
}
