package com.example.yummytummy2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
 import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;


public class DashBoardActivity extends AppCompatActivity {

    //  Variables declared the drawer layout
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    public static List<LocationClass> locationClassList;

    // Make sure to be using androidx.appcompat.app.ActionBarDrawerToggle version.
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

//      Setting up the custom toolbar for the drawer layout.

        toolbar = (Toolbar) findViewById(R.id.customToolbar);
        setSupportActionBar(toolbar);




        // This will display an Up icon (<-), we will replace it with hamburger later
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer);
//      Setting the up the drawer layout.
        // Find our drawer view
        drawerToggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
        if(drawerToggle == null){
            Log.e("error","hello");
        }

//        Getting the locationof all the resturants from te database
        FirebaseFirestore.getInstance().collection("ResturantLocations").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<LocationClass> locationArray = queryDocumentSnapshots.toObjects(LocationClass.class);
                locationClassList = locationArray;
            }
        });

        // Setup toggle to display hamburger icon with nice animation
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();

        // Tie DrawerLayout events to the ActionBarToggle
//      It is making the listner for drawer layout.
        mDrawer.addDrawerListener(drawerToggle);

        nvDrawer = (NavigationView) findViewById(R.id.side_navigation);
//      Setting up the custom base fragment
        Fragment home = new HomeFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.framelayout,home);
        transaction.commit();

        setupDrawerContent(nvDrawer);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        //                      setting up the menuItem
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }


    public void selectDrawerItem(MenuItem menuItem) {
        Fragment fragment = null;
        switch(menuItem.getItemId()) {
            case R.id.Home:
                fragment = new HomeFragment();
                break;
            case R.id.notisfication:
                fragment = new NotificationFragement();
                break;
            case R.id.Cart:
                fragment = new CartFragment();
                break;
            case R.id.profile:
                fragment = new ProfileFragment();
                break;
            case R.id.resturants:
                fragment = new MapsFragment();
                break;
            case R.id.near_resturants:
                fragment = new NearbyFragment();
                break;
            default:
                fragment = new HomeFragment();
        }
//      Getting fragment and setting the main framelayout
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.framelayout,fragment);
        transaction.commit();

        menuItem.setChecked(true);
        //      Setting the title on the drawer layout
        setTitle(menuItem.getTitle());
        //       closing the drawer
        mDrawer.closeDrawers();

    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

}