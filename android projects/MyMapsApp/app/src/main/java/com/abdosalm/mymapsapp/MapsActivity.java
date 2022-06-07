package com.abdosalm.mymapsapp;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.abdosalm.mymapsapp.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private static final String TAG = "MapsActivity";

    private LatLng mountEverest  = new LatLng(28.001377 , 86.928129);
    private LatLng mountKilimanjaro = new LatLng(-3.075558 , 37.344363);
    private LatLng theAlps = new LatLng(47.368955 , 9.702579);

    // TODO : create markers

    private Marker everestMarker;
    private Marker kilimanjaroMarker;
    private Marker theAlpsMarker;

    private ArrayList<Marker> markerArrayList = new ArrayList<>();

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
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        everestMarker = mMap.addMarker(new MarkerOptions().position(mountEverest).title("Everest").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        kilimanjaroMarker = mMap.addMarker(new MarkerOptions().position(mountKilimanjaro).title("Mt Kilimanjaro").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        theAlpsMarker = mMap.addMarker(new MarkerOptions().position(theAlps).title("The Alps").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));

        markerArrayList.add(everestMarker);
        markerArrayList.add(kilimanjaroMarker);
        markerArrayList.add(theAlpsMarker);



       // mMap.addMarker(new MarkerOptions().position(binga).title("Marker in binga").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(binga));
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(binga , 8));
    }
}