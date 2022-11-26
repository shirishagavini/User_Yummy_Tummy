package com.example.yummytummy2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {
    List<MyOrderDetails> list;
    Context context;


    public MyOrderAdapter(List<MyOrderDetails> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myorderitem,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyOrderAdapter.ViewHolder holder, int position) {
        MyOrderDetails currEle = list.get(position);
        holder.itemName.setText(currEle.getItemName());
        int total = currEle.getPrice()* currEle.getQuantity();

        holder.cost.setText(""+currEle.getPrice()+" * "+ currEle.getQuantity()+" = Rs "+total);
        holder.orderDate.setText(currEle.getTimeStamp());
        holder.paymentMode.setText(""+currEle.getQuantity());
        holder.orderId.setText(currEle.getDate());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView orderId,paymentMode,orderDate,cost,itemName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderId = itemView.findViewById(R.id.orderIdNumberTextView);
            paymentMode = itemView.findViewById(R.id.modeTextView);
            orderDate = itemView.findViewById(R.id.date);
            cost = itemView.findViewById(R.id.costing);
            itemName = itemView.findViewById(R.id.itemNameValue);
        }

        @Override
        public void onClick(View view) {
        }
    }
}
