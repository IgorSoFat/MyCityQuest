package asus.mycityquest;

import android.Manifest;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager lm;
    private double latitude;
    private double longitude;
    private String adresse;
    private String nom;

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
        JSONObject json = new JSONObject();
        Geocoder geocoder = new Geocoder(this);
        if (ContextCompat.checkSelfPermission(
                this.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            askGPSPersmissions();
        }
        map.setMyLocationEnabled(true);

        // GET LIEUX SUR LA MAP
        try {
            attemptGetAllAdress();

            String adresse = null;

            List<Address> address = geocoder.getFromLocationName("132 rue de Lille", 1);
            latitude = address.get(0).getLatitude();
            longitude = address.get(0).getLongitude();
            LatLng location = new LatLng(latitude, longitude);
            map.addMarker(new MarkerOptions().position(location).title(nom));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // GET EVENNEMENTS SUR LA MAP
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

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptGetAllAdress() throws Exception {
        new MapsActivity.RetrieveFeedTask().execute("");

    }

    class RetrieveFeedTask extends AsyncTask<String, Void, String> {
        JSONArray resultat;

        protected String doInBackground(String... urls) {
            try {
                resultat = attemptGetAllAdress();
                for (int i = 0; i < resultat.length(); i++) {
                    JSONObject json =resultat.getJSONObject(i);
                    String nom = json.getString("nom");
                    String adresse = json.getString("adresse");
                    String ville = json.getString("ville");

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "done";
        }

        protected void onPostExecute(String feed) {
            // TODO: check this.exception
            // TODO: do something with the feed
        }

        public JSONArray attemptGetAllAdress() throws Exception {
            HttpRequest httpRequest = new HttpRequest();
            JSONArray res = HttpRequest.getAllAdress(); // COMMENT RENVOYER LE JSON
            return res;
        }

        public JSONArray getResultat() {
            return this.resultat;
        }
    }

}
