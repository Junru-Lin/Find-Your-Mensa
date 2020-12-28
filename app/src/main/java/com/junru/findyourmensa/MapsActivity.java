 package com.junru.findyourmensa;

 import android.content.Intent;
 import android.os.Bundle;
 import android.os.StrictMode;
 import android.util.Log;
 import android.view.MenuItem;
 import android.view.View;
 import android.widget.ImageButton;
 import android.widget.ImageView;
 import android.widget.TextView;

 import androidx.annotation.NonNull;
 import androidx.fragment.app.FragmentActivity;

 import com.google.android.gms.maps.CameraUpdateFactory;
 import com.google.android.gms.maps.GoogleMap;
 import com.google.android.gms.maps.OnMapReadyCallback;
 import com.google.android.gms.maps.SupportMapFragment;
 import com.google.android.gms.maps.model.BitmapDescriptorFactory;
 import com.google.android.gms.maps.model.LatLng;
 import com.google.android.gms.maps.model.Marker;
 import com.google.android.gms.maps.model.MarkerOptions;
 import com.google.android.material.bottomnavigation.BottomNavigationView;

 import java.util.HashMap;
 import java.util.*;

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

        // Add a marker in Dresden and move the camera
        LatLng dresden = new LatLng(51.050407, 13.737262);
        mMap.addMarker(new MarkerOptions().position(dresden).title("Marker in Dresden"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(dresden, 14));

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);//to solve NetworkOnMainThreadException

        Parser parser = new Parser("https://openmensa.org/api/v2/canteens"); //create Parser
        Data data = new Data(parser, System.out); //create Data
        data.initialize(); // initialize Data

        Set<Canteen> MensaInDresden = data.getCanteenByCity("Dresden"); //got a certain canteen
        for (Canteen canteen: MensaInDresden) {
            LatLng canteenLatLng = new LatLng(canteen.getLatitude(),canteen.getLongitude());
            String canteenName = canteen.getName();
            String canteenAddress = canteen.getAddress();
            mMap.addMarker(new MarkerOptions()
                    .position(canteenLatLng)
                    .title(canteenName)
                    .snippet(canteenAddress));
            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                // Use default InfoWindow frame
                @Override
                public View getInfoWindow(Marker arg0) {
                    return null;
                }

                // Defines the contents of the InfoWindow
                @Override
                public View getInfoContents(Marker arg0) {

                    // Getting view from the layout file infowindow_layout.xml
                    View v = getLayoutInflater().inflate(R.layout.infowindow_layout, null);

                    LatLng latLng = arg0.getPosition();

                    ImageView im = (ImageView) v.findViewById(R.id.imageView1);
                    TextView tv1 = (TextView) v.findViewById(R.id.textView1);
                    TextView tv2 = (TextView) v.findViewById(R.id.textView2);
                    String title = arg0.getTitle();
                    String informations = arg0.getSnippet();

                    tv1.setText(title);
                    tv2.setText(informations);
                    im.setImageResource(R.drawable.logo_mensa);

                    return v;

                }
            });
            }

    }




}