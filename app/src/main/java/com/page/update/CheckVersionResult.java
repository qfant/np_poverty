package com.page.update;

import com.framework.domain.response.BaseResult;
import com.framework.domain.response.UpgradeInfo;

import java.io.Serializable;

/**
 * Created by chenxi.cui on 2018/1/19.
 */

public class CheckVersionResult extends BaseResult {
    public CheckVersionData data;

    public static class CheckVersionData implements Serializable {
        private static final long serialVersionUID = 1L;
        public UpgradeInfo upgradeInfo;
    }
}
