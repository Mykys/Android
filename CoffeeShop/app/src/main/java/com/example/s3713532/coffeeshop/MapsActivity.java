package com.example.s3713532.coffeeshop;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double latitude;
    private double longitude;

    private CameraZoomView screen;
    private Marker currMarker;
    private Circle currCircle;
    private int radius;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Start position
        latitude = 10.729339;
        longitude = 106.694286;
        currMarker = null;
        currCircle = null;
        radius = 10;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Get the popular user buttons
        mMap.getUiSettings().setZoomControlsEnabled(true);

        mMap.setInfoWindowAdapter(new CustomInfoWindow(MapsActivity.this));

        // Starting shop when user open the app
        LatLng RMITVN = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions()
                .position(RMITVN)
                .title("RMIT Vietnam")
                .snippet("My University")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.kitty)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(RMITVN));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(RMITVN, 12f));

        // Get initial boundary values of the screen
        screen = new CameraZoomView(mMap);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (currMarker != null) {
                   currMarker.remove();
                }
                latitude = latLng.latitude;
                longitude = latLng.longitude;
                currMarker = mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.down_arrow)));
            }
        });

        // This method is working, but somehow the camera is very sensitive.
//        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
//            @Override
//            public void onCameraMove() {
//                screen.update();
//                if (screen.isCameraViewChange()) {
//                    //Toast.makeText(MapsActivity.this, "Camera working", Toast.LENGTH_SHORT).show();
//                    new GetNearbyShops(mMap, latitude, longitude, 10).execute();
//                }
//            }
//        });

        // BUTTONS

        Button btnMilkTea = findViewById(R.id.btnMilkTea);
        btnMilkTea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.clear();
                new GetNearbyShops(mMap, latitude, longitude, radius, 1, "").execute();

                LatLng currPos = new LatLng(latitude, longitude);
                currCircle = mMap.addCircle(new CircleOptions()
                        .center(currPos)
                        .radius(radius*1000)
                        .fillColor(0x556b96db).strokeColor(Color.TRANSPARENT));
            }
        });

        final Button priceBtn = findViewById(R.id.priceBtn);
        priceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopUpMenu(priceBtn, MapsActivity.this, R.menu.popup_menu, 2);
            }
        });

        final Button reviewBtn = findViewById(R.id.reviewBtn);
        reviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopUpMenu(reviewBtn, MapsActivity.this, R.menu.review_menu, 3);
            }
        });

        final Button distanceBtn = findViewById(R.id.distanceBtn);
        distanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopUpMenu(distanceBtn, MapsActivity.this, R.menu.popup_distance, 4);
            }
        });


        Button addShopBtn = findViewById(R.id.addShopBtn);
        addShopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
                LinearLayout layout = new LinearLayout(MapsActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText nameBox = new EditText(MapsActivity.this);
                nameBox.setHint("Name");
                layout.addView(nameBox);

                final EditText priceBox = new EditText(MapsActivity.this);
                priceBox.setHint("Price");
                priceBox.setInputType(InputType.TYPE_CLASS_NUMBER);
                layout.addView(priceBox);

                final EditText impressionBox = new EditText(MapsActivity.this);
                impressionBox.setHint("Impression");
                layout.addView(impressionBox);

                final EditText addressBox = new EditText(MapsActivity.this);
                addressBox.setHint("Address");
                layout.addView(addressBox);

                final EditText styleBox = new EditText(MapsActivity.this);
                styleBox.setHint("Style");
                layout.addView(styleBox);

                final EditText photoBox1 = new EditText(MapsActivity.this);
                photoBox1.setHint("Photo");
                layout.addView(photoBox1);

                final EditText photoBox2 = new EditText(MapsActivity.this);
                photoBox2.setHint("Photo");
                layout.addView(photoBox2);


                builder.setView(layout);
                builder.setTitle("Add details about shop")
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Map<String, String> postData = new LinkedHashMap<>();

                                try {
                                    postData.put("name", nameBox.getText().toString());
                                    postData.put("price", priceBox.getText().toString());
                                    postData.put("impression", impressionBox.getText().toString());
                                    postData.put("address", addressBox.getText().toString());
                                    postData.put("lat", String.format("%.6f", latitude));
                                    postData.put("lon", String.format("%.6f", longitude));
                                    postData.put("style", styleBox.getText().toString());
                                    postData.put("photo1", photoBox1.getText().toString());
                                    postData.put("photo2", photoBox2.getText().toString());

                                    SendShopDetails details = new SendShopDetails(mMap);
                                    details.execute(details.wwwEncodeMap(postData));

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }).create().show();
            }
        });
    }

    private void createPopUpMenu (Button button, Context context, int resMenu, final int state) {

        // Creating the instance of PopupMenu
        android.widget.PopupMenu popup = new android.widget.PopupMenu(context, button);

        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(resMenu, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new android.widget.PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                mMap.clear();

                if (state == 4) {
                    radius = Integer.parseInt(menuItem.getTitle().toString());
                }

                new GetNearbyShops(mMap, latitude, longitude, radius, state, menuItem.getTitle().toString()).execute();

                LatLng currPos = new LatLng(latitude, longitude);
                currCircle = mMap.addCircle(new CircleOptions()
                        .center(currPos)
                        .radius(radius*1000)
                        .fillColor(0x556b96db).strokeColor(Color.TRANSPARENT));
                return true;
            }
        });
        popup.show();
    }
}
