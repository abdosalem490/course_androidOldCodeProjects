package com.abdosalm.hikerswatch;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                try {
                    updateLocation(location);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        if (ContextCompat.checkSelfPermission(this , Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this , new String[]{Manifest.permission.ACCESS_FINE_LOCATION} , 1);
        }else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0,locationListener);
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastKnownLocation != null){
                try {
                    updateLocation(lastKnownLocation);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startListening();
        }
    }
    private void updateLocation(Location location) throws IOException {
        TextView latTextView = findViewById(R.id.latTextView);
        TextView lonTextView = findViewById(R.id.longTextView);
        TextView accTextView = findViewById(R.id.accTextView);
        TextView altTextView= findViewById(R.id.altTextView);
        TextView addressTextView = findViewById(R.id.addressTextView);

        latTextView.setText(String.format("Latitude : %s", Double.toString(location.getLatitude())));
        lonTextView.setText(String.format("Longitude : %s", Double.toString(location.getLongitude())));
        accTextView.setText(String.format("Accuracy : %s", Double.toString(location.getAccuracy())));
        altTextView.setText(String.format("altitude : %s", Double.toString(location.getAltitude())));
        String address = "Couldn't find address :(";
        Geocoder geocoder = new Geocoder(this , Locale.getDefault());
        List<Address> addressList = geocoder.getFromLocation(location.getLatitude() , location.getLongitude() , 1);
        if (addressList != null && addressList.size() > 0){
            address = "Address : \n";
            if (addressList.get(0).getThoroughfare() != null){
                address += addressList.get(0).getThoroughfare() +"\n";
            }
            if (addressList.get(0).getLocality() != null){
                address += addressList.get(0).getLocality() +" ";
            }
            if (addressList.get(0).getPostalCode() != null){
                address += addressList.get(0).getPostalCode() +" ";
            }
            if (addressList.get(0).getAdminArea() != null){
                address += addressList.get(0).getAdminArea();
            }
        }
        addressTextView.setText(address);

    }
    private void startListening(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }
}