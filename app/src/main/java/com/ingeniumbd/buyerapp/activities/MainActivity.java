package com.ingeniumbd.buyerapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.ingeniumbd.buyerapp.R;
import com.ingeniumbd.buyerapp.infrastructure.Constant;
import com.ingeniumbd.buyerapp.model.BuyerProfile;
import com.ingeniumbd.buyerapp.model.Products;
import com.ingeniumbd.buyerapp.model.ProductsFirestore;
import com.ingeniumbd.buyerapp.model.SellerProfile;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        OnMapReadyCallback,
        MyDialogFragment.OnCitySelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private GoogleMap mGoogleMap;
    private Double geolat, geolong;
    Button button;
    LinearLayout serviceType, dateSet, timeSet;
    private View navHeader;
    Integer cnt = 0;
    ListView listView;
    ArrayList<String> service_type = new ArrayList<>();
    TextView dateTextView, timeTextView;
    Integer yearG, monthOfYear, dayOfMonthG;
    RelativeLayout searchLayoutR;
    DrawerLayout drawer;
    Firebase firebaseCategory, firebaseBuyerProfile;
    ImageView placeTaker;
    String startDate, endDate, category, startTime, buyerLocation, numberOfPeople, endTime,
            regularPrice, offerPrice;
    EditText buyerNumberOfPeople, buyerStartPrice, buyerEndPrice;
    String buyerPeopleQty, buyerPriceStart, buyerPriceEnd;
    FirebaseFirestore db;
    Button buttonEditProfile, search_button_go;
    TextView nav_buyerName, nav_buyerAddress;
    ImageView nav_buyerProfileImage;
    String uKey, url, seller_id, time, date, serviceTypeItem;
    ArrayList<String> seller_id_array;

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

        View header = navigationView.getHeaderView(0);

        serviceType = (LinearLayout) findViewById(R.id.serviceTypeLL);
        dateSet = (LinearLayout) findViewById(R.id.date_set_layout);
        timeSet = (LinearLayout) findViewById(R.id.time_set_layout);
        dateTextView = (TextView) findViewById(R.id.date_picker);
        timeTextView = (TextView) findViewById(R.id.time_picker);
        RelativeLayout searchLayout = (RelativeLayout) findViewById(R.id.search_layout);
        buyerNumberOfPeople = (EditText) findViewById(R.id.buyer_numberOfPeople);
        buyerStartPrice = (EditText) findViewById(R.id.buyerStartPrice);
        buyerEndPrice = (EditText) findViewById(R.id.buyerEndPrice);

        nav_buyerProfileImage = (ImageView) header.findViewById(R.id.nav_imageView);
        nav_buyerName = (TextView) header.findViewById(R.id.nav_buyer_name);
        nav_buyerAddress = (TextView) header.findViewById(R.id.nav_buyerAddress);

        //Button:
        search_button_go = (Button) findViewById(R.id.search_button_go);
        search_button_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                buyerPeopleQty = buyerNumberOfPeople.getText().toString();
                buyerPriceStart = buyerStartPrice.getText().toString();
                buyerPriceEnd = buyerEndPrice.getText().toString();

                final ArrayList<Products> productsList = new ArrayList<Products>();
                db = FirebaseFirestore.getInstance();
                final CollectionReference productsRef = db.collection("Products");

                //need to compare services types:
              /*  productsRef.whereGreaterThanOrEqualTo("offerPrice", "100")
                .whereLessThanOrEqualTo("offerPrice", "300");
        productsRef.whereEqualTo("location", "Gulshan")
                .whereLessThanOrEqualTo("numberOfPeople", "12")
                */
                productsRef.whereGreaterThanOrEqualTo("offerPrice", buyerPriceStart)
                        .whereLessThanOrEqualTo("offerPrice", buyerPriceEnd);
                productsRef.whereEqualTo("location", "Gulshan")
                        .whereLessThanOrEqualTo("numberOfPeople", buyerPeopleQty)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    seller_id_array = new ArrayList<String>();
                                    for (DocumentSnapshot document : task.getResult()) {
                                        String location = document.getString("location");
                                        String numberOfPeople = document.getString("numberOfPeople");
                                        String regularPrice = document.getString("regularPrice");

                                        seller_id = document.getString("id");
                                        seller_id_array.add(seller_id);

                                        Log.e(TAG, "Id: " + seller_id);
                                        Log.e(TAG, "Location: " + location);
                                        Log.e(TAG, "numberOfPeople: " + numberOfPeople);
                                        Log.e(TAG, "regularPrice: " + regularPrice);
                                        Log.e(TAG, "offerPrice: " + document.getString("offerPrice"));

                                        Products products = new Products();
                                        products.setNumberOfPeople(numberOfPeople);
                                        products.setRegularPrice(regularPrice);

                                        productsList.add(products);
                                        //ProductsFirestore productsFirestore = document.toObject(ProductsFirestore.class);
                                        // Log.e(TAG,"Location: "+productsFirestore.getLocation());

                                    }
                                    if (seller_id_array.size() > 0) {

                                        Toast.makeText(MainActivity.this, "id " + seller_id, Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(MainActivity.this, Dashboard.class);
                                        intent.putExtra("sellerIdList", seller_id_array);
                                        startActivity(intent);
                                    }
                                } else {
                                    Log.e(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });


            }
        });


        //Buyer Profile nav header:
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        uKey = preferences.getString("Key", "");
        Log.e("userKey", uKey);

        firebaseBuyerProfile = new Firebase("https://sa-11-ce9b8.firebaseio.com/BuyerProfile");
        firebaseBuyerProfile.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot cld : dataSnapshot.getChildren()) {
                    BuyerProfile buyerProfile = cld.getValue(BuyerProfile.class);
                    if (uKey.equals(buyerProfile.getUserId())) {
                        String name = buyerProfile.getUserName();
                        nav_buyerName.setText(name);
                        String city = buyerProfile.getCity();
                        String address = buyerProfile.getAddress();
                        nav_buyerAddress.setText(city + "," + address);
                        url = buyerProfile.getProfilepic();
                        if (!url.equals(" ")) {
                            Glide.with(MainActivity.this)
                                    .load(url)
                                    .into(nav_buyerProfileImage);

                        }
                    }

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        //Buyer Edit Profile options:
        buttonEditProfile = (Button) header.findViewById(R.id.edit_profile);
        buttonEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, EditProfile.class));
            }
        });

        /*firebaseProducts = new Firebase("https://sa-11-ce9b8.firebaseio.com/Products");
        firebaseProducts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot cld : dataSnapshot.getChildren()) {
                    Products products = cld.getValue(Products.class);
                    startDate = products.getStartDate();
                    endDate = products.getEndDate();
                    buyerLocation = products.getLocation();
                    numberOfPeople = products.getNumberOfPeople();
                    regularPrice = products.getRegularPrice();
                    offerPrice = products.getOfferPrice();
                    Log.e("Firebase Regular Price:", regularPrice);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
*/
        firebaseCategory = new Firebase("https://sa-11-ce9b8.firebaseio.com/SellerProfile");
        firebaseCategory.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot cld : dataSnapshot.getChildren()) {
                    SellerProfile sp = cld.getValue(SellerProfile.class);
                    String category_item = sp.getCategory();
                    service_type.add(category_item);
                    Log.e(TAG, "Category: " + category_item);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        listView = (ListView) findViewById(R.id.serviceTypeLV);
        ArrayAdapter myAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, service_type);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // String item = service_type.get(i);
                //  Toast.makeText(MainActivity.this, "Item: " + item, Toast.LENGTH_LONG).show();
                serviceTypeItem = service_type.get(i);
            }
        });

        placeTaker = (ImageView) findViewById(R.id.placeTakerImg);

        placeTaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDialogFragment myDialogFragment = new MyDialogFragment();
                myDialogFragment.show(getSupportFragmentManager(), "dogu");
            }
        });

        searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(Gravity.END);
            }
        });


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
                //Toast.makeText(MainActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
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

                                date = dayOfMonthG + "/" + (++monthOfYear) + "/" + yearG;
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
                                time = hourOfDay + ":" + var3 + " " + status;

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
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
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
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))
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

    @Override
    public void onCitySelected(String city) {
        TextView location = (TextView) findViewById(R.id.location);
        location.setText(city);
    }
}
