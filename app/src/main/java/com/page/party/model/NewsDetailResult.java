package com.page.party.model;

import com.framework.domain.response.BaseResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shucheng.qu on 2017/8/24.
 */

public class NewsDetailResult extends BaseResult {


    public NewsData data;

    public static class NewsData implements Serializable {
        public NewsResult.NewsData.NewsItem newsdetialResult;
    }
}
