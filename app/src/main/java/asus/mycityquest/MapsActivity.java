package asus.mycityquest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.nfc.Tag;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
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
    private String TAG = "Position status : ";

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
        mMap = googleMap;
        // LatLng maubeuge = new LatLng(50.2788, 3.9727);
        askGPSPersmissions();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        LocationManager locationManager = (LocationManager) getSystemService(this.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(
                LocationManager.GPS_PROVIDER
        );
        LatLng pos = onLocationChanged(location);
        Log.e(TAG,"OK");
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos,15));

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
        LatLng position = new LatLng(latitude,longitude);
        return position;
    }

}
