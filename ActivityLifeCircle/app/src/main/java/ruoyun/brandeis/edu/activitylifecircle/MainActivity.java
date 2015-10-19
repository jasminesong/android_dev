package ruoyun.brandeis.edu.activitylifecircle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    String tag = "Events";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onStart(){
        super.onStart();
        Log.d(tag, "in onStart()");
    }

    public void onRestart(){
        super.onRestart();
        Log.d(tag, "in onRestart()");
    }

    public void onResume(){
        super.onResume();
        Log.d(tag, "in onResume()");
    }

    public void onPause(){
        super.onPause();
        Log.d(tag, "in onPause()");
    }

    public void onStop(){
        super.onStop();
        Log.d(tag, "in onStop()");
    }

    public void onDestroy(){
        super.onStart();
        Log.d(tag, "in onDestroy()");
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
}
