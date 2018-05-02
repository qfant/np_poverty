package com.page.detail;

import com.framework.domain.response.BaseResult;

/**
 * Created by chenxi.cui on 2017/9/12.
 * "address":"123123",
 * 09-12 18:31:27.639 11407-14004/com.haolb.worker V/response: 		"evaluate":2,
 * 09-12 18:31:27.639 11407-14004/com.haolb.worker V/response: 		"statusCN":"已评价",
 * 09-12 18:31:27.639 11407-14004/com.haolb.worker V/response: 		"status":7,
 * 09-12 18:31:27.639 11407-14004/com.haolb.worker V/response: 		"id":6,
 * 09-12 18:31:27.639 11407-14004/com.haolb.worker V/response: 		"phone":"13212345678",
 * 09-12 18:31:27.639 11407-14004/com.haolb.worker V/response: 		"comment":"hao",
 * 09-12 18:31:27.639 11407-14004/com.haolb.worker V/response: 		"intro":"我勒个去",
 * 09-12 18:31:27.639 11407-14004/com.haolb.worker V/response: 		"pic":"123123"
 */

public class CameraDetailResult extends BaseResult {

    public CameraDetailData data;

    public static class CameraDetailData implements BaseData {
        public CameraBean cameraResult;
    }
    public static class CameraBean implements BaseData {
        public String id;
        public String rtmp;
        public String name="";
    }
}
