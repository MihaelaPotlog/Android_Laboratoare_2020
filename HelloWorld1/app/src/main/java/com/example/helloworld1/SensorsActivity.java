package com.example.helloworld1;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;


public class SensorsActivity extends Activity implements SensorEventListener {
    private  SensorManager sensorManager;
    private List<Sensor> sensors;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList;
    private Hashtable<String,Integer> sensorsIndexes;

    int ID_FOR_PERMISSION = 44;
    FusedLocationProviderClient FusedLocationClient;
    final SensorsActivity thisActivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);


        FusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        arrayList = new ArrayList<>();
        sensorsIndexes = new Hashtable<>();

        arrayList.add("LATITUDE");
        sensorsIndexes.put("LATITUDE", 0);
        arrayList.add("LONGITUDE");
        sensorsIndexes.put("LONGITUDE", 1);


        sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        for(Sensor sensor: sensorManager.getSensorList(Sensor.TYPE_ALL)){
            arrayList.add(sensor.getName());
            sensorsIndexes.put(sensor.getName(), arrayList.size()-1);
        }



        ListView list =  findViewById(R.id.sensors_info);
        adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, arrayList);
        list.setAdapter(adapter);

        getLastGPSLocation();
    }
    
    protected void onResume() {
        super.onResume();
        for( Sensor sensor: sensors)
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
    public void onSensorChanged(SensorEvent event) {
        Integer index = sensorsIndexes.get(event.sensor.getName());
        String value = event.sensor.getName();

        if(index != null && arrayList.get(index).equals(value)){
            for( float val : event.values)
                value = value + "  " +val ;
            arrayList.set(index, value);

            adapter.notifyDataSetChanged();
        }
    }

    

    private boolean verifyPermissions(){

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            return true;
        }

        return false;

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == ID_FOR_PERMISSION) {
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ){
                getLastGPSLocation();
            }
        }
    }

    private boolean isLocationEnabled(){
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return   locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    @SuppressLint("MissingPermission")
    private void getLastGPSLocation(){
        final SensorsActivity act = this;
        if (verifyPermissions()) {
            if (isLocationEnabled()) {
                FusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
//                                    Toast.makeText(act,"hello", Toast.LENGTH_LONG).show();
                                    requestNewLocationData();
                                } else
                                    {
                                    String coordinates = location.getLatitude()+"  "+ location.getLongitude();
                                    Toast.makeText(act,coordinates, Toast.LENGTH_LONG).show();
                                    arrayList.set(0, "LATITUDE :" + location.getLatitude());
                                    arrayList.set(1, "LONGITUDE :" + location.getLongitude());
                                    adapter.notifyDataSetChanged();
                                    }
                            }
                        }
                );
            }
            else {
                Toast.makeText(this, "GPS IS NOT ENABLED", Toast.LENGTH_LONG).show();
                arrayList.set(0, "LATITUDE : GPS IS NOT ENABLED");
                arrayList.set(1, "LONGITUDE : GPS IS NOT ENABLED");
                adapter.notifyDataSetChanged();
            }
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, ID_FOR_PERMISSION);
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData(){

        LocationRequest locationReq = new LocationRequest();
        locationReq.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationReq.setInterval(0);
        locationReq.setFastestInterval(0);
        locationReq.setNumUpdates(1);


        FusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        FusedLocationClient.requestLocationUpdates(locationReq, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location location = locationResult.getLastLocation();
            String coordinates = location.getLatitude()+""+ location.getLongitude();
            arrayList.set(0, "LATITUDE :" + location.getLatitude());
            arrayList.set(1, "LONGITUDE :" + location.getLongitude());
            adapter.notifyDataSetChanged();
            Toast.makeText(thisActivity ,"iii", Toast.LENGTH_LONG).show();
        }
    };
}
