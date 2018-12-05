package com.page.integral;

import com.framework.domain.response.BaseResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shucheng.qu on 2017/8/30.
 */

public class IntegralResult extends BaseResult {


    public IntegralData data;

    public static class IntegralFilter implements Serializable {
        public int id;
        public String name;
    }

    public static class IntegralData implements Serializable {
        public int totalNum;
        public List<IntegralFilter> area;
        public List<IntegralFilter> cat;
        public List<IntegralFilter> quarter;
        public List<IntegralFilter> type;
        public List<IntegralFilter> year;
        public List<IntegralItem> integralList;


        public void setArea(List<IntegralFilter> area) {
            this.area = area;
            IntegralFilter integralFilter = new IntegralFilter();
            integralFilter.id = 0;
            integralFilter.name = "全部";
            this.area.add(0, integralFilter);
        }

        public void setQuarter(List<IntegralFilter> quarter) {
            this.quarter = quarter;
            IntegralFilter integralFilter = new IntegralFilter();
            integralFilter.id = 0;
            integralFilter.name = "全部";
            this.quarter.add(0, integralFilter);
        }

        public void setType(List<IntegralFilter> type) {
            this.type = type;
            IntegralFilter integralFilter = new IntegralFilter();
            integralFilter.id = 0;
            integralFilter.name = "全部";
            this.type.add(0, integralFilter);
        }

        public void setYear(List<IntegralFilter> year) {
            this.year = year;
            IntegralFilter integralFilter = new IntegralFilter();
            integralFilter.id = 0;
            integralFilter.name = "全部";
            this.year.add(0, integralFilter);
        }
        public void setCat(List<IntegralFilter> cat) {
            this.cat = cat;
            IntegralFilter integralFilter = new IntegralFilter();
            integralFilter.id = 0;
            integralFilter.name = "全部";
            this.cat.add(0, integralFilter);
        }
        public String[] getType() {
            if (type == null) {
                return new String[]{"药企", "酒厂", "农产品"};
            }
            String[] arr = new String[type.size()];
            int i = 0;
            for (IntegralFilter item : type) {
                arr[i] = item.name;
                i++;
            }
            return arr;
        }

        public String[] getQuarter() {
            if (quarter == null) {
                return new String[]{"第一季度", "第二季度", "第三季度", "第四季度"};
            }
            String[] arr = new String[quarter.size()];
            int i = 0;
            for (IntegralFilter item : quarter) {
                arr[i] = item.name;
                i++;
            }
            return arr;
        }

        public String[] getArea() {
            if (area == null) {
                return new String[]{"亳州市", "涡阳县", "利辛县", "蒙城县", "谯城区", "亳州经开区", "亳芜园区"};
            }
            String[] arr = new String[area.size()];
            int i = 0;
            for (IntegralFilter item : area) {
                arr[i] = item.name;
                i++;
            }
            return arr;
        }

        public String[] getYear() {
            if (year ==null) {
                return new String[]{"2018", "2017"};
            }
            String[] arr = new String[year.size()];
            int i = 0;
            for (IntegralFilter item : year) {
                arr[i] = item.name;
                i++;
            }
            return arr;
        }
        public String[] getCat() {
            if (cat == null) {
                return new String[]{"社会组织党组织", "非公企业党组织"};
            }
            String[] arr = new String[cat.size()];
            int i = 0;
            for (IntegralFilter item : cat) {
                arr[i] = item.name;
                i++;
            }
            return arr;
        }
    }

    public static class IntegralItem implements Serializable {
        public int id;
        public float score;
        public String title;
        public String createtime;
        public String name;
        public String year;
        public String quarter;
    }
}
