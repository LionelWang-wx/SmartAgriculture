package com.tfu.smartagriculture.wx.base.baseAdapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tfu.smartagriculture.R;
import com.tfu.smartagriculture.wx.bean.PlanData;
import com.tfu.smartagriculture.wx.other.PlanDetailsActivity;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created on by WangXi 15/05/2021.
 * 社区方案适配器
 */
public class CommunityArticleAdapter extends RecyclerView.Adapter<CommunityArticleAdapter.PlanViewHolder> {
    private ArrayList<PlanData.Plan> planList;
    private Activity activity;

    //    private ArrayList<Drawable> iconList = new ArrayList(){R.d};
    public CommunityArticleAdapter(Activity activity, ArrayList<PlanData.Plan> planList) {
        this.activity = activity;
        this.planList = planList;
    }

    @NonNull
    @Override
    public PlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        PlanViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.community_article_item_wx, parent, false);
            viewHolder = new PlanViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (PlanViewHolder) view.getTag();
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, PlanDetailsActivity.class);
                int position = viewHolder.getAdapterPosition();
                PlanData.Plan plan = planList.get(position);
                intent.putExtra("plan", plan);
                parent.getContext().startActivity(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlanViewHolder holder, int position) {
        PlanData.Plan plan = planList.get(position);
        holder.iv_icon.setImageResource(R.drawable.iv_jj4);
        holder.tv_title.setText(plan.getTitle());
        holder.tv_pageViews.setText("浏览量:2000");
        holder.tv_authorName.setText(plan.getAccountName());
    }

    @Override
    public int getItemCount() {
        return planList.size();
    }

    class PlanViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_icon;
        TextView tv_title;
        TextView tv_pageViews;
        TextView tv_authorName;

        public PlanViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_icon = itemView.findViewById(R.id.iv_icon);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_pageViews = itemView.findViewById(R.id.tv_pageViews);
            tv_authorName = itemView.findViewById(R.id.tv_authorName);
        }
    }
}
