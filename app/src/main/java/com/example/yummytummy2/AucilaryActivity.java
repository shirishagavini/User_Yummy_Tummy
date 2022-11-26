package com.example.yummytummy2;

import static com.example.yummytummy2.ProfileFragment.profileMode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class AucilaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//setting up the moon for the profile
        if(profileMode == 1){
//            loading the my details fragment
            loadFragment(new MyDetailsFragment(),false);
        }else if(profileMode == 2){
//            loading the my Wallet fragment
            loadFragment(new MyWalletFragment(),false);
        }else if(profileMode == 3){
//            loading the my Orders fragment
            loadFragment(new myOrdersFragment(),false);
        }
    }

    protected void loadFragment(Fragment fragment, boolean add){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.mainframeLayout,fragment);
        if(add){
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

}
