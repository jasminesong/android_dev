package ruoyun.brandeis.edu.expenselog;

/**
 * Created by reallifejasmine on 11/17/15.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class EntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense_add);

        final Button bt_cancel =(Button)findViewById(R.id.button2);
        final Button bt_save =(Button)findViewById(R.id.button1);
        final EditText editText1 = (EditText) findViewById(R.id.editText);
        final EditText editText2 = (EditText) findViewById(R.id.editText2);


        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent cancel_input_intent = new Intent(EntryActivity.this,MainActivity.class);

                startActivity(cancel_input_intent);
            }
        });

        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent save_input_intent = new Intent(EntryActivity.this,MainActivity.class);
                save_input_intent.putExtra("Heading",editText1.getText().toString());
                save_input_intent.putExtra("Note",editText2.getText().toString());

                startActivity(save_input_intent);



            }
        });
    }




}
