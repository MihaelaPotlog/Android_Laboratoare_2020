package com.example.proiecturlchangecheck;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import Service.SiteUpdaterTask;



import Service.SiteAdapter;
import Service.SiteAdderTask;
import Service.SiteModel;

import io.realm.Realm;
import io.realm.RealmResults;

import static androidx.core.os.LocaleListCompat.create;


public class MainActivity extends AppCompatActivity {
    private Realm realm;
    LayoutInflater LayoutSearcher ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LayoutSearcher = LayoutInflater.from(this);
        new SiteUpdaterTask(this).execute();

        ListView listView = (ListView) findViewById(R.id.task_list);
        final Realm realm = Realm.getDefaultInstance();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final SiteModel site = (SiteModel) adapterView.getAdapter().getItem(i);
                LinearLayout layout = getLinearLayout(MainActivity.this, site);

                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle(site.getName())
                        .setView(layout)
                        .setPositiveButton("Open", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                                browserIntent.setData(Uri.parse(site.getUrl()));
                                startActivity(browserIntent);
                            }
                        })
                        .create();
                dialog.show();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View alertDialogView = LayoutSearcher.inflate(R.layout.add_site_alert_dialog, null);
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Add  Site")
                        .setView(alertDialogView)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String name = String.valueOf(((TextInputEditText)alertDialogView.findViewById(R.id.site_name_id)).getText());
                                String url = String.valueOf(((TextInputEditText)alertDialogView.findViewById(R.id.site_url_id)).getText());

                                new SiteAdderTask().execute( name, url);
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
//
            }});

    }


//    public void changeTaskDone(final String taskId) {
//        realm.executeTransactionAsync(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                SiteModel task = realm.where(SiteModel.class).equalTo("id", taskId).findFirst();
//                task.setDone(!task.isDone());
//            }
//        });
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

    public LinearLayout getLinearLayout(Context context, SiteModel site){
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        final EditText urlEditText = new EditText(context);
        urlEditText.setText(site.getUrl());

        final EditText nameEditText = new EditText(context);
        nameEditText.setText(site.getName());

        final EditText lastModifiedDateEditText = new EditText(context);
        lastModifiedDateEditText.setText(site.getLastModifiedDate().toString());

        linearLayout.addView(nameEditText);
        linearLayout.addView(urlEditText);
        linearLayout.addView(lastModifiedDateEditText);

        return linearLayout;
    }


}
