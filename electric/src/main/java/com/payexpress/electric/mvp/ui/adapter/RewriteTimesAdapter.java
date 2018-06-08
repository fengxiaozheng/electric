package com.payexpress.electric.mvp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.payexpress.electric.R;
import com.payexpress.electric.mvp.model.entity.payment.RewriteTimesInfo;

import java.util.List;

/**
 * Created by fengxiaozheng
 * on 2018/6/6.
 */

public class RewriteTimesAdapter extends RecyclerView.Adapter<RewriteTimesAdapter.ViewHolder> {
    private OnItemViewClickListener listener;
    private List<RewriteTimesInfo> infos;

    public RewriteTimesAdapter(List<RewriteTimesInfo> infos) {
        this.infos = infos;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rewrite_times, parent, false);
        return new RewriteTimesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.num.setText(infos.get(position).getPay_count());
        holder.date.setText(infos.get(position).getPay_date());
        holder.write.setText(infos.get(position).getWritable_amount());
        holder.pay.setText(infos.get(position).getPay_amount());
        holder.rewrite.setOnClickListener(
                v -> listener.onClick(v, infos.get(position).getPay_count()));
    }

    @Override
    public int getItemCount() {
        return infos.size();
    }

    public interface OnItemViewClickListener {
        void onClick(View view, String payCount);
    }

    public void setOnItemViewClickListener(OnItemViewClickListener listener) {
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView num;
        TextView date;
        TextView pay;
        TextView write;
        Button rewrite;
        public ViewHolder(View itemView) {
            super(itemView);
            num = itemView.findViewById(R.id.times_no);
            date = itemView.findViewById(R.id.times_date);
            pay = itemView.findViewById(R.id.times_amount);
            write = itemView.findViewById(R.id.times_write);
            rewrite = itemView.findViewById(R.id.times_rewrite);
        }
    }
}
