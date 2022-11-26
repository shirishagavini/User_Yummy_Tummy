package com.example.yummytummy2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.ViewHolder>{


    private Context context;
    private List<MenuItemClass> list;

    public MenuItemAdapter(List<MenuItemClass> list,Context context) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public MenuItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        setting up the viewholder of the adapter
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuItemAdapter.ViewHolder holder, int position) {
        MenuItemClass item = list.get(position);
        holder.name.setText(item.getItemName());
        holder.price.setText(""+item.getPrice());
        holder.itemCheck.setChecked(item.getIsAdded());
        try{
            Picasso.get().load(item.getUrl()).placeholder(R.drawable.food_display).into(holder.itemImage);
        }catch (Exception e){

        }
        holder.itemCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(item.getIsAdded()){
                    item.setIsAdded(false);
                    Toast.makeText(view.getContext(), "item Removed", Toast.LENGTH_SHORT).show();
                    holder.itemCheck.setChecked(item.getIsAdded());
                }else if(!item.getIsAdded()){
                    item.setIsAdded(true);
                    Toast.makeText(view.getContext(), "item added", Toast.LENGTH_SHORT).show();
                    holder.itemCheck.setChecked(item.getIsAdded());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView name,price,quantity;
        public CheckBox itemCheck;
        public ImageView minus,plus,itemImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.foodTextView);
            price = itemView.findViewById(R.id.priceTextView);
            itemCheck = itemView.findViewById(R.id.checkbox);
            quantity = itemView.findViewById(R.id.quantityTextView);
            minus= itemView.findViewById(R.id.minusButtonImageView);
            plus= itemView.findViewById(R.id.plusButtonImageView);
            itemImage = itemView.findViewById(R.id.itemImageView);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            MenuItemClass currEle = list.get(getAdapterPosition());
            minus.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onClick(View view) {
                    if(currEle.getQuantity()>0){
                        int quantityy = currEle.getQuantity();
                        quantity.setText(quantityy-1+"");
                        currEle.setQuantity(quantityy-1);
                    }
                }
            });
            plus.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onClick(View view) {
                    int quantityy = currEle.getQuantity();
                    quantity.setText(quantityy+1+"");
                    currEle.setQuantity(quantityy+1);
                }
            });


            itemCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(currEle.getIsAdded()){
                        currEle.setIsAdded(false);
                        Toast.makeText(view.getContext(), "item Removed", Toast.LENGTH_SHORT).show();
                        itemCheck.setChecked(currEle.getIsAdded());
                    }else if(!currEle.getIsAdded()){
                        currEle.setIsAdded(true);
                        Toast.makeText(view.getContext(), "item added", Toast.LENGTH_SHORT).show();
                        itemCheck.setChecked(currEle.getIsAdded());
                    }
                }
            });
        }
    }
}
