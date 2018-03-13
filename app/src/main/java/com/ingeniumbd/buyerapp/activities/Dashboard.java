package com.ingeniumbd.buyerapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.ingeniumbd.buyerapp.Adapter.DashboardAdapter;
import com.ingeniumbd.buyerapp.R;
import com.ingeniumbd.buyerapp.model.Products;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {

    private ListView product_list;
    private DashboardAdapter dashboardAdapter;
    Firebase firebaseProducts;
    ArrayList<String> offerTitle = new ArrayList<String>();
    ArrayList<String> regularPrice = new ArrayList<String>();
    ArrayList<String> offerPrice = new ArrayList<String>();
    ArrayList<String> profileImage = new ArrayList<String>();
    ArrayList<String> userIdList = new ArrayList<String>();
    int i = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashbord);

        final ArrayList<String> myList = (ArrayList<String>) getIntent().getSerializableExtra("sellerIdList");
        product_list = findViewById(R.id.product_listb);
        dashboardAdapter = new DashboardAdapter(Dashboard.this, offerTitle, regularPrice, offerPrice, profileImage, userIdList);
        product_list.setAdapter(dashboardAdapter);

        firebaseProducts = new Firebase("https://sa-11-ce9b8.firebaseio.com/Products");

        for (i = 0; i < myList.size(); i++) {
            final String value = myList.get(i).toString();
            firebaseProducts.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot cld : dataSnapshot.getChildren()) {
                        Products products = cld.getValue(Products.class);
                        String product_id = products.getId();
                        if (value.equals(product_id)) {
                            String offerT = products.getOfferTitle();
                            String offerP = products.getOfferPrice();
                            String regularP = products.getRegularPrice();
                            String productImage = products.getProductImageOne();
                            String user_id = products.getUserId();

                            offerTitle.add(offerT);
                            offerPrice.add(offerP);
                            regularPrice.add(regularP);
                            profileImage.add(productImage);
                            userIdList.add(user_id);

                            dashboardAdapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }

/*        if (offerTitle.size() >= 0) {
            DashboardAdapter dashboardAdapter = new DashboardAdapter(Dashboard.this, offerTitle, regularPrice, offerPrice, profileImage, userIdList);
            product_list.setAdapter(dashboardAdapter);
        }*/


    }
}
