package com.example.yummytummy2;

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

public class NotificationAdapter extends FirestoreRecyclerAdapter<MyOrderDetails,NotificationAdapter.ViewHolder>  {
    public NotificationAdapter(@NonNull FirestoreRecyclerOptions<MyOrderDetails> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder viewHolder, int i, @NonNull MyOrderDetails myOrderDetails) {
        viewHolder.text.setText("Your order of " +myOrderDetails.getQuantity()+" "+myOrderDetails.getItemName()+" is completed succesfully");
    }

    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notification_layout,viewGroup,false);
        return new NotificationAdapter.ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{
        public TextView text;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.notifictaion_content_textView);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            Snackbar.make(view,"Do you want to Delete this item",Snackbar.LENGTH_LONG)
                    .setAction("Yes", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            DocumentSnapshot snapshots = getSnapshots().getSnapshot(getAdapterPosition());
                            snapshots.getReference().delete();
                        }
                    }).show();
            return true;
        }
    }
}
