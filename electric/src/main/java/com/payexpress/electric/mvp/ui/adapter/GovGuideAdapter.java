package com.payexpress.electric.mvp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.payexpress.electric.R;
import com.payexpress.electric.mvp.model.entity.govEntity.GovGuideInfo;

import java.util.List;

/**
 * Created by fengxiaozheng
 * on 2018/6/19.
 */

public class GovGuideAdapter extends RecyclerView.Adapter<GovGuideAdapter.ViewHolder>{
    private List<GovGuideInfo> data;
    private OnGuideItemClickListener listener;

    public GovGuideAdapter(List<GovGuideInfo> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gov_guide_item, parent, false);
        return new GovGuideAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mTextView.setText(data.get(position).getName());
        holder.view.setOnClickListener(v -> listener.onItemClick(v, data.get(position)));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        View view;
        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            mTextView = itemView.findViewById(R.id.gov_guide_text);
        }
    }

    public interface OnGuideItemClickListener {
        void onItemClick(View view, GovGuideInfo info);
    }

    public void setOnItemClickListener(OnGuideItemClickListener listener) {
        this.listener = listener;
    }
}
