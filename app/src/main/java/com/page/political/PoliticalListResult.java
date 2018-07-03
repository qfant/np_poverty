package com.page.political;

import com.framework.domain.response.BaseResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shucheng.qu on 2017/8/30.
 */

public class PoliticalListResult extends BaseResult {


    public PoliticalData data;

    public static class PoliticalData implements Serializable {
        public int signin;
        public int signout;
        public int worklog;
        public List<PoliticalItem> partyBranchList;
    }

    //"createtime":"2018-05-28 15:06",
//07-02 20:53:56.732 27948-28325/com.qfant.noparty V/response: 				"intro":"<p style=\"color:#333333;font-family:宋体;font-size:medium;background-color:#FFFFFF;text-indent:2em;\">\r\n\t近日，我市出台《非公企业和社会组织党组织党建工作考核积分管理办法（试行）》，从考核内容、考核细则以及结果运用等方面提出明确要求。\r\n</p>\r\n<p style=\"color:#333333;font-family:宋体;font-size:medium;background-color:#FFFFFF;text-indent:2em;\">\r\n\t<strong>明确考核内容</strong>\r\n</p>\r\n<p style=\"color:#333333;font-family:宋体;font-size:medium;background-color:#FFFFFF;text-indent:2em;\">\r\n\t《办法》指出，全市非公企业和社会组织党组织党建工作每季度考核一次，内容包括非公企业和社会组织党组织党建重点工作落实情况、阶段性工作完成情况、工作保障情况、创新举措和特色做法等。考核采用百分制评分办法，同一年度积分累加计算。对因工作成效突出或不力，受到市级以上表彰或通报批评的非公企业和社会组织党组织，各地各单位可适当给予加、减分。\r\n</p>\r\n<p style=\"color:#333333;font-family:宋体;font-size:medium;background-color:#FFFFFF;text-indent:2em;\">\r\n\t<strong>细化管理责任</strong>\r\n</p>\r\n<p style=\"color:#333333;font-family:宋体;font-size:medium;background-color:#FFFFFF;text-indent:2em;\">\r\n\t《办法》指出，考核工作由市委非公工委牵头，县区委非公工委，亳州经济开发区、亳芜现代产业园区非公企业综合党委负责组织实施所辖非公企业和社会组织党组织考核工作；市直机关工委负责组织实施市属非公企业和社会组织党组织考核工作。考核按照分级负责的原则进行，每年1月、7月，各地各单位将所辖非公企业和社会组织党组织上一年度、上半年党建工作考核情况进行通报并报市委非公工委。市委非公工委审定后，将考核结果抄送各级非公工委成员单位、涉企职能部门。\r\n</p>\r\n<p style=\"color:#333333;font-family:宋体;font-size:medium;background-color:#FFFFFF;text-indent:2em;\">\r\n\t<strong>强化结果运用</strong>\r\n</p>\r\n<p style=\"color:#333333;font-family:宋体;font-size:medium;background-color:#FFFFFF;text-indent:2em;\">\r\n\t《办法》强调，考核结果作为非公企业出资人和社会组织负责人评先评优、政治安排，党建工作指导员年度考核和任期考核的重要依据。非公企业和社会组织在争取各级资金、技术、项目、税收等支持时，对积分排名靠前的非公企业和社会组织给予适当倾斜，同等条件下优先安排。 （记者 陈显锋）\r\n</p>",
//07-02 20:53:56.732 27948-28325/com.qfant.noparty V/response: 				"id":"37",
//07-02 20:53:56.732 27948-28325/com.qfant.noparty V/response: 				"title":"我市非公企业和社会组织党建考核实行积分制"
    public static class PoliticalItem implements Serializable {
        public String signdate;
        public String day;
        public String partybranchname;
        public int type;
        public String createtime;
        public String intro;
        public String managerid;
        public double longitude;
        public int id;
        public String name;
        public int signin;
        public double latitude;
    }
}
