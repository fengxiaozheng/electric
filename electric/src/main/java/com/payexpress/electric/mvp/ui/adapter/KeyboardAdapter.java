package com.payexpress.electric.mvp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.payexpress.electric.R;

/**
 * Created by fengxiaozheng
 * on 2018/5/11.
 */

public class KeyboardAdapter extends RecyclerView.Adapter<KeyboardAdapter.ViewHolder> {
    private String[] titles;
    private OnItemClickListener listener;

    public KeyboardAdapter(String[] titles, OnItemClickListener listener) {
        this.titles = titles;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.keyboard_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.mTextView.setText(titles[position]);
        holder.view.setOnClickListener(v -> {
            listener.onItemClick(v, position);
        });
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        private View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            mTextView = (TextView) view.findViewById(R.id.keyboard_number);
        }

    }
}
