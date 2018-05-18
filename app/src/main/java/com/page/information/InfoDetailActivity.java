package com.page.information;

import android.os.Bundle;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.view.CircleImageView;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenxi.cui on 2018/5/18.
 */

public class InfoDetailActivity extends BaseActivity {
    @BindView(R.id.image_pic)
    CircleImageView imagePic;
    @BindView(R.id.text_1)
    TextView text1;
    @BindView(R.id.text_2)
    TextView text2;
    @BindView(R.id.text_3)
    TextView text3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_detail_layout);
        ButterKnife.bind(this);
        int id = myBundle.getInt("id");
        setTitleBar("信息详情", true);
        InfoDetailParam param = new InfoDetailParam();
        param.id = id;
        Request.startRequest(param, ServiceMap.InfoDetail, mHandler, Request.RequestFeature.CANCELABLE, Request.RequestFeature.BLOCK);
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.InfoDetail) {
            InfoDetailResult resul = (InfoDetailResult) param.result;
            InfoDetailResult.InfoDetail data = resul.data.infoDetialResult;
            text1.setText("企业: " + data.name);
            text2.setText("电话：" + data.phone);
            text3.setText("地址：" + data.address);
        }
        return super.onMsgSearchComplete(param);
    }

}
