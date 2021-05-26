package com.tfu.smartagriculture.wx.base.baseAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.tfu.smartagriculture.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created on by WangXi 15/05/2021.
 * 框架适配器(弃用)
 */
public class ViewPager2BaseAdapter extends RecyclerView.Adapter<ViewPager2BaseAdapter.ViewPager2ViewHolder> {
    Context context;
    ArrayList<Fragment> fragList;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;

    public ViewPager2BaseAdapter(Context context, ArrayList<Fragment> fragList, FragmentManager fragmentManager) {
        this.context = context;
        this.fragList = fragList;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public ViewPager2ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        ViewPager2ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewpager2_item_wx, parent, false);
            holder = new ViewPager2ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewPager2ViewHolder) view.getTag();
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPager2ViewHolder holder, int position) {
        /**
         * 切换Fragment
         */
        transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.flt_fragContainer, fragList.get(position)).show(fragList.get(position)).commit();
    }

    @Override
    public int getItemCount() {
        return fragList.size();
    }


    class ViewPager2ViewHolder extends RecyclerView.ViewHolder {
        FrameLayout flt_fragContainer;

        public ViewPager2ViewHolder(@NonNull View itemView) {
            super(itemView);
            flt_fragContainer = itemView.findViewById(R.id.flt_fragContainer);
        }
    }

}
