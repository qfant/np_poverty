package com.page.political;

import com.framework.domain.response.BaseResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shucheng.qu on 2017/8/30.
 */

public class SignListResult extends BaseResult {


    public SignListData data;

    public static class SignListData implements Serializable {
        public List<SignListItem> signList;
    }

    public static class SignListItem implements Serializable {
        public String signdate;
        public String day;
        public String partybranchname;
        public int type;
    }
}
