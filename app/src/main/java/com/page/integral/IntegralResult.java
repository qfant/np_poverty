package com.page.integral;

import com.framework.domain.response.BaseResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shucheng.qu on 2017/8/30.
 */

public class IntegralResult extends BaseResult {


    public IntegralData data;

    public static class IntegralData implements Serializable {
        public int totalNum;
        public String[] area;
        public String[] quarter;
        public String[] type;
        public String[] year;
        public List<IntegralItem> integralList;

        public String[] getType() {
            if (type == null) {
                return new String[]{"药企", "酒厂", "农产品"};
            }
            return type;
        }

        public String[] getQuarter() {
            if (area == null) {
                return new String[]{"第一季度", "第二季度", "第三季度", "第四季度"};
            }
            return quarter;
        }

        public String[] getArea() {
            if (area == null) {
                return new String[]{"亳州市", "涡阳县", "利辛县", "蒙城县", "谯城区", "亳州经开区", "亳芜园区"};
            }
            return area;
        }

        public String[] getYear() {
            if (year == null) {
                return new String[]{"2018", "2017"};
            }
            return year;
        }
    }

    public static class IntegralItem implements Serializable {
        public int id;
        public int score;
        public String title;
        public String createtime;
    }
}
