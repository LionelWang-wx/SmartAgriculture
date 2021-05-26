package com.tfu.smartagriculture.wx.other;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.tfu.smartagriculture.R;
import com.tfu.smartagriculture.wx.bean.PlanData;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 方案详情
 */
public class PlanDetailsActivity extends AppCompatActivity {

    TextView tv_title;
    TextView tv_pageViews;
    ImageView iv_userHeader;
    TextView tv_useName;
    TextView tv_content;
    PlanData.Plan plan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_details);
        initView();
        initData();
    }

    private void initData() {
        plan = (PlanData.Plan) getIntent().getSerializableExtra("plan");

        tv_title.setText(plan.getTitle());
        tv_pageViews.setText("浏览量：100");
        iv_userHeader.setImageResource(R.drawable.img_carousel1);
        tv_useName.setText(plan.getAccountName());
        tv_content.setText(plan.getRemark());
    }

    private void initView() {

        tv_title = this.findViewById(R.id.tv_title);
        tv_pageViews = this.findViewById(R.id.tv_pageViews);
        iv_userHeader = this.findViewById(R.id.iv_userHeader);
        tv_useName = this.findViewById(R.id.tv_useName);
        tv_content = this.findViewById(R.id.tv_content);
    }


}