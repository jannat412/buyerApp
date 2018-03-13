package com.ingeniumbd.buyerapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.StorageReference;
import com.ingeniumbd.buyerapp.R;
import com.ingeniumbd.buyerapp.model.BuyerProfile;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class EditProfile extends AppCompatActivity {

    private static final int GALLERY_REQUEST_CODE = 1;
    private static final int PICK_IMAGE_REQUEST = 234;
    EditText name, address, city, country, phone;
    private static final String TAG = EditProfile.class.getSimpleName();
    String uKey, id;
    Button btnEditProfile;
    Firebase firebaseName, firebaseAddress, firebaseCity, firebaseCountry, firebasePhone, firebaseBuyerPrifile;
    private Uri uri;
    private StorageReference storageReference;
    private ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        image = (ImageView) findViewById(R.id.imageEditProfile);
        btnEditProfile = (Button) findViewById(R.id.editProfileDone);
        name = (EditText) findViewById(R.id.editProfile_activity_name);
        address = (EditText) findViewById(R.id.editProfile_activity_address);
        city = (EditText) findViewById(R.id.editProfile_activity_city);
        country = (EditText) findViewById(R.id.editProfile_activity_country);
        phone = (EditText) findViewById(R.id.editProfile_activity_phone);
        Log.e(TAG, "User");

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(EditProfile.this);
        uKey = preferences.getString("Key", "");
        Log.e("userKey", uKey);

        firebaseBuyerPrifile = new Firebase("https://sa-11-ce9b8.firebaseio.com/BuyerProfile");
        firebaseBuyerPrifile.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot cld : dataSnapshot.getChildren()) {
                    BuyerProfile buyerProfile = cld.getValue(BuyerProfile.class);
                    if (uKey.equals(buyerProfile.getUserId())) {

                        String uName = buyerProfile.getUserName();
                        String uAddress = buyerProfile.getAddress();
                        String uCity = buyerProfile.getCity();
                        String uCountry = buyerProfile.getCountry();
                        String uPhone = buyerProfile.getUserPhone();
                        id = buyerProfile.getId();
                        Log.e(TAG, "Id: " + id);
                        name.setText(uName);
                        address.setText(uAddress);
                        city.setText(uCity);
                        country.setText(uCountry);
                        phone.setText(uPhone);
                    }

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String uName = name.getText().toString();
                String uAddress = address.getText().toString();
                String uCity = city.getText().toString();
                String uCountry = country.getText().toString();
                String uPhone = phone.getText().toString();

                if (!name.equals("")) {
                    firebaseName = new Firebase("https://sa-11-ce9b8.firebaseio.com/BuyerProfile/" + id + "/userName");
                    firebaseName.setValue(uName);
                }

                if (!address.equals("")) {
                    firebaseAddress = new Firebase("https://sa-11-ce9b8.firebaseio.com/BuyerProfile/" + id + "/address");
                    firebaseAddress.setValue(uAddress);
                }
                if (!city.equals("")) {
                    firebaseCity = new Firebase("https://sa-11-ce9b8.firebaseio.com/BuyerProfile/" + id + "/city");
                    firebaseCity.setValue(uCity);
                }
                if (!country.equals("")) {
                    firebaseCountry = new Firebase("https://sa-11-ce9b8.firebaseio.com/BuyerProfile/" + id + "/country");
                    firebaseCountry.setValue(uCountry);
                }
                if (!phone.equals("")) {
                    firebasePhone = new Firebase("https://sa-11-ce9b8.firebaseio.com/BuyerProfile/" + id + "/userPhone");
                    firebasePhone.setValue(uPhone);
                }

                if (uri != null) {
                    final StorageReference filePath = storageReference.child("Photos").child(uri.getLastPathSegment());
                }
                Toast.makeText(EditProfile.this, "Updated Successfully", Toast.LENGTH_SHORT).show();

            }
        });
    }

    /**
     * ---------image upload event-------
     **/
    public void imageUpload(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select AddOfferData"), PICK_IMAGE_REQUEST);
    }

    /**
     * ---when back from activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            uri = data.getData();
            CropImage.activity(uri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                uri = result.getUri();
                image.setImageResource(R.drawable.upload_documents_icon_red);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}
