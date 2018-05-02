package com.page.home;

import com.framework.domain.response.BaseResult;

import java.util.List;

/**
 * Created by chenxi.cui on 2017/9/12.
 */

public class NoticesResult extends BaseResult {

    public NoticesData data;

    public static class NoticesData implements BaseData {
        public List<NoticeBean> noticesResult;
    }
    public static class NoticeBean implements BaseData {
        public String id;
        public String title;
        public String createtime;
        public String intro;
    }
}
