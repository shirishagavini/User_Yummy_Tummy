package com.example.yummytummy2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class NotificationFragement extends BaseFragment {

    public NotificationFragement() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    NotificationAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_notification_fragement, container, false);
        recyclerView = view.findViewById(R.id.recycelerView);
        try{
//          getting the current user ID
        currUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
            Query query = FirebaseFirestore.getInstance().collection("CustomerDetails")
                    .document(currUserID).collection("Orders Compeleted");
//          querying all the orders which has been completed by the vendor
            FirestoreRecyclerOptions<MyOrderDetails> items = new FirestoreRecyclerOptions
                    .Builder<MyOrderDetails>()
                    .setQuery(query,MyOrderDetails.class).build();
//            setting up the firebaserecycleroptions recycler adaptor for the items to be displayed
            LinearLayoutManager manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(manager);
//            initialising the notification and after
            adapter = new NotificationAdapter(items);
            recyclerView.setAdapter(adapter);
            adapter.startListening();
        }catch (Exception e){

        }
        return view;
    }
}