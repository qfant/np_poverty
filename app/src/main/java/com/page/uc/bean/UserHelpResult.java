package com.page.uc.bean;

import com.framework.domain.response.BaseResult;


public class UserHelpResult extends BaseResult {

    public UserHelpData data;

    public static class UserHelpData implements BaseData {
        public String useHelpResult;
    }
}
