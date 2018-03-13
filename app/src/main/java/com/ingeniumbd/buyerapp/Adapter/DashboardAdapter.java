package com.ingeniumbd.buyerapp.Adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ingeniumbd.buyerapp.R;
import com.ingeniumbd.buyerapp.activities.ViewProfile;
import com.ingeniumbd.buyerapp.model.SellerProfile;

import java.util.ArrayList;

/**
 * Created by Princess on 2/8/2018.
 */

public class DashboardAdapter extends BaseAdapter {

    private Activity ct;
    ArrayList<String> offerTitle = new ArrayList<String>();
    ArrayList<String> regularPrice = new ArrayList<String>();
    ArrayList<String> offerPrice = new ArrayList<String>();
    ArrayList<String> productImage = new ArrayList<String>();
    ArrayList<String> idList = new ArrayList<String>();

    public DashboardAdapter(Activity context, ArrayList<String> offerT, ArrayList<String> regularP,
                            ArrayList<String> offerP, ArrayList<String> offerImage, ArrayList<String> myList) {
        ct = context;
        offerTitle = offerT;
        regularPrice = regularP;
        offerPrice = offerP;
        productImage = offerImage;
        idList = myList;
    }

    @Override
    public int getCount() {
        return offerTitle.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public class MyHolder {
        ImageView productImageView, bookNow;
        TextView offerTitle_tv, regularPrice_tv, offerPrice_tv, viewProfile_tv;
    }

    @Override
    public View getView(int i, View myView, ViewGroup viewGroup) {
        final int position = i;
        MyHolder myHolder = new MyHolder();
        myView = ct.getLayoutInflater().inflate(R.layout.dashboard_product_list, viewGroup, false);

        myHolder.offerTitle_tv = (TextView) myView.findViewById(R.id.OfferTitle);
        myHolder.regularPrice_tv = (TextView) myView.findViewById(R.id.regularPrice);
        myHolder.offerPrice_tv = (TextView) myView.findViewById(R.id.offerPrice);
        myHolder.viewProfile_tv = (TextView) myView.findViewById(R.id.viewProfile);

        myHolder.productImageView = (ImageView) myView.findViewById(R.id.productImageOne);

        myHolder.offerTitle_tv.setText(offerTitle.get(i));
        myHolder.regularPrice_tv.setText(regularPrice.get(i));
        myHolder.offerPrice_tv.setText(offerPrice.get(i));

        if (!productImage.get(i).equals("empty")) {
            Glide.with(ct)
                    .load(productImage.get(i))
                    .into(myHolder.productImageView);

        }
        myHolder.viewProfile_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sellerId = idList.get(position).toString();
                //Toast.makeText(ct, "Clicked: " + idList.get(position).toString(), Toast.LENGTH_LONG).show();
                Intent i = new Intent(ct, ViewProfile.class);
                i.putExtra("id", sellerId);
                ct.startActivity(i);
            }
        });

        return myView;
    }
}
