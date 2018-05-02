package com.page.home;

import com.framework.domain.response.BaseResult;

import java.util.List;

/**
 * Created by chenxi.cui on 2017/9/12.
 */

public class CamerasResult extends BaseResult {

    public WorkerRepairData  data;

    public static class WorkerRepairData implements BaseData {
        public List<CameraBean> cameras;
    }


    public static class CameraBean implements BaseData {
        public String id;
        public String name;
        public String imageUrl;
        public int type;
        public String url;
        public String areaid;
        public String deviceid;
        public String shareid;
        public String uk;

//        	"uk":"270539",
//                    11-15 22:40:27.684 23618-23646/com.haolb.personnel V/response: 			"id":"23",
//                11-15 22:40:27.684 23618-23646/com.haolb.personnel V/response: 			"name":"古城镇万瑞箱包有限公司",
//                11-15 22:40:27.684 23618-23646/com.haolb.personnel V/response: 			"areaid":"1",
//                11-15 22:40:27.684 23618-23646/com.haolb.personnel V/response: 			"deviceid":"137894025675",
//                11-15 22:40:27.684 23618-23646/com.haolb.personnel V/response: 			"shareid":"fa182dae82dd8ced3aae0bf4438aa265"
    }
}
