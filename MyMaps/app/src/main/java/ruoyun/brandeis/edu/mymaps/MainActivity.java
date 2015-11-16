package ruoyun.brandeis.edu.mymaps;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.ConnectionRequest;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener

{
    private static final int ERROR_DIALOG_REQUEST = 9001;
    GoogleMap mMap;
    private static final double
            HOME_LAT=30.497089,
                 HOME_LNG=114.364183;


    private GoogleApiClient mLocationClient;//previous class named LocationApi has been solidated
    // into GoogleApiClient class

    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);


        if (serviceOk()) {
            setContentView(R.layout.activity_map);


            final EditText et = (EditText) findViewById(R.id.editText1);
            et.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //EditText yourEditText= (EditText) findViewById(R.id.editText1);
                    //yourEditText.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
                }
            });


            //showKeyboard();
            if (initMap()) {
                //Toast.makeText(this,"Ready to map!",Toast.LENGTH_LONG).show();
                //mMap.setMyLocationEnabled(true);
                gotolocation(HOME_LAT, HOME_LNG, 15);

                mLocationClient = new GoogleApiClient.Builder(this)
                        .addApi(LocationServices.API)
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this)
                        .build();      //create client object

                mLocationClient.connect();
                //showCurrentLocation();
                //getCurrentLocation();



            } else {
                Toast.makeText(this, "Map is not connected!", Toast.LENGTH_LONG).show();

            }

        } else {
            setContentView(R.layout.activity_main);
        }


        Button btn2 = (Button) findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(marker!=null){
                    LatLng latLng = marker.getPosition();
                    Intent my_intent = new Intent(MainActivity.this,FindVehicle.class);
                    my_intent.putExtra("marker_latitude",latLng.latitude);
                    my_intent.putExtra("marker_longtitude",latLng.longitude);
                    startActivity(my_intent);

                }else{
                    Toast.makeText(MainActivity.this,"Please long touch the map to place your car",
                            Toast.LENGTH_LONG).show();
                }


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //Add menu handling code
        switch (id) {
            case R.id.mapTypeNone:
                mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                break;
            case R.id.mapTypeNormal:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.mapTypeSatellite:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.mapTypeTerrain:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case R.id.mapTypeHybrid:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    public boolean serviceOk() {

        int isAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (GooglePlayServicesUtil.isUserRecoverableError(isAvailable)) {
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(isAvailable, this, ERROR_DIALOG_REQUEST);
            dialog.show();

        } else {
            Toast.makeText(this, "Cannot connect to mapping service", Toast.LENGTH_LONG).show();
        }


        return false;

    }

    private boolean initMap() {
        if (mMap == null) {
            //java equivalent to put a fragment tag in xml
            SupportMapFragment mapFragment =
                    (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mMap = mapFragment.getMap();

            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);


            if (mMap != null) {
                mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(LatLng latLng) {
                        Geocoder gc = new Geocoder(MainActivity.this);
                        List<Address> list = null;

                        try {
                            list = gc.getFromLocation(latLng.latitude, latLng.longitude, 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                            return;
                        }

                        Address add = list.get(0);

                        MainActivity.this.addMarker(add, latLng.latitude, latLng.longitude);
                    }
                });

//                mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
//                    @Override
//                    public void onMarkerDragStart(Marker marker) {
//
//                    }
//
//                    @Override
//                    public void onMarkerDrag(Marker marker) {
//
//                    }
//
//                    @Override
//                    public void onMarkerDragEnd(Marker marker) {
//                        Geocoder gc = new Geocoder(MainActivity.this);
//                        List<Address> list = null;
//                        LatLng latLng = marker.getPosition();
//
//                        try {
//                            list=gc.getFromLocation(latLng.latitude,latLng.longitude,1);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                            return;
//                        }
//
//                        Address add = list.get(0);
//
//                    }
//                });
            }
        }

        return (mMap != null);
    }

    private void addMarker(Address add, double latitude, double longitude) {
        MarkerOptions options = new MarkerOptions()

                .position(new LatLng(latitude, longitude))
                .draggable(true)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_top_car_picture_color));
        if (marker != null) {
            marker.remove();
        }
        marker = mMap.addMarker(options);

    }

    private void gotolocation(double lat, double lng, float zoom) {
        LatLng latLng = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, zoom);
        mMap.moveCamera(update);
    }


    public void showCurrentLocation(MenuItem item) {
        Location currentLocation = LocationServices.FusedLocationApi
                .getLastLocation(mLocationClient);
        if (currentLocation == null) {
            Toast.makeText(this, "Couldn't connect", Toast.LENGTH_SHORT).show();
        } else {
            LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, 18);
            mMap.animateCamera(update);

            //add marker

            if (marker != null) {
                marker.remove();
            }
            MarkerOptions options = new MarkerOptions()

                    .position(latLng)
                    .draggable(true)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_top_car_picture_color));
            marker = mMap.addMarker(options);
        }


    }

    @Override
    public void onConnected(Bundle bundle) {
        Toast.makeText(this, "Ready to map!", Toast.LENGTH_LONG).show();//only see this msg if only connected to Google location services
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    //@Override
//    protected void onResume() {
//        super.onResume();
//        mLocationClient.connect();
//    }


    public void geoLocate(View v) throws IOException {
        hideSoftKeyboard(v);

        Toast.makeText(this, "geolocate!!!", Toast.LENGTH_LONG).show();
        //showKeyboard();

        TextView tv = (TextView) findViewById(R.id.editText1);
        String searchString = tv.getText().toString();

        Geocoder gc = new Geocoder(this);
        List<Address> list = gc.getFromLocationName(searchString, 1);

        if (list.size() > 0) {
            Address add = list.get(0);
            String locality = add.getLocality();
            Toast.makeText(this, "Found:" + locality, Toast.LENGTH_SHORT).show();

            double lat = add.getLatitude();
            double lng = add.getLongitude();
            gotolocation(lat, lng, 15);

            //add marker

            if (marker != null) {
                marker.remove();
            }
            MarkerOptions options = new MarkerOptions()
                    .title(locality)
                    .position(new LatLng(lat, lng))
                    .draggable(true)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_top_car_picture_color));
            marker = mMap.addMarker(options);

        }
    }

    private void hideSoftKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public void showKeyboard() {
        //Toast.makeText(this,"hello",Toast.LENGTH_SHORT).show();

        EditText yourEditText = (EditText) findViewById(R.id.editText1);
        //yourEditText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(yourEditText, InputMethodManager.SHOW_IMPLICIT);
    }


    public void getCurrentLocation() {
        Location currentLocation = LocationServices.FusedLocationApi
                .getLastLocation(mLocationClient);
        if (currentLocation == null) {
            Toast.makeText(this, "Couldn't connect", Toast.LENGTH_SHORT).show();
        } else {
            LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, 18);
            mMap.animateCamera(update);
        }


    }




}
