package com.tfu.smartagriculture.wx.base.baseAdapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tfu.framework.utils.SpUtils;
import com.tfu.smartagriculture.R;
import com.tfu.smartagriculture.wx.bean.DetectDevice;
import com.tfu.smartagriculture.wx.other.InspectionDetailsActivity;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class DetectAdapter extends RecyclerView.Adapter<DetectAdapter.DetectViewHolder> {
    private ArrayList<DetectDevice> planList;
    private Activity activity;

    public DetectAdapter(Activity activity, ArrayList<DetectDevice> planList) {
        this.activity = activity;
        this.planList = planList;
    }

    @NonNull
    @Override
    public DetectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        DetectViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detect_item_wx, parent, false);
            viewHolder = new DetectViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (DetectViewHolder) view.getTag();
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, InspectionDetailsActivity.class);
                int position = viewHolder.getAdapterPosition();
                DetectDevice detectDevice = planList.get(position);
                i.putExtra("detectDevice", detectDevice);
                activity.startActivity(i);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DetectViewHolder holder, int position) {
        DetectDevice detectDevice = planList.get(position);
        int value = Integer.parseInt(detectDevice.getTv_detectValue());
        switch (detectDevice.getTv_detectName()) {
            case "实时温度":
                int leftNormal_T = SpUtils.getInstance().getInt("Tem_N_L", 0);
                int rightNormal_T = SpUtils.getInstance().getInt("Tem_N_R", 100);
                int leftY_T = SpUtils.getInstance().getInt("Tem_Y_L", 0);
                int rightY_T = SpUtils.getInstance().getInt("Tem_Y_R", 0);
//                int leftR = SpUtils.getInstance().getInt("Tem_R_L",0);
//                int rightR = SpUtils.getInstance().getInt("Tem_R_L",0);
                if ((value < rightNormal_T) && (value > leftNormal_T)) {
                    holder.cl_backG.setBackgroundColor(Color.parseColor("#a100BCD4"));
                } else if (value >= leftY_T && value < rightY_T) {
                    holder.cl_backG.setBackgroundColor(Color.parseColor("#aaFFEB3B"));
                } else {
                    holder.cl_backG.setBackgroundColor(Color.parseColor("#F44336"));
                }
                holder.tv_detectValue.setText(Integer.parseInt(detectDevice.getTv_detectValue()) + "℃");
                break;
            case "实时湿度":
                int leftNormal_H = SpUtils.getInstance().getInt("Hum_N_L", 0);
                int rightNormal_H = SpUtils.getInstance().getInt("Hum_N_R", 100);
                int leftY_H = SpUtils.getInstance().getInt("Hum_Y_L", 0);
                int rightY_H = SpUtils.getInstance().getInt("Hum_Y_R", 0);
                if ((value < rightNormal_H) && (value > leftNormal_H)) {
                    holder.cl_backG.setBackgroundColor(Color.parseColor("#a100BCD4"));
                } else if (value >= leftY_H && value < rightY_H) {
                    holder.cl_backG.setBackgroundColor(Color.parseColor("#aaFFEB3B"));
                } else {
                    holder.cl_backG.setBackgroundColor(Color.parseColor("#F44336"));
                }
                holder.tv_detectValue.setText(Integer.parseInt(detectDevice.getTv_detectValue()) + "%");
                break;
            case "实时PM2.5":
                //70<x<120黄
                //x>120红
                int leftNormal_P = SpUtils.getInstance().getInt("PM_N_L", 0);
                int rightNormal_P = SpUtils.getInstance().getInt("PM_N_R", 100);
                int leftY_P = SpUtils.getInstance().getInt("PM_Y_L", 0);
                int rightY_P = SpUtils.getInstance().getInt("PM_Y_R", 0);
                if ((value < rightNormal_P) && (value > leftNormal_P)) {
                    holder.cl_backG.setBackgroundColor(Color.parseColor("#a100BCD4"));
                } else if (value >= leftY_P && value < rightY_P) {
                    holder.cl_backG.setBackgroundColor(Color.parseColor("#aaFFEB3B"));
                } else {
                    holder.cl_backG.setBackgroundColor(Color.parseColor("#F44336"));
                }
                holder.tv_detectValue.setText(Integer.parseInt(detectDevice.getTv_detectValue()) + "μg/m³");
                break;
            case "实时烟雾":
                int leftNormal_S = SpUtils.getInstance().getInt("SM_N_L", 0);
                int rightNormal_S = SpUtils.getInstance().getInt("SM_N_R", 102201);
                int leftY_S = SpUtils.getInstance().getInt("SM_Y_L", 0);
                int rightY_S = SpUtils.getInstance().getInt("SM_Y_R", 0);
                if ((value < rightNormal_S) && (value > leftNormal_S)) {
                    holder.cl_backG.setBackgroundColor(Color.parseColor("#a100BCD4"));
                } else if (value >= leftY_S && value < rightY_S) {
                    holder.cl_backG.setBackgroundColor(Color.parseColor("#aaFFEB3B"));
                } else {
                    holder.cl_backG.setBackgroundColor(Color.parseColor("#F44336"));
                }
                holder.tv_detectValue.setText(Integer.parseInt(detectDevice.getTv_detectValue()) + "%");
                break;
        }
        holder.iv_detectIcon.setImageResource(detectDevice.getIv_detectIcon());
        holder.tv_detectName.setText(detectDevice.getTv_detectName());

    }

    @Override
    public int getItemCount() {
        return planList.size();
    }

    class DetectViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_detectIcon;
        TextView tv_detectName;
        TextView tv_detectValue;
        ConstraintLayout cl_backG;

        public DetectViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_detectIcon = itemView.findViewById(R.id.iv_detectIcon);
            tv_detectName = itemView.findViewById(R.id.tv_detectName);
            tv_detectValue = itemView.findViewById(R.id.tv_detectValue);
            cl_backG = itemView.findViewById(R.id.cl_backG);
        }
    }
}
