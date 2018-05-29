package com.page.information;

import com.framework.domain.response.BaseResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shucheng.qu on 2017/8/30.
 */

public class InfoDetailResult extends BaseResult {


    public InfoDetailData data;

    public static class InfoDetailData implements Serializable {
        public List<InfoDetailItem> infoDetialResult;
    }

    public static class InfoDetailItem implements Serializable {
        public String key;
        public String name;
    }

//    public static class InfoDetail implements Serializable {
//        public int id;
//        public String name;//姓名
//        public String address;//位置
//        public String area;//地区
//        public String promot;//出资人
//        public int scale;//是否规模以上企业 0否 1是
//        public int isparty;//是否成立党组织 0否
//        public String partytime;//党组织成立时间
//        public String partyshape;//党组织设置形式
//        public int orgmethod;//组件方式 0--单独组件 1--联合组建
//        public String clerkname;//党组织书记姓名
//        public String phone;//党组织书记手机
//
//    }
}
