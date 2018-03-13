package com.ingeniumbd.buyerapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.ingeniumbd.buyerapp.R;
import com.ingeniumbd.buyerapp.model.SellerProfile;

public class ProductDetailView extends AppCompatActivity {

    Firebase firebaseProducts, firebaseSeller;
    TextView shopName, detailAddress, country, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail_view);

        shopName = (TextView) findViewById(R.id.ShopName);
        detailAddress = (TextView) findViewById(R.id.detailAddress);
        //country = (TextView) findViewById(R.id.country);
        phone = (TextView) findViewById(R.id.phone);

        firebaseSeller = new Firebase("https://sa-11-ce9b8.firebaseio.com/SellerProfile");
        firebaseProducts = new Firebase("https://sa-11-ce9b8.firebaseio.com/Products");

        firebaseSeller.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot cld : dataSnapshot.getChildren()) {
                    SellerProfile seller = cld.getValue(SellerProfile.class);
                    String sName = seller.getStoreName();
                    String sAddress = seller.getAddress();
                    //String sCountry = seller.getCountry();
                    String sPhone = seller.getPhone();
                    // String cover = seller.getCover_photo();
                    String profile = seller.getProfile_picture();

                    // Log.e("VAlue:",cover);
                    shopName.setText(sName);
                    detailAddress.setText(sAddress);
                    //country.setText(sCountry);
                    phone.setText(sPhone);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }
}
