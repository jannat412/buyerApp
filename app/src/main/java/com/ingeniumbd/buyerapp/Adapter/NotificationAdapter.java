package com.ingeniumbd.buyerapp.Adapter;

import android.app.Activity;
import android.app.Notification;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ingeniumbd.buyerapp.R;

/**
 * Created by Princess on 2/17/2018.
 */

public class NotificationAdapter extends BaseAdapter {

    Activity act;

    public NotificationAdapter(Activity ct) {
        act = ct;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public class NotificationHolder {
        ImageView profileImage;
        TextView nameConfirmed, time;
    }

    @Override
    public View getView(int i, View myView, ViewGroup viewGroup) {
        NotificationHolder nv = new NotificationHolder();
        myView = act.getLayoutInflater().inflate(R.layout.notification_product, viewGroup, false);
        SpannableString styledString = new SpannableString("Sanaz Confirmed your booking at Dubai");
        styledString.setSpan(new RelativeSizeSpan(1.4f),0,5,0);
        styledString.setSpan(new StyleSpan(Typeface.BOLD),0,5,0);

        nv.nameConfirmed = (TextView)myView.findViewById(R.id.nameConfirmedN);
        nv.nameConfirmed.setText(styledString);
        return myView;
    }
}
