package com.example.administrator.powerpayment.activity.mvp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.powerpayment.activity.R;

public class GovAdapter extends RecyclerView.Adapter<GovAdapter.ViewHolder> {
    private String[] titles;
    private int[] imgs;
    private OnItemClickListener listener;

    public GovAdapter(String[] titles, int[] imgs, OnItemClickListener listener) {
        this.titles = titles;
        this.imgs = imgs;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gov_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mImageView.setImageResource(imgs[position]);
        holder.mTextView.setText(titles[position]);
        holder.view.setOnClickListener(v -> {
            listener.onItemClick(v, imgs[position]);
        });
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        ImageView mImageView;
        private View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            mTextView = (TextView) view.findViewById(R.id.gov_item_tv);
            mImageView = (ImageView) view.findViewById(R.id.gov_item_iv);
        }

    }
}
