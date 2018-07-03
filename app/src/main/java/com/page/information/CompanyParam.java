package com.page.information;

import com.framework.domain.param.BaseParam;

public class CompanyParam extends BaseParam {
    public int managerId;
    public int type;
    public int pageNo = 1;
    public int pageSize = 20;
}
