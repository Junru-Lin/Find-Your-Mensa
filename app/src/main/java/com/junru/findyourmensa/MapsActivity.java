 package com.junru.findyourmensa;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.junru.findyourmensa.R.drawable.logo_mensa;

 public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    private GoogleMap mMap;
     private ImageButton button;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.Map);
        mapFragment.getMapAsync(this);

        button = findViewById(R.id.help_button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openHelpActivity();
            }
        });

         //Initialize and assign variable
         BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
         //Perform ItemSelectedListener
         bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
             @Override
             public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                 switch (menuItem.getItemId()) {
                     case R.id.page_1:
                         startActivity(new Intent(getApplicationContext(), ListActivity.class));
                         overridePendingTransition(0,0);
                         return true;
                     case R.id.page_2:
                         startActivity(new Intent(getApplicationContext(), FilterActivity.class));
                         overridePendingTransition(0,0);
                         return true;
                     case R.id.page_3:
                         startActivity(new Intent(getApplicationContext(), FavouritesActivity.class));
                         overridePendingTransition(0,0);
                         return true;
                 }
                 return false;
             }
         });
     }

     private void openHelpActivity() {
         Intent intent = new Intent(this, HelpActivity.class);
         startActivity(intent);
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
        LatLng dresden = new LatLng(51.050407, 13.737262);
        mMap.addMarker(new MarkerOptions().position(dresden).title("Marker in Dresden"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(dresden, 14));
    }


}