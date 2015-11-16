package ruoyun.brandeis.edu.mymaps;

import android.content.Intent;
import android.location.Address;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
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

public class FindVehicle extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {


    GoogleMap mMap;
    Marker client_marker;
    Marker vehicle_marker;
    private GoogleApiClient mLocationClient;
    LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_vehicle);

        Intent my_intent=getIntent();
        latLng = new LatLng(my_intent.getDoubleExtra("marker_latitude", 0.0),
                my_intent.getDoubleExtra("marker_longtitude", 0.0));


        if (mMap == null) {
            //java equivalent to put a fragment tag in xml
            SupportMapFragment mapFragment =
                    (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mMap = mapFragment.getMap();

            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

            mLocationClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();      //create client object

            mLocationClient.connect();


            //gotolocation(cameraLatLng,18);
            //addClientMarker(latLngClient);

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_find_vehicle, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void gotolocation(LatLng latLng, float zoom) {
        //LatLng latLng = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, zoom);
        mMap.moveCamera(update);
    }

    @Override
  public void onConnected(Bundle bundle) {
       //Toast.makeText(this, "Ready to find vehicle!", Toast.LENGTH_LONG).show();
        Location currentLocation = LocationServices.FusedLocationApi
                .getLastLocation(mLocationClient);

        LatLng latLngClient = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
       //LatLng latLngClient = new LatLng(0.0,0.0);
        addClientMarker(latLngClient);
        addVehicleMarker(latLng);

        LatLng cameraLatLng = new LatLng(0.5 * (latLng.latitude + latLngClient.latitude),
               0.5 * (latLng.longitude + latLngClient.longitude));
        Toast.makeText(this, "mLocationClient is connected!", Toast.LENGTH_LONG).show();

       //gotolocation(cameraLatLng,18);
       gotolocation(cameraLatLng, 16);
        Toast.makeText(this, "mLocationClient is connected!"+latLngClient+"----"+cameraLatLng, Toast.LENGTH_LONG).show();

        //addVehicleMarker(latLng);
   }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private void addVehicleMarker(LatLng latLng) {
        MarkerOptions options = new MarkerOptions()

                .position(latLng)
                .draggable(true)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));
        if (vehicle_marker != null) {
            vehicle_marker.remove();
        }
        vehicle_marker = mMap.addMarker(options);

    }

    private void addClientMarker(LatLng latLng){
        MarkerOptions options = new MarkerOptions()

                .position(latLng)
                .draggable(true)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_client));
        if (client_marker != null) {
            client_marker.remove();
        }
        client_marker = mMap.addMarker(options);
    }
}
