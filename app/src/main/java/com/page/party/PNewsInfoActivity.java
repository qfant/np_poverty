package com.page.party;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenxi.cui on 2018/4/2.
 */

public class PNewsInfoActivity extends BaseActivity {
    @BindView(R.id.tv_content)
    TextView tvContent;
    public static void startActivity(BaseActivity activity, String title, String url) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("url", url);
        Intent intent = new Intent(activity, PNewsInfoActivity.class);
        intent.putExtras(bundle);
        activity.qStartActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_new_info_layout);
        ButterKnife.bind(this);
        String title = myBundle.getString("title");
        String url = myBundle.getString("url");
        setTitleBar(title, true);
        tvContent.setText("　　新华社北京10月28日电 十九届中共中央政治局10月27日上午就深入学习贯彻党的十九大精神进行第一次集体学习。中共中央总书记习近平在主持学习时强调，党的十九大在政治上、理论上、实践上取得了一系列重大成果，就新时代坚持和发展中国特色社会主义的一系列重大理论和实践问题阐明了大政方针，就推进党和国家各方面工作制定了战略部署，是我们党在新时代开启新征程、续写新篇章的政治宣言和行动纲领。贯彻落实党的十九大精神，在新时代坚持和发展中国特色社会主义，要求全党来一个大学习。\n" +
                "\n" +
                "　　李克强、栗战书、汪洋、王沪宁、赵乐际、韩正就深刻领会和贯彻落实党的十九大精神谈了体会。他们表示，党的十九大高举中国特色社会主义伟大旗帜，以马克思列宁主义、毛泽东思想、邓小平理论、“三个代表”重要思想、科学发展观、习近平新时代中国特色社会主义思想为指导，作出了中国特色社会主义进入了新时代等重大政治论断，深刻阐述了新时代中国共产党的历史使命，确定了决胜全面建成小康社会、开启全面建设社会主义现代化国家新征程的目标，对新时代推进中国特色社会主义伟大事业和党的建设新的伟大工程作出了全面部署。党的十九大报告进一步指明了党和国家事业前进方向，是我们党团结带领全国各族人民在新时代坚持和发展中国特色社会主义的政治宣言和行动纲领，是马克思主义的纲领性文献。全党全国要紧密团结在以习近平同志为核心的党中央周围，增强“四个意识”，坚定“四个自信”，统筹推进“五位一体”总体布局，协调推进“四个全面”战略布局，坚持党要管党、全面从严治党，使党的十九大精神成为推动党和国家事业发展的强大思想武器，把党的十九大提出的各项目标任务落到实处。");
    }
}
