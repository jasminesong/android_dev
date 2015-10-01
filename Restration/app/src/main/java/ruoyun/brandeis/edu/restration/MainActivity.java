package ruoyun.brandeis.edu.restration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button btn = (Button)findViewById(R.id.bt_register);
        final EditText editText1 = (EditText) findViewById(R.id.etFirstName);
        final EditText editText2 = (EditText) findViewById(R.id.etLastName);
        final EditText editText3 = (EditText) findViewById(R.id.etEmail);



        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent my_intent = new Intent(MainActivity.this, AfterRegisterActivity.class);
                my_intent.putExtra("First_Name",editText1.getText().toString());
                my_intent.putExtra("Last_Name",editText2.getText().toString());
                my_intent.putExtra("Email",editText3.getText().toString());

                startActivity(my_intent);
            }

        });
    }

//    public void onStart(){
//        super.onStart();
//        Log.d("mainactivity","onStart");
//    }


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
