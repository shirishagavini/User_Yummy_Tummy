package com.example.yummytummy2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ResturantAdapter extends RecyclerView.Adapter<ResturantAdapter.ViewHolder> implements View.OnLongClickListener{
    private View.OnLongClickListener longClickListener;
    private List<ResturantVendorDetails> list = new ArrayList<>();
    private Context context;

    public ResturantAdapter(List<ResturantVendorDetails> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ResturantAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View item = inflater.inflate(R.layout.res_item,parent,false);
        ViewHolder view = new ViewHolder(item);
        item.setOnLongClickListener(this);
        return view;
    }

    @Override
    public void onBindViewHolder(@NonNull ResturantAdapter.ViewHolder holder, int position) {
        ResturantVendorDetails curele = list.get(position);
        holder.resturantName.setText(curele.getName());
        holder.rattings.setText(""+curele.getRatings());
//        Picasso.with(context).load(curele.getUrl()).placeholder(R.drawable.food_image)
//                .error(R.drawable.food_image)
//                .fit()
//                .into(holder.profileImage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public boolean onLongClickListner(View.OnLongClickListener longClickListener)
    {
        this.longClickListener = longClickListener;
        return true;
    }

    @Override
    public boolean onLongClick(View view)
    {
        longClickListener.onLongClick(view);
        return true;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        public TextView resturantName,rattings;
        public ImageView profileImage;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            resturantName = itemView.findViewById(R.id.foodTextView);
            rattings = itemView.findViewById(R.id.contentTextView);
//            profileImage = itemView.findViewById(R.id.foodImageView);
        }

        @Override
        public boolean onLongClick(View view) {
            return false;
        }

        @Override
        public void onClick(View view)
        {

        }
    }
}

