package com.example.helloworld1;
import android.os.Bundle;
import android.preference.PreferenceActivity;

import androidx.appcompat.app.AppCompatActivity;


public class MySettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shared_settings_activity);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings_container, new MySettingsFragment())
                .commit();

    }
}
