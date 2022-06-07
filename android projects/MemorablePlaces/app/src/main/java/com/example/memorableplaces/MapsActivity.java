package com.example.memorableplaces;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.memorableplaces.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    LocationManager mLocationManager;
    LocationListener mLocationListener;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull  String[] permissions, @NonNull  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if ( grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            if (ContextCompat.checkSelfPermission(this , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
                Location lastKnownLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                centerMapOnLocation(lastKnownLocation , "Your location");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLongClickListener(this);
        // Add a marker in Sydney and move the camera
        Intent intent = getIntent();
        if (intent.getIntExtra("placeNumber", -1) == 0) {
            mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            mLocationListener = new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    centerMapOnLocation(location, "your Location");
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
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
            }
            else
            {
                if (ContextCompat.checkSelfPermission(this , Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
                {
                    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
                    Location lastKnownLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    centerMapOnLocation(lastKnownLocation , "Your location");

                }else{
                    ActivityCompat.requestPermissions(this , new String[]{Manifest.permission.ACCESS_FINE_LOCATION} , 1);
                }
            }
        }else{
            Location placelocation = new Location(LocationManager.GPS_PROVIDER);
            placelocation.setLatitude(MainActivity.locations.get(intent.getIntExtra("placeNumber", -1)).latitude);
            placelocation.setLongitude(MainActivity.locations.get(intent.getIntExtra("placeNumber", -1)).longitude);
            centerMapOnLocation(placelocation ,MainActivity.places.get(intent.getIntExtra("placeNumber", -1)));

        }
    }
    private void centerMapOnLocation(Location location , String title)
    {
        LatLng userLocation = new LatLng(location.getLatitude() , location.getLongitude());
        mMap.clear();
        if (title != "Your location")
        {
            mMap.addMarker(new MarkerOptions().position(userLocation).title(title));

        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation , 10));

    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {
        Geocoder geocoder = new Geocoder(getApplicationContext() , Locale.getDefault());
        String address = "";
        try {
            List<Address> listAddresses = geocoder.getFromLocation(latLng.latitude , latLng.longitude , 1);
            if (listAddresses != null && listAddresses.size()>0)
            {
                if (listAddresses.get(0).getThoroughfare() != null)
                {
                    if (listAddresses.get(0).getSubThoroughfare() != null)
                    {
                        address += listAddresses.get(0).getSubThoroughfare()+" ";
                    }
                    address += listAddresses.get(0).getThoroughfare();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (address == "")
        {
            SimpleDateFormat sdf = new SimpleDateFormat("mm:HH yyyy-MM-dd");
            address = sdf.format(new Date());
        }
        mMap.addMarker(new MarkerOptions().position(latLng).title(address));
        MainActivity.places.add(address);
        MainActivity.locations.add(latLng);
        MainActivity.arrayAdapter.notifyDataSetChanged();
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.memorableplaces" ,Context.MODE_PRIVATE);
        try {
            ArrayList<String> latitude = new ArrayList<>();
            ArrayList<String>longitude = new ArrayList<>();
            for (LatLng coordianters : MainActivity.locations)
            {
                latitude.add(Double.toString(coordianters.latitude));
                longitude.add(Double.toString(coordianters.longitude));

            }
            sharedPreferences.edit().putString("places",ObjectSerializer.serialize(MainActivity.places)).apply();
            sharedPreferences.edit().putString("latitudes",ObjectSerializer.serialize(latitude)).apply();
            sharedPreferences.edit().putString("longitudes",ObjectSerializer.serialize(longitude)).apply();

        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "Locations saved", Toast.LENGTH_SHORT).show();

    }
}