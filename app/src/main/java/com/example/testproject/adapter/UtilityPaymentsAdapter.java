package com.example.testproject.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.testproject.R;
import com.example.testproject.models.UtilityPayments;
import com.example.testproject.utils.ItemAnimation;
import com.example.testproject.utils.Tools;

import java.util.ArrayList;
import java.util.List;

public class UtilityPaymentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<UtilityPayments> u_Payments = new ArrayList<>();
    private Context context;
    private OnItemClickListener mOnItemClickListener;
    private int animation_type = 0;



    public interface OnItemClickListener {
        void onItemClick(View view, UtilityPayments obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public UtilityPaymentsAdapter(List<UtilityPayments> uPayments, Context context, int animation_type) {
        this.u_Payments = uPayments;
        this.context = context;
        this.animation_type = animation_type;
    }

    private class PaymentsViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView name;
        public View lyt_parent;
        public PaymentsViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.image);
            name = (TextView) view.findViewById(R.id.name);
            lyt_parent = (View) view.findViewById(R.id.lyt_parent);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_utility_payment, parent, false);
        viewHolder = new PaymentsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PaymentsViewHolder) {
            PaymentsViewHolder viewHolder = (PaymentsViewHolder) holder;
            UtilityPayments utilityPayments = u_Payments.get(position);
            viewHolder.name.setText(utilityPayments.name);
            Tools.displayImageRound(context, viewHolder.image, utilityPayments.image);
            viewHolder.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, u_Payments.get(position), position);
                    }
                }
            });
            setAnimation(viewHolder.itemView, position);
        }
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                on_attach = false;
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }
    private int lastPosition = -1;
    private boolean on_attach = true;
    private void setAnimation(View itemView, int position) {
        if (position > lastPosition) {
            ItemAnimation.animate(itemView, on_attach ? position : -1, animation_type);
            lastPosition = position;
        }
    }



    @Override
    public int getItemCount() {
        return u_Payments.size();
    }


}
