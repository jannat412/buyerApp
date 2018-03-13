package com.ingeniumbd.buyerapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.ingeniumbd.buyerapp.Adapter.ViewProfileAdapter;
import com.ingeniumbd.buyerapp.R;
import com.ingeniumbd.buyerapp.model.Products;
import com.ingeniumbd.buyerapp.model.SellerProfile;

import java.util.ArrayList;

public class ViewProfile extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView lv;
    Firebase firebaseProducts, firebaseSeller;
    String offerTitle, regularPrice, offerPrice;
    ArrayList<String> array_offerTitle = new ArrayList<String>();
    ArrayList<String> array_regularPrice = new ArrayList<String>();
    ArrayList<String> array_offerPrice = new ArrayList<String>();
    ArrayList<String> array_productsId = new ArrayList<String>();
    String userId, sellerProductId;
    ImageView coverPic, proPic;
    TextView shopName, detailAddress, country, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        lv = (ListView) findViewById(R.id.viewProfileListView);
        shopName = (TextView) findViewById(R.id.ShopName);
        detailAddress = (TextView) findViewById(R.id.detailAddress);
        country = (TextView) findViewById(R.id.country);
        phone = (TextView) findViewById(R.id.phone);

        coverPic = (ImageView) findViewById(R.id.cover_imageview);
        proPic = (ImageView) findViewById(R.id.profile_image);

        final Intent intent = getIntent();
        userId = intent.getExtras().getString("id");

        firebaseSeller = new Firebase("https://sa-11-ce9b8.firebaseio.com/SellerProfile");

        firebaseProducts = new Firebase("https://sa-11-ce9b8.firebaseio.com/Products");
        firebaseProducts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot cld : dataSnapshot.getChildren()) {
                    Products products = cld.getValue(Products.class);
                    String id = products.getUserId();
                    if (userId.equals(id)) {
                        offerTitle = products.getOfferTitle();
                        regularPrice = products.getRegularPrice();
                        offerPrice = products.getOfferPrice();
                        sellerProductId = products.getUserId();
                        String product_id = products.getId();
                        Log.e("Firebase Regular Price:", regularPrice);

                        array_offerTitle.add(offerTitle);
                        array_regularPrice.add(regularPrice);
                        array_offerPrice.add(offerPrice);
                        array_productsId.add(product_id);

                    }
                }
                if (array_offerTitle.size() > 0) {
                    ViewProfileAdapter viewProfileAdapter = new ViewProfileAdapter(ViewProfile.this, array_offerTitle, array_regularPrice, array_offerPrice);
                    lv.setAdapter(viewProfileAdapter);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        firebaseSeller.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot cld : dataSnapshot.getChildren()) {
                    SellerProfile seller = cld.getValue(SellerProfile.class);
                    String sName = seller.getStoreName();
                    String sAddress = seller.getAddress();
                    String sCountry = seller.getCountry();
                    String sPhone = seller.getPhone();
                    // String cover = seller.getCover_photo();
                    String profile = seller.getProfile_picture();

                    // Log.e("VAlue:",cover);
                    shopName.setText(sName);
                    detailAddress.setText(sAddress);
                    country.setText(sCountry);
                    phone.setText(sPhone);

                   /* if (!cover.equals("empty")) {
                        Glide.with(ViewProfile.this)
                                .load(cover)
                                .into(coverPic);

                    }*/

                    if (!profile.equals("empty")) {
                        Glide.with(ViewProfile.this)
                                .load(profile)
                                .into(proPic);

                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ViewProfile.this, "id: " + array_productsId.get(i), Toast.LENGTH_LONG).show();
                String id = array_productsId.get(i);
                Intent myIntent = new Intent(ViewProfile.this, ProductDetailView.class);
                myIntent.putExtra("product_id", id);
                startActivity(myIntent);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(ViewProfile.this, "id: " + array_productsId.get(i), Toast.LENGTH_LONG).show();
        String id = array_productsId.get(i);
        Intent myIntent = new Intent(ViewProfile.this, ProductDetailView.class);
        myIntent.putExtra("product_id", id);
        startActivity(myIntent);
    }
}
