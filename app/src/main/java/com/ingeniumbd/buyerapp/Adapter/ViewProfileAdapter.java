package com.ingeniumbd.buyerapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ingeniumbd.buyerapp.R;
import com.ingeniumbd.buyerapp.activities.MainActivity;

import java.util.ArrayList;

/**
 * Created by Princess on 2/10/2018.
 */

public class ViewProfileAdapter extends BaseAdapter {

    Activity ct;
    ArrayList<String>offerTitle;
    ArrayList<String>regularPrice;
    ArrayList<String>offerPrice;
    public ViewProfileAdapter(Activity context,ArrayList<String>offerT,ArrayList<String>regularP,ArrayList<String>offerP){
        ct = context;
        offerTitle = offerT;
        regularPrice = regularP;
        offerPrice = offerP;
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

    public class ViewProfileHolder{
        ImageView productImage;
        TextView offerTitleTextView,regularPriceTextView,offerPriceTextView;
    }
    @Override
    public View getView(int i, View myView, ViewGroup viewGroup) {

        ViewProfileHolder viewProfileHolder = new ViewProfileHolder();
        myView = ct.getLayoutInflater().inflate(R.layout.product_list,viewGroup,false);

        viewProfileHolder.productImage = (ImageView)myView.findViewById(R.id.productImageOne);
        viewProfileHolder.offerTitleTextView  = (TextView)myView.findViewById(R.id.OfferTitle);
        viewProfileHolder.regularPriceTextView  = (TextView)myView.findViewById(R.id.regularPrice);
        viewProfileHolder.offerPriceTextView  = (TextView)myView.findViewById(R.id.offerPrice);

        viewProfileHolder.offerTitleTextView.setText(offerTitle.get(i));
        Log.e("OfferTitle",offerTitle.get(i));
        viewProfileHolder.regularPriceTextView.setText(regularPrice.get(i));
        viewProfileHolder.offerPriceTextView.setText(offerPrice.get(i));

        return myView;
    }
}
