package com.page.party.model;

import com.framework.domain.response.BaseResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shucheng.qu on 2017/8/24.
 */

public class NewsResult extends BaseResult {


    public NewsData data;

    public static class NewsData implements Serializable {
        public int totalNum;
        public List<NewsItem> newsList;

        public static List<NewsItem> mock(){
            List<NewsItem> newsList = new ArrayList<>();
            newsList.add(new NewsItem());
            newsList.add(new NewsItem());
            newsList.add(new NewsItem());
            newsList.add(new NewsItem());
            newsList.add(new NewsItem());
            newsList.add(new NewsItem());
            newsList.add(new NewsItem());
            newsList.add(new NewsItem());
            return newsList;
        }

        public static class NewsItem implements Serializable {
            public String id;
            public String title ="全面深化农村改革加壳推进农业现代化";
            public String time ="2018.04.02";
            public int readCount = 100;
            public String pic = "";
            public String content = "";
        }
    }
}
