package com.example.yummytummy2;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class CartFragment extends BaseFragment {


    public CartFragment() {
        // Required empty public constructor
    }
//    declaring the variables
    protected MyCartAdapter adapter ;
    protected CheckBox Wallet,Cash;
    public static TextView totall,discount;
    public static List<MenuItemClass> cartList;
    protected AppCompatButton checkoutButton;
    protected List<ClientDetails> list ;
    protected int walletBalance;
    protected RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_cart, container, false);
        recyclerView = view.findViewById(R.id.resturantRecyclerView);
        Wallet = view.findViewById(R.id.walletCheckBox);
        Cash = view.findViewById(R.id.cashCheckBox);
        totall = view.findViewById(R.id.itemTotalNum);
        discount = view.findViewById(R.id.cartDiscountNum);
        checkoutButton = view.findViewById(R.id.proceedToCheckOut);
        Wallet.setChecked(true);
        currUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        try{
//            getting the card detail item from the firebase
            FirebaseFirestore.getInstance().collection("CustomerDetails")
//                    getting the cart items document from the firebase
                    .document(currUserID).collection("Cart").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    cartList = queryDocumentSnapshots.toObjects(MenuItemClass.class);
//casting the document into the menuitem class
                    if(cartList.size() == 0){
//                    loadFragment(new EmptyCartFragment(),false);
                    }
                    int total = 0,off,offffff;
                    for(int i=0;i<cartList.size();i++){
                        total = total + cartList.get(i).getQuantity()*cartList.get(i).getPrice();
                    }
//                  getting the cart total and applying up the discounts
                    off= total-(total/10);
                    offffff = total/10;
                    totall.setText("£ "+off);
                    discount.setText("£ "+offffff);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
//                loadFragment(new EmptyCartFragment(),false);

                }
            });
        }catch (Exception e ){

        }



        Query query = FirebaseFirestore.getInstance().collection("CustomerDetails")
                .document(currUserID).collection("Cart");
        FirestoreRecyclerOptions<MenuItemClass> items = new FirestoreRecyclerOptions
                .Builder<MenuItemClass>()
                .setQuery(query,MenuItemClass.class).build();
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
//        getting all the items from the database and setting up the recyclerview for displaying the items in the fragment layout
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        adapter = new MyCartAdapter(items);
        recyclerView.setAdapter(adapter);
//        setting up the adaptor
        adapter.startListening();
//      checking whether the customer has choosen wallet for making the payment of the items of the cart
        Wallet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b == true){
                    Cash.setChecked(false);
                }if(b==false){
                    Cash.setChecked(true);
                }
            }
        });
//      checking whether the customer has choosen cash for wanted for making the payment of the items of the cart
        Cash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b == true){
                    Wallet.setChecked(false);
                }if(b==false){
                    Wallet.setChecked(true);
                }
            }
        });

//        setting up the checkout button
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {int total = 0,off,offffff;
                for(int i=0;i<cartList.size();i++){
                    total = total + cartList.get(i).getQuantity()*cartList.get(i).getPrice();
                }
                off= total-(total/10);
//              getting the total of a cart

                FirebaseFirestore.getInstance().collection("CustomerDetails").document(currUserID).collection("PersonalDetails").get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

//                            getting the wallet balance from the customer details
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                list = queryDocumentSnapshots.toObjects(ClientDetails.class);
                                walletBalance = Integer.parseInt(list.get(0).getWalletBalance());
                                if(Wallet.isChecked() && walletBalance<off){
//                                    showing tools for the insufficient wallet balance
                                    Toast.makeText(getActivity(), "Insufficient wallet Balance", Toast.LENGTH_SHORT).show();
                                }
                                else if(Wallet.isChecked() && walletBalance >= off){
//                                    adding the orders into the customers completed section in the database
                                    addToOrders("Wallet");
//                                    deducting the wallet balance if the wallet is chosen as in payment option by the customer
                                    deductWalletBalance(walletBalance-off);
                                }else if(Cash.isChecked()){
                                    addToOrders("Cash");
//                                    adding the orders into the customers completed section in the database
                                    Toast.makeText(getActivity(), "Order SuccessFul", Toast.LENGTH_SHORT).show();
//                                    starting the dashboard activity
                                    startActivity(new Intent(getActivity(), DashBoardActivity.class));
                                    getActivity().finish();
                                }
                            }
                        });
            }
        });
        return view;
    }


    private void deductWalletBalance(int walletBalance) {
        currUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore.getInstance().collection("CustomerDetails").document(currUserID).collection("PersonalDetails")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//updating the wallet balance of the customer in the customers database
                queryDocumentSnapshots.getDocuments().get(0).getReference().update("walletBalance",""+walletBalance).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
//                                    starting the dashboard activity
                        startActivity(new Intent(getActivity(), DashBoardActivity.class));
                        getActivity().finish();
                    }
                });
            }
        });
    }

    private void addToOrders(String method){
//        getting the timestamp while placing the order
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String orderId = timestamp.getTime()+"";
        currUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        for(int i =0;i<cartList.size();i++){
            MenuItemClass curr = cartList.get(i);
            Timestamp ts=new Timestamp(System.currentTimeMillis());
            Date date=ts;
//
            FirebaseFirestore.getInstance().collection("Resturants").document(curr.getResId()).collection("InComplete Orders").add(new MyOrders(curr.getItemName(),curr.getItemId(), curr.getPrice(),curr.getQuantity(),orderId, timestamp.toString().substring(0,10),method));
            FirebaseFirestore.getInstance().collection("CustomerDetails").document(currUserID).collection("Orders").add(new MyOrders(curr.getItemName(),curr.getItemId(), curr.getPrice(),curr.getQuantity(),orderId, timestamp.toString().substring(0,10),method));
            if(i == cartList.size()-1){
                FirebaseFirestore.getInstance().collection("CustomerDetails").document(currUserID).collection("Cart").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        WriteBatch batch = FirebaseFirestore.getInstance().batch();
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot snapshot:list){
                            batch.delete(snapshot.getReference());
                        }
                        batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getActivity(), "Order SuccessFul", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        }
    }
}