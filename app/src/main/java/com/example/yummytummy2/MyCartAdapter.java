package com.example.yummytummy2;

import static com.example.yummytummy2.CartFragment.cartList;
import static com.example.yummytummy2.CartFragment.discount;
import static com.example.yummytummy2.CartFragment.totall;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;

public class MyCartAdapter extends FirestoreRecyclerAdapter<MenuItemClass,MyCartAdapter.ViewHolder> {
    public MyCartAdapter(@NonNull FirestoreRecyclerOptions<MenuItemClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyCartAdapter.ViewHolder holder, int i, @NonNull MenuItemClass model) {
        holder.name.setText(model.getItemName());
        holder.price.setText(""+model.getPrice()*model.getQuantity());
        holder.quantity.setText(""+model.getQuantity());
    }

    @NonNull
    @Override
    public MyCartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item,parent,false);
        return new MyCartAdapter.ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{
        public TextView name,price,quantity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.foodTextView);
            price = itemView.findViewById(R.id.priceTextView);
            quantity = itemView.findViewById(R.id.quantityTextView);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            Snackbar.make(view,"Do you want to Delete this item",Snackbar.LENGTH_LONG)
                    .setAction("Yes", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            cartList.remove(getAdapterPosition());
                            int total = 0,off,offffff;
                            for(int i=0;i<cartList.size();i++){
                                total = total + cartList.get(i).getQuantity()*cartList.get(i).getPrice();
                            }
                            off= total-(total/10);
                            offffff = total/10;
                            totall.setText("£ "+off);
                            discount.setText("£ "+offffff);
                            DocumentSnapshot snapshots = getSnapshots().getSnapshot(getAdapterPosition());
                            snapshots.getReference().delete();
                        }
                    }).show();
            return true;
        }
    }
}
