package com.example.helloworld1;
import android.os.Bundle;
import android.content.Intent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Toast;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import java.util.Hashtable;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    int PERMISSION_ID = 44;
    FusedLocationProviderClient mFusedLocationClient;
    TextView latTextView, lonTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        final TextView textView =  findViewById(R.id.text_view_id);
        final Hashtable<String,String> itemsDetails = new Hashtable<String, String>() ;
        itemsDetails.put("Shoes", "These are a pair of red high heels");
        itemsDetails.put("Dress", "Long black dress");
        itemsDetails.put("Book", "Game of Thrones");
        itemsDetails.put("Pen", "A blue pen");
        itemsDetails.put("Laptop", "Asus zenbook");
        itemsDetails.put("Stickers", "12 stickers with Snow White");




        final ListView list = findViewById(R.id.list);
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList("Shoes","Dress", "Book", "Pen","Laptop","Stickers"));

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arrayList);
        list.setAdapter(arrayAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem=(String) list.getItemAtPosition(position);
                textView.setText(itemsDetails.get(clickedItem));

            }});




        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        SetSettingsButton();
        SetSaveButton();
        SetReadButton();
        SetSensorsButton();

    }
    protected void SetSettingsButton(){
        final MainActivity  mainActivity = this;
        Button button = findViewById(R.id.settings_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(mainActivity, MySettingsActivity.class));
            }
        });
    }
    protected void SetSensorsButton(){
        final MainActivity mainActivity = this;
        Button button = findViewById(R.id.sensors_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(mainActivity, SensorsActivity.class));
            }
        });
    }

    protected void SetReadButton(){

        Button button = findViewById(R.id.read_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    FileInputStream fin = openFileInput("internal_file.txt");
                    int c;
                    String temp="";
                    while( (c = fin.read()) != -1){
                        temp = temp + (char)c;
                    }

                    Toast.makeText(getBaseContext(),temp,Toast.LENGTH_SHORT).show();
                }
                catch(Exception e){ }
            }
        });
    }


    protected void SetSaveButton() {
        Button button = findViewById(R.id.save_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                TextInputEditText input =(TextInputEditText)findViewById(R.id.text_input);
                TextView textView = findViewById(R.id.text_view_id);
                FileOutputStream fos;
                try {
                    fos = openFileOutput("internal_file.txt", MODE_APPEND);
                    fos.write(input.getText().toString().getBytes());
                    String text = input.getText().toString() + " was saved !!";
                    textView.setText(text);
                    fos.close();;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
    @Override
    protected void onResume(){

        super.onResume();
        System.out.println("Resume action");

    }
    @Override
    protected void onPause(){

        super.onPause();
        System.out.println("Pause action");
    }

    @Override
    protected void onStop(){

        super.onStop();
        System.out.println("Stop action");

    }
    @Override
    protected void onStart(){
        super.onStart();
        System.out.println("Start action");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        System.out.println("Destroy action");
    }

    @Override
    public void onSaveInstanceState(Bundle outInstanceState) {
//        savedInstanceState.putString(STATE_USER, mUser);
        // Always call the superclass so it can save the view hierarchy state
        System.out.println("OnsaveInstanceState");
        TextView textView = (TextView)  findViewById(R.id.text_view_id);
        outInstanceState.putString("clicked_item", textView.getText().toString());
        super.onSaveInstanceState(outInstanceState);
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
//        savedInstanceState.putString(STATE_USER, mUser);
        // Always call the superclass so it can save the view hierarchy state
        super.onRestoreInstanceState(savedInstanceState);

        TextView textView = (TextView)  findViewById(R.id.text_view_id);
        textView.setText(savedInstanceState.getString("clicked_item"));

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        getMenuInflater().inflate(R.menu.menu_info, menu);
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
        if(id == R.id.about_us_item){
            Intent intent = new Intent(this, AboutUsActivity.class);


            startActivity(intent);
        }
        else if(id == R.id.share_item){
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Helloo");
            sendIntent.setType("text/plain");

            // Verify that the intent will resolve to an activity
            if (sendIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(sendIntent);
            }
        }
        else if(id == R.id.subscribe){
            DialogFragment newFragment = new SubscribeDialogFragment();
            newFragment.show(getSupportFragmentManager(), "subscribe");

        }


        return super.onOptionsItemSelected(item);
    }
}
