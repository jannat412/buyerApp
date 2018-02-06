package com.ingeniumbd.buyerapp.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ingeniumbd.buyerapp.R;
import com.ingeniumbd.buyerapp.infrastructure.Constant;
import com.ingeniumbd.buyerapp.model.SellerProfile;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    private static final String TAG = MainActivity.class.getSimpleName();
    private GoogleMap mGoogleMap;
    private Double geolat, geolong;
    Button button;
    Spinner day_spinnerM, time_spinnerM;
    LinearLayout serviceType, dateSet, timeSet;
    private View navHeader;
    Integer cnt = 0;
    ListView listView;
    ArrayList<String> service_type = new ArrayList<>();
    TextView dateTextView, timeTextView;
    Integer yearG, monthOfYear, dayOfMonthG;
    Firebase firebaseCategory;
    RelativeLayout searchLayoutR;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.main_activity_map);
        mapFragment.getMapAsync(this);

        firebaseCategory = new Firebase("https://sa-11-ce9b8.firebaseio.com/SellerProfile");
        firebaseCategory.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot cld : dataSnapshot.getChildren()) {
                    SellerProfile sp = cld.getValue(SellerProfile.class);
                    String category_item = sp.getCategory();
                    service_type.add(category_item);
                    Log.d("Data", category_item);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        serviceType = (LinearLayout) findViewById(R.id.serviceTypeLL);
        dateSet = (LinearLayout) findViewById(R.id.date_set_layout);
        timeSet = (LinearLayout) findViewById(R.id.time_set_layout);
        dateTextView = (TextView) findViewById(R.id.date_picker);
        timeTextView = (TextView) findViewById(R.id.time_picker);
        RelativeLayout searchLayout=(RelativeLayout)findViewById(R.id.search_layout);

        searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(Gravity.END);
            }
        });

        listView = (ListView) findViewById(R.id.serviceTypeLV);
        ArrayAdapter myAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, service_type);
        listView.setAdapter(myAdapter);

        serviceType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cnt++;
                RelativeLayout rl = (RelativeLayout) findViewById(R.id.serviceTypeRL);
                if (cnt % 2 == 1) {
                    rl.setVisibility(View.VISIBLE);
                } else {
                    rl.setVisibility(View.GONE);
                }
                Toast.makeText(MainActivity.this, "Cliecked", Toast.LENGTH_SHORT).show();
            }
        });


        dateSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                new android.app.DatePickerDialog(
                        MainActivity.this,
                        new android.app.DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                Log.e("Orignal", "Got clicked");
                                yearG = year;
                                monthOfYear = month;
                                dayOfMonthG = dayOfMonth;

                                String date = dayOfMonthG + "/" + (++monthOfYear) + "/" + yearG;
                                dateTextView.setText(date);
                                //Toast.makeText(getApplicationContext(), "Y:" + yearG + "M:" + monthOfYear + "D:" + dayOfMonthG, Toast.LENGTH_LONG);
                                // Log.e("Year:", yearG.toString());
                                // Log.e("Mon:", monthOfYear.toString());

                            }
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                ).show();
            }
        });


        timeSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar now = Calendar.getInstance();
                new android.app.TimePickerDialog(
                        MainActivity.this,
                        new android.app.TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker var1, int hourOfDay, int var3) {

                                String status = "AM";

                                if (hourOfDay > 11) {
                                    hourOfDay -= 12;
                                    // If the hour is greater than or equal to 12
                                    // Then the current AM PM status is PM
                                    status = "PM";
                                }

                                // Initialize a new variable to hold 12 hour format hour value
                                int hour_of_12_hour_format;

                                if (hourOfDay > 11) {

                                    // If the hour is greater than or equal to 12
                                    // Then we subtract 12 from the hour to make it 12 hour format time
                                    hour_of_12_hour_format = hourOfDay - 12;
                                } else {
                                    hour_of_12_hour_format = hourOfDay;
                                }
                                String time = hourOfDay + ":" + var3 + " " + status;

                                timeTextView.setText(time);
                            }
                        },
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        true
                ).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_notification) {
            // Handle the camera action
        } else if (id == R.id.nav_booking) {

        } else if (id == R.id.action_settings) {

        } else if (id == R.id.nav_logout) {

        } else if (id == R.id.nav_term) {

        } else if (id == R.id.nav_rate) {

        } else if (id == R.id.nav_about) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        Log.e(TAG, "onMapReady");

        Firebase firebase = new Firebase("https://sa-11-ce9b8.firebaseio.com/SellerProfile");
        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(TAG, "onDataChange");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    SellerProfile sellerProfile = snapshot.getValue(SellerProfile.class);

                    geolat = Double.parseDouble(sellerProfile.getGeoLat());
                    geolong = Double.parseDouble(sellerProfile.getGeoLong());

                    Log.e("Lat", sellerProfile.getGeoLat());
                    Log.e("Long", sellerProfile.getGeoLong());

                    LatLng listOfGeo = new LatLng(geolat, geolong);
                    MarkerOptions options = new MarkerOptions()
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                            .title(sellerProfile.getStoreName())
                            .position(listOfGeo);
                    mGoogleMap = googleMap;
                    mGoogleMap.addMarker(options);
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(listOfGeo, 13));
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
