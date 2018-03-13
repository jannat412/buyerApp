package com.ingeniumbd.buyerapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.ingeniumbd.buyerapp.Adapter.NotificationAdapter;
import com.ingeniumbd.buyerapp.R;
import com.ingeniumbd.buyerapp.model.SellerProfile;

import java.util.ArrayList;

public class Notification extends AppCompatActivity {

    ListView lv;
    Firebase firebase;
    ArrayList<String>companyImage = new ArrayList<String>();
    ArrayList<String>companyName = new ArrayList<String>();
    ArrayList<String>companyCity = new ArrayList<String>();
    ArrayList<String>confirmedTime = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        firebase = new Firebase("https://sa-11-ce9b8.firebaseio.com/Products");
        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot cld:dataSnapshot.getChildren()){
                    SellerProfile value = cld.getValue(SellerProfile.class);

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        lv = (ListView)findViewById(R.id.lv_notification);
        NotificationAdapter adapter = new NotificationAdapter(Notification.this);
        lv.setAdapter(adapter);
    }
}
