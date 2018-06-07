package asus.mycityquest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager lm;
    private double latitude;
    private double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        GoogleMap map = googleMap;
        if (ContextCompat.checkSelfPermission(
                this.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            askGPSPersmissions();
        }
        map.setMyLocationEnabled(true);

        /*
        Ajouter un marker selon TOUTES les adresse de la BDD
        LatLng pos = new LatLng(location.getLatitude(),location.getLongitude());
        map.addMarker(new MarkerOptions().position(pos).title("Ma position"));
        */

        //mMap.addMarker(new MarkerOptions().position(maubeuge).title("Maubeuge"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(maubeuge,15));
    }

    protected void askGPSPersmissions() {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        )) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1
            );
        }
    }

    public LatLng onLocationChanged(Location loc) {
        this.latitude = loc.getLatitude();
        this.longitude = loc.getLongitude();
        LatLng position = new LatLng(latitude, longitude);
        return position;
    }

}
