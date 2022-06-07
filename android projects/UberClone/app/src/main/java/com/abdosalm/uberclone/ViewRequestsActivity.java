package com.abdosalm.uberclone;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ViewRequestsActivity extends AppCompatActivity {
    ListView requestListView;
    ArrayList<String> requests;
    ArrayAdapter<String> arrayAdapter;
    LocationManager locationManager;
    LocationListener locationListener;
    ArrayList<Double> requestLatitude = new ArrayList<>();
    ArrayList<Double> requestLongitude = new ArrayList<>();
    ArrayList<String> usernames = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_requests);

        requestListView = findViewById(R.id.requestListView);
        requests = new ArrayList<>();


        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, requests);
        requests.clear();
        requests.add("Getting nearby requests...");
        requestListView.setAdapter(arrayAdapter);
        requestListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (requestLatitude.size() > position && requestLongitude.size() > position &&usernames.size() > position && lastKnownLocation != null){
                    Intent intent = new Intent(getApplicationContext(),DriverLocationActivityActivity.class);
                    intent.putExtra("requestLatitude",requestLatitude.get(position));
                    intent.putExtra("requestLongitude",requestLongitude.get(position));
                    intent.putExtra("driverLatitude",lastKnownLocation.getLatitude());
                    intent.putExtra("driverLongitude",lastKnownLocation.getLongitude());
                    intent.putExtra("username",usernames.get(position));
                    startActivity(intent);
                }
            }}
        });

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                updateListView(location);
                ParseUser.getCurrentUser().put("location",new ParseGeoPoint(location.getLatitude(),location.getLongitude()));
                ParseUser.getCurrentUser().saveInBackground();
            }
        };
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastKnownLocation != null) {
                updateListView(lastKnownLocation);
            }
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull  String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                    Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    updateListView(lastKnownLocation);
                }
            }
        }
    }

    private void updateListView(Location location){
        if (location != null) {
            requests.clear();
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Request");
            ParseGeoPoint geoPointLocation = new ParseGeoPoint(location.getLatitude(), location.getLongitude());
            query.whereNear("location",geoPointLocation);
            query.whereDoesNotExist("driverUsername");
            query.setLimit(10);
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null){
                        requests.clear();
                        requestLongitude.clear();
                        requestLatitude.clear();
                        if (objects.size() > 0){
                            for (ParseObject object : objects){
                                ParseGeoPoint requestLocation = (ParseGeoPoint) object.get("location");
                                if (requestLocation != null) {
                                    Double distance = geoPointLocation.distanceInKilometersTo(requestLocation);
                                    Double distanceOneDP = Double.valueOf(Math.round(distance * 10) / 10);
                                    requests.add(distanceOneDP.toString() + " KM");
                                    requestLatitude.add(requestLocation.getLatitude());
                                    requestLongitude.add(requestLocation.getLongitude());
                                    usernames.add(object.getString("username"));
                                }
                            }
                        }else{
                            requests.add(0,"No active request nearby");
                        }
                        arrayAdapter.notifyDataSetChanged();
                    }
                }
            });

        }
    }
}