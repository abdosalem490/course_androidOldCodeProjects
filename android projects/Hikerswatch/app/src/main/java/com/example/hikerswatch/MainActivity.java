package com.example.hikerswatch;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull  String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            startListening();
        }
    }

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                updateLocationInfo(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {

            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {

            }
        };
        if (Build.VERSION.SDK_INT < 23) {
            startListening();
        }else{
            if (ContextCompat.checkSelfPermission(this , Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this , new String[]{Manifest.permission.ACCESS_FINE_LOCATION} ,1);
            }else{
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null)
                {
                    updateLocationInfo(location);
                }

            }
        }
    }
    public void updateLocationInfo(Location location)
    {
        Log.d(TAG, "updateLocationInfo: "+location.toString());
        TextView latTextView = findViewById(R.id.latTextView);
        TextView lonTextView = findViewById(R.id.lonTextView);
        TextView altTextView = findViewById(R.id.altTextView);
        TextView accTextView = findViewById(R.id.accTextView);
        latTextView.setText("Latitude : "+location.getLatitude());
        lonTextView.setText("Longitude : "+location.getLongitude());
        altTextView.setText("Altitude : "+location.getAltitude());
        accTextView.setText("Accuracy : "+location.getAccuracy());
        Geocoder geocoder = new Geocoder(this , Locale.getDefault());
        try {
            String address = "Couldn't find address";
            List<Address> listAddresses = geocoder.getFromLocation(location.getLatitude() , location.getLongitude() , 1);
            if (listAddresses != null && listAddresses.size() > 0)
            {
                Log.d(TAG, "updateLocationInfo: "+listAddresses.get(0).toString());
                address = "";
                if (listAddresses.get(0).getSubThoroughfare() != null)
                {
                    address += listAddresses.get(0).getSubThoroughfare()+" ";
                }
                if (listAddresses.get(0).getThoroughfare() != null)
                {
                    address += listAddresses.get(0).getThoroughfare()+"\n";
                }
                if (listAddresses.get(0).getLocality() != null)
                {
                    address += listAddresses.get(0).getLocality()+"\n";
                }
                if (listAddresses.get(0).getPostalCode() != null)
                {
                    address += listAddresses.get(0).getPostalCode()+"\n";
                }
                if (listAddresses.get(0).getCountryName() != null)
                {
                    address += listAddresses.get(0).getCountryName()+"\n";
                }
                TextView addressTextView = findViewById(R.id.addressTextView);
                addressTextView.setText(address);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void startListening()
    {
        if (ContextCompat.checkSelfPermission(this , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        }
    }
}