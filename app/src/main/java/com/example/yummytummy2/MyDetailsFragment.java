package com.example.yummytummy2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class MyDetailsFragment extends BaseFragment
{

    public MyDetailsFragment()
    {
        // Required empty public constructor
    }
    protected EditText mYuserName, mYphoneuNumber, mYpincode, mYstate, mYaddress, mYlandmark;

    protected ClientDetails Details;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_my_details, container, false);

        mYuserName = view.findViewById(R.id.namedittext);
        mYphoneuNumber = view.findViewById(R.id.phoneEdittext);
        mYpincode = view.findViewById(R.id.pincodeEdittext);
        mYstate = view.findViewById(R.id.stateEdittext);
        mYaddress = view.findViewById(R.id.addressEdittext);
        mYlandmark = view.findViewById(R.id.landmarkEdittext);

//        getting the current user ID

        currUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore.getInstance().collection("CustomerDetails").document(currUserID).collection("PersonalDetails").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//              getting the documents of the client my details and converting them into the class of client details
                List<ClientDetails> details = queryDocumentSnapshots.toObjects(ClientDetails.class);
                Details = details.get(0);
//                setting up the information in the desired edit text for displaying
                mYuserName.setText(Details.getName());
                mYphoneuNumber.setText(Details.getPhone());
                mYpincode.setText(Details.getPincode());
                mYstate.setText(Details.getState());
                mYaddress.setText(Details.getAddress());
                mYlandmark.setText(Details.getLandmark());
            }
        }).addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e){

            }
        });
        return view;
    }
}