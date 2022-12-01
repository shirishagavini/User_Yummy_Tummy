package com.example.yummytummy2;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class UserFormFragment extends BaseFragment
{


    public UserFormFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //        this is the fragment is responsible for getting the information from the restaurant owner and putting it into the database

        view =  inflater.inflate(R.layout.fragment_user_form, container, false);
        AppCompatButton submitButton = (AppCompatButton)view.findViewById(R.id.loginButton);

        EditText nameTextView,phoneTextView,addressTextView,landmarkTextView,stateTextView,pincodeTextView;

        nameTextView = (EditText)view.findViewById(R.id.namedittext);
        phoneTextView = (EditText)view.findViewById(R.id.phoneEdittext);
        addressTextView = (EditText)view.findViewById(R.id.addressEdittext);
        pincodeTextView = (EditText)view.findViewById(R.id.pincodeEdittext);
        stateTextView = (EditText)view.findViewById(R.id.stateEdittext);
        landmarkTextView = (EditText)view.findViewById(R.id.landmarkEdittext);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name,phone,pincode,address,state,landmark;
//              getting the data from the edittext and converting them into the string
                name = nameTextView.getText().toString();
                phone = phoneTextView.getText().toString();
                address = addressTextView.getText().toString();
                pincode = pincodeTextView.getText().toString();
                state = stateTextView.getText().toString();
                landmark = landmarkTextView.getText().toString();
//                checking if the field provided are not empty
                if(!name.isEmpty() && !phone.isEmpty() && !pincode.isEmpty() && !address.isEmpty() && !state.isEmpty() && !landmark.isEmpty()){
                    currUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    FirebaseFirestore.getInstance().collection("CustomerDetails").document(currUserID).collection("Orders");
                    FirebaseFirestore.getInstance()
                            .collection("CustomerDetails")
                            .document(currUserID)
                            .collection("PersonalDetails")
                            .add(new ClientDetails( name,  phone,  pincode,  address,  state,  landmark))
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    FirebaseFirestore.getInstance().collection("Users").document(currUserID).collection("Details").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            List<UserData> details = queryDocumentSnapshots.toObjects(UserData.class);
                                            for(int i=0;i<details.size();i++){
//                                                updating the respond details as form field when the form is actually paid by the vendor
                                                queryDocumentSnapshots.getDocuments().get(i).getReference().update("isFormFilled",""+true);
                                                }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e){
                                            Toast.makeText(getActivity(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                        }
                                    });
//                                    setting up the toast message and launching the dashboard activity on Success
                                    Toast.makeText(getActivity(),"Data Saved",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getActivity(), DashBoardActivity.class));
                                    getActivity().finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e){
                            Toast.makeText(getActivity(),"Unable to process",Toast.LENGTH_SHORT).show();
                        }
                    });



                }
            }
        });
        return view;
    }
}