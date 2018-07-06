package com.example.administrator.powerpayment.activity.mvp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.powerpayment.activity.R;
import com.example.administrator.powerpayment.activity.mvp.model.entity.govEntity.GovBusinessInfo;

import java.util.List;

/**
 * Created by fengxiaozheng
 * on 2018/6/25.
 */

public class GovBusinessListAdapter extends RecyclerView.Adapter<GovBusinessListAdapter.ViewHolder> {
    private List<GovBusinessInfo> data;
    private OnBusinessListItemClickListener listener;

    public GovBusinessListAdapter(List<GovBusinessInfo> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public GovBusinessListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_guide_list, parent, false);
        return new GovBusinessListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GovBusinessListAdapter.ViewHolder holder, int position) {
        holder.title.setText(String.format("%s（%s）", data.get(position).getName(), data.get(position).getArchName()));
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

    public interface OnBusinessListItemClickListener {
        void onItemClick(View view, GovBusinessInfo info);
    }

    public void setOnItemClickListener(OnBusinessListItemClickListener listener) {
        this.listener = listener;
    }
}
