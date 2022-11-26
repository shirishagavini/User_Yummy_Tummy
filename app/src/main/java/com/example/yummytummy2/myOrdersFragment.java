package com.example.yummytummy2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;


public class myOrdersFragment extends BaseFragment {

    protected RecyclerView recyclerView;
    public myOrdersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        this is a my order is fragment in which the order history of all the orders that has been placed by the client are being displayed
        view =  inflater.inflate(R.layout.fragment_my_orders, container, false);
        recyclerView = view.findViewById(R.id.recycelerView);
//        getting the user ID of the client
        currUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore.getInstance().collection("CustomerDetails").document(currUserID).collection("Orders").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                getting the documents of all the orders which has been successfully done by the customer
                List<MyOrderDetails> list = queryDocumentSnapshots.toObjects(MyOrderDetails.class);
//                setting up the recyclerview for displaying all the orders
                LinearLayoutManager manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(manager);
//              initialising my order adaptor
                MyOrderAdapter adapter = new MyOrderAdapter(list,getActivity());
                recyclerView.setAdapter(adapter);
            }
        });

        return view;
    }
}