package com.page.home;

import com.framework.domain.response.BaseResult;

import java.util.List;

/**
 * Created by chenxi.cui on 2017/12/12.
 */

public class PersonsResult extends BaseResult {

    public PersonsData data;

    /**
     * townResult	列表	JsonArray
     * townResult [i].id	id	string
     * townResult [i].name	名称	String
     * townResult [i].villages	村	JsonArray
     * townResult [i]. villages[i].name	村名	String
     * townResult [i]. villages[i].id	村id	String
     */
    public static class PersonsData implements BaseData {
        public List<PersonBean> personsResult;
    }

    /**
     * personsResult [i].id	id
     * personsResult [i].name	姓名
     * personsResult [i].age	年龄
     * personsResult [i].phone	电话
     * personsResult [i].address	家庭住址
     * personsResult [i].iden	身份证
     * personsResult [i].bank	开户行
     * personsResult [i].banknum	银行卡号
     * personsResult [i].headimg	照片
     */
    public static class PersonBean implements BaseData {
        public String id;
        public String name;
        public String age;
        public String phone;
        public String address;
        public String iden;
        public String bank;
        public String banknum;
        public String headimg;
    }
}
