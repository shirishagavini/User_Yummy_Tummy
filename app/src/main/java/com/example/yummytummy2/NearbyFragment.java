package com.example.yummytummy2;

import android.annotation.SuppressLint;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class NearbyFragment extends BaseFragment {
    public NearbyFragment() {
        // Required empty public constructor
    }
    protected String clientPinCode;
    protected TextView title, resturantName;
    protected RecyclerView recyclerView, menuRecyclerView;
    protected AppCompatButton addToCart;
    protected BottomSheetDialog bottomSheeet;
    protected List<ResturantVendorDetails> details;
    protected ImageView back, profileImage;
    public static List<MenuItemClass> menuItems;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.nearby, container, false);
//        getting the current user ID
        currUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        title = view.findViewById(R.id.resturantAvailable);
//        initialising the bottom sheet
        bottomSheeet = new BottomSheetDialog(getActivity());
        FirebaseFirestore.getInstance().collection("CustomerDetails").document(currUserID).collection("PersonalDetails").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<ClientDetails> details = queryDocumentSnapshots.toObjects(ClientDetails.class);
                Log.e("manan",""+details);
                ClientDetails clientDetails = details.get(0);
                clientPinCode = clientDetails.getPincode();
                FirebaseFirestore.getInstance().collection("Pincode").document(clientPinCode).collection("Resturant Names").get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                List<ResturantVendorDetails> resDetails;
                                resDetails = queryDocumentSnapshots.toObjects(ResturantVendorDetails.class);
                                recyclerView = view.findViewById(R.id.resturantRecyclerView);
                                LinearLayoutManager linearmanager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setLayoutManager(linearmanager);
                                ResturantAdapter adapter = new ResturantAdapter(resDetails, getActivity());
                                adapter.onLongClickListner(new View.OnLongClickListener() {
                                    @Override
                                    public boolean onLongClick(View view) {
                                        int position = recyclerView.getChildAdapterPosition(view);
                                        callBottomSheet(position, clientPinCode,resDetails);
                                        return true;
                                    }
                                });
                                recyclerView.setAdapter(adapter);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                title.setText("No Resturant is Availble in your area");
                            }
                        });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
        return view;
    }


    private void callBottomSheet(int position, String pincode,List<ResturantVendorDetails> details) {
        ResturantVendorDetails currResturant = details.get(position);
        View bottomView = getLayoutInflater().from(getActivity()).inflate(R.layout.menu_sheet, null);

        resturantName = bottomView.findViewById(R.id.hotelNameTextView);
        back = bottomView.findViewById(R.id.arrowImageView);
        menuRecyclerView = bottomView.findViewById(R.id.bottomRecyclerView);
        addToCart = bottomView.findViewById(R.id.logInButton);
        profileImage = bottomView.findViewById(R.id.foodImageView);

        resturantName.setText(currResturant.getName());
// getting the menu of the restaurant from the firebase
        FirebaseFirestore.getInstance()
                .collection("Pincode")
                .document(pincode)
                .collection("Menu")
                .document(currResturant.getResturantId())
                .collection(currResturant.getResturantId())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        menuItems = queryDocumentSnapshots.toObjects(MenuItemClass.class);
//                        casting the menuitem document into the menuitem class
                        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//                        setting up the menu recyclerview for displaying the menu items in the bottom sheet
                        menuRecyclerView.setLayoutManager(manager);
                        menuRecyclerView.setHasFixedSize(true);
//                        initialising the menu item adaptor
                        MenuItemAdapter adapter = new MenuItemAdapter(menuItems,getActivity());
                        menuRecyclerView.setAdapter(adapter);
                    }
                });

//        setting up the add to cart button in the bottom sheet
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<menuItems.size();i++){
                    MenuItemClass currItems = menuItems.get(i);
                    if(currItems.getIsAdded() && currItems.getQuantity()>0){
                        currUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
//                        adding the menu items into the cart of the customer
                        FirebaseFirestore.getInstance().collection("CustomerDetails").document(currUserID).collection("Cart").add(currItems);
                    }
//dismissing the bottom sheet
                    bottomSheeet.dismiss();
                }
                Toast.makeText(getActivity(), "Items added to the cart", Toast.LENGTH_SHORT).show();
            }
        });
        bottomSheeet.setContentView(bottomView);
        bottomSheeet.show();
//        setting up the functionality of the back button for closing the bottom sheet
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheeet.dismiss();
            }
        });

    }

}