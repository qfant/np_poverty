package com.page.map;

import com.framework.domain.response.BaseResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenxi.cui on 2017/9/30.
 */

public class PointResult extends BaseResult {
    public List<PointItem> data;


    public static class PointItem implements BaseData {
        public List<PointItem> children = new ArrayList<>();
        public List<PointItem> boundary = new ArrayList<>();
        public String name;
        public String id;
        public double lon;
        public double lat;
        public String intro;
        public String contact;
        public String tel;
    }
}
