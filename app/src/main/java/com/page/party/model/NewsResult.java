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
        public List<NewsItem> workNewsList;

        public void setWorkNewsList(List<NewsItem> workNewsList) {
            this.newsList = workNewsList;
        }

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
            public String title ="";
            public String createtime ="";
            public int readCount = 0;
            public String image = "";
            public String intro = "";
            public String type = "";
        }
    }
}
