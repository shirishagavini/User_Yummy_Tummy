package com.example.yummytummy2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends BaseFragment {

    protected TextView curruserName,curremailId,currphoneNumber,currmyWallet,currwallet;
    protected CircleImageView currprofileImage;
    protected ImageView currmyDetails,currpaymentMethod,currmyOrder,currmyAddress,currabout,curreditProfile;
    protected AppCompatButton currlogout;
    public static int profileMode = 0;
    public static int walletBalance = 0;

//this is the profile fragment for displaying all the data related to the user
    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        view =  inflater.inflate(R.layout.fragment_profile, container, false);
        currlogout = view.findViewById(R.id.logOutButton);
        curruserName = view.findViewById(R.id.userNameTextView);
        currprofileImage = view.findViewById(R.id.profileImageView);
        curremailId = view.findViewById(R.id.emailIdTextView);
        currphoneNumber = view.findViewById(R.id.numberTextView);
        currmyDetails = view.findViewById(R.id.myDetailsArrow);
        currmyWallet = view.findViewById(R.id.helpArrow);
        currmyOrder = view.findViewById(R.id.myOrderArrow);
        currpaymentMethod = view.findViewById(R.id.paymentMethodsArrow);
        curreditProfile = view.findViewById(R.id.editProfileImageView);
        currwallet = view.findViewById(R.id.helpTextView);
// getting the instance of the current user
        currUser = FirebaseAuth.getInstance().getCurrentUser();
        currUserID = currUser.getUid();

        if(currUser!=null){
            curruserName.setText(currUser.getDisplayName());
            if(currUser.getPhotoUrl() != null){
//                setting up the profile photo
                Glide.with(getActivity())
                        .load(currUser.getPhotoUrl())
                        .into(currprofileImage);
            }
        }
//         getting the basic details of the customer from the firebase
        FirebaseFirestore.getInstance().collection("CustomerDetails").document(currUserID).collection("PersonalDetails").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                getting the document of the client details from the firebase
                List<ClientDetails> details = queryDocumentSnapshots.toObjects(ClientDetails.class);
                ClientDetails currDetails = details.get(0);
//                setting of the details in the appropriate places or to be displayed on the profile
                curruserName.setText(currDetails.getName());
                currphoneNumber.setText(currDetails.getPhone());
                curremailId.setText(currUser.getEmail());
                walletBalance = Integer.parseInt(currDetails.getWalletBalance());
                currmyWallet.setText("£ "+currDetails.getWalletBalance());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e){
            }
        });
//         setting up my details and setting the profile mood as well as starting the auxiliary activity for displaying the specific fragment
        currmyDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileMode = 1;
                startActivity(new Intent(getActivity(), AucilaryActivity.class));
            }
        });

//         setting up my Orders and setting the profile mood as well as starting the auxiliary activity for displaying the specific fragment

        currmyOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileMode = 3;
                startActivity(new Intent(getActivity(), AucilaryActivity.class));
            }
        });

//         setting up my Wallet and setting the profile mood as well as starting the auxiliary activity for displaying the specific fragment
        currmyWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileMode = 2;
                   startActivity(new Intent(getActivity(), AucilaryActivity.class));
            }
        });

        currwallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileMode = 2;
                startActivity(new Intent(getActivity(), AucilaryActivity.class));
            }
        });

        currprofileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent changeProfilePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(changeProfilePicture.resolveActivity(getActivity().getPackageManager()) != null){
                    startActivityForResult(changeProfilePicture,95400);
                }
            }
        });

//         setting up Edit Profile and setting the profile mood as well as starting the auxiliary activity for displaying the specific fragment
        curreditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileMode = 5;
                startActivity(new Intent(getActivity(), AucilaryActivity.class));
            }
        });

//        Setting up the logout functionality

        currlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthUI.getInstance().signOut(getActivity());
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 95400){
            if(resultCode == Activity.RESULT_OK){
//                Converting image into bitmap
                Bitmap image  = (Bitmap)data.getExtras().get("data");
                currprofileImage.setImageBitmap(image);
//                calling function for seeting up the image
                setImage(image);
            }else{
                Toast.makeText(getActivity(), "Unable to upload Image 1", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setImage(Bitmap image) {
        currUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG,100,stream);
        StorageReference reference = FirebaseStorage.getInstance().getReference()
                .child("Images")
                .child("profile Images")
                .child(currUserID+".jpeg");
        reference.putBytes(stream.toByteArray())
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        getProfileUrl(reference);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Unable to upload Image 2 ", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getProfileUrl(StorageReference reference) {
        reference.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        currUser = FirebaseAuth.getInstance().getCurrentUser();
                        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                                .setPhotoUri(uri)
                                .build();
                        currUser.updateProfile(request)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(getActivity(),"upload successfull",Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getActivity(), "Unable to upload Image 3", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Unable to upload Image 4", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}