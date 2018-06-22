package com.payexpress.electric.mvp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.payexpress.electric.R;
import com.payexpress.electric.mvp.model.entity.govEntity.GovGuideListInfo;

import java.util.List;

/**
 * Created by fengxiaozheng
 * on 2018/6/20.
 */

public class GovGuideListAdapter extends RecyclerView.Adapter<GovGuideListAdapter.ViewHolder>{
    private List<GovGuideListInfo> data;
    private OnGuideListItemClickListener listener;

    public GovGuideListAdapter(List<GovGuideListInfo> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_guide_list, parent, false);
        return new GovGuideListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(data.get(position).getTitle());
        holder.date.setText(data.get(position).getUpdatedAt());
        holder.view.setOnClickListener(v -> listener.onItemClick(v, data.get(position)));
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView date;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            title = itemView.findViewById(R.id.item_title);
            date = itemView.findViewById(R.id.item_date);
        }
    }

    public interface OnGuideListItemClickListener {
        void onItemClick(View view, GovGuideListInfo info);
    }

    public void setOnItemClickListener(OnGuideListItemClickListener listener) {
        this.listener = listener;
    }
}
