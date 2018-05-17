package com.page.political;

import com.framework.domain.response.BaseResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shucheng.qu on 2017/8/30.
 */

public class SignStatusResult extends BaseResult {


    public SignStatus data;

    public static class SignStatus implements Serializable {
        public int signin;
        public int signout;
        public double latitude;
        public double longitude;
        public String companyname;
        public int id;
    }
}
