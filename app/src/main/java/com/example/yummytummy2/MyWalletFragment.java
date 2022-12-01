package com.example.yummytummy2;

import static com.example.yummytummy2.ProfileFragment.walletBalance;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class MyWalletFragment extends BaseFragment {

    public MyWalletFragment() {
    }

    protected AppCompatButton addButton;
    protected EditText amountEditTest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
//        this fragment is made for adding the balance into the wallet
        view =  inflater.inflate(R.layout.fragment_my_wallet, container, false);
        addButton = view.findViewById(R.id.addToWallet);
        amountEditTest = view.findViewById(R.id.amount);
//        setting up the add button
        addButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
//                getting the current user ID
                currUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                FirebaseFirestore.getInstance().collection("CustomerDetails").document(currUserID).collection("PersonalDetails")
                        .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>()
                {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots)
                    {
//                        fetching the current wallet balance of the client
                        int balance = Integer.parseInt(amountEditTest.getText().toString())+walletBalance;
//                        getting the amount from the edit text which should be added in the wallet
                        queryDocumentSnapshots.getDocuments().get(0).getReference().update("walletBalance",""+balance).addOnSuccessListener(new OnSuccessListener<Void>()
                        {
                            @Override
                            public void onSuccess(Void unused)
                            {
                                Toast.makeText(getActivity(), "Balance is added to wallet", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getActivity(), DashBoardActivity.class));
                                getActivity().finish();
                            }
                        });
                    }
                });
            }
        });
        return view;
    }
}