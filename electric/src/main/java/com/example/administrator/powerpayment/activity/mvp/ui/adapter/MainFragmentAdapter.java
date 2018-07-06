package com.example.administrator.powerpayment.activity.mvp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.powerpayment.activity.R;
import com.example.administrator.powerpayment.activity.mvp.model.entity.MainFragmentItemInfo;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by fengxiaozheng
 * on 2018/6/15.
 */

public class MainFragmentAdapter extends RecyclerView.Adapter<MainFragmentAdapter.ViewHolder> {
    private MainFragmentClickListener listener;
    private List<MainFragmentItemInfo> data;


    public MainFragmentAdapter(List<MainFragmentItemInfo> data, MainFragmentClickListener listener) {
        this.data = data;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gov_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if ("01".equals(data.get(position).getStatus())) {
            holder.mIView.setVisibility(View.VISIBLE);
        }
        holder.mImageView.setImageResource(getResource(data.get(position).getFuncIcon()));
        holder.mTextView.setText(data.get(position).getFuncName());
        holder.view.setOnClickListener(v -> {
            listener.onItemClick(v, data.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        ImageView mImageView;
        View mIView;
        private View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            mTextView = (TextView) view.findViewById(R.id.gov_item_tv);
            mImageView = (ImageView) view.findViewById(R.id.gov_item_iv);
            mIView = view.findViewById(R.id.isNo);
        }

    }

    private int  getResource(String imageName){
        Class mipmap = R.mipmap.class;
        try {
            Field field = mipmap.getField(imageName);
            int resId = field.getInt(imageName);
            return resId;
        } catch (NoSuchFieldException e) {//如果没有在"mipmap"下找到imageName,将会返回0
            return 0;
        } catch (IllegalAccessException e) {
            return 0;
        }

    }
}

