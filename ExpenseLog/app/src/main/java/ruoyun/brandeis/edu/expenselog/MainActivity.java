package ruoyun.brandeis.edu.expenselog;

import android.content.ClipData;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView l1;

    ExpenseTrackerAdapter myAdapter = new ExpenseTrackerAdapter();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        myAdapter.addActivity(this);

        if(getIntent().getStringExtra("Heading")!=null) {
            ExpenseLogEntryData add_entry = new ExpenseLogEntryData(getIntent().getStringExtra("Heading"),
                    getIntent().getStringExtra("Note"));

            myAdapter.addData(add_entry);

        }

        l1=(ListView)findViewById(R.id.ll1);
        l1.setAdapter(myAdapter);



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
        if (id == R.id.single_add) {
            Intent add_entry_intent = new Intent(MainActivity.this,EntryActivity.class);
            startActivity(add_entry_intent);

        }

        return super.onOptionsItemSelected(item);
    }



}
