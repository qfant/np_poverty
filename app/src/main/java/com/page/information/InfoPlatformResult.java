package com.page.information;

import com.framework.domain.response.BaseResult;

import java.util.List;

/**
 * Created by chenxi.cui on 2017/9/17.
 */

public class InfoPlatformResult extends BaseResult {
    public InfoPlatformData data;

    public static class InfoPlatformData implements BaseData {
        public List<InfoPlatformItem> infoPlatformlist;
    }
    public static class InfoPlatformItem implements BaseData {
        public String name;
        public String phone;
        public String portrait;
    }
}
