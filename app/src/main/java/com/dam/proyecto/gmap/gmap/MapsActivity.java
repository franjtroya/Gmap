package com.dam.proyecto.gmap.gmap;


/**
 TODO: 1º Permisos -> y gps activated
 TODO: 2º Start service and he saves localizations
 TODO: 3º poder ver -> Polyline for GLocation history on db4o
 */

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<Localizacion> locs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        locs = new ArrayList<>();
        Intent iquery = getIntent();
        locs = iquery.getParcelableArrayListExtra("locations");
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

        // Add a marker in Sydney and move the camera
//        LatLng granada = new LatLng(-37.1608, -3.5911);
//        mMap.addMarker(new MarkerOptions().position(granada).title("Marker in Granada"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(granada));

        PolylineOptions polylineOptions = new PolylineOptions();
        LatLng alatlng;
        for (Localizacion l :locs) {
            alatlng = new LatLng(l.getLocalizacion().getLatitude(), l.getLocalizacion().getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLng(alatlng));
            mMap.moveCamera(CameraUpdateFactory.zoomTo(17));
            polylineOptions.add(alatlng);
        }
        Polyline polyLinea = googleMap.addPolyline(polylineOptions);
    }
}
