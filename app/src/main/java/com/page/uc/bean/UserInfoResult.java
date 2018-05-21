package com.page.uc.bean;

import com.framework.domain.response.BaseResult;

public class UserInfoResult extends BaseResult {

    public UserInfoData data;

    public static class UserInfoData implements BaseData {
        public String dname;
        public String userId;
        public String id;
        public String headpic;
        public String phone;
        public String truename;
    }
}
