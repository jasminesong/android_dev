package ruoyun.brandeis.edu.mymaps;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.ConnectionRequest;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

public class MainActivity extends AppCompatActivity {

    GoogleMap mMap;
    private static final int ERROR_DIALOG_REQUEST = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(serviceOk()){
            setContentView(R.layout.activity_map);

            if(serviceOk()){
                Toast.makeText(this,"Ready to map!",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this,"Map is not connected!",Toast.LENGTH_LONG).show();

            }

        }else{
            setContentView(R.layout.activity_main);
        }
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean serviceOk(){

        int isAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        if(isAvailable== ConnectionResult.SUCCESS){
            return true;
        }else if(GooglePlayServicesUtil.isUserRecoverableError(isAvailable)){
            Dialog dialog =GooglePlayServicesUtil.getErrorDialog(isAvailable,this,ERROR_DIALOG_REQUEST);
            dialog.show();

        }else{
            Toast.makeText(this,"Cannot connect to mapping service",Toast.LENGTH_LONG).show();
        }


        return false;

    }

    private boolean initMap(){
        if(mMap == null){
            //java equivalent to put a fragment tag in xml
            SupportMapFragment mapFragment =
                    (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
            mMap = mapFragment.getMap();
        }

        return (mMap !=null);
    }
}
