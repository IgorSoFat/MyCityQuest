package asus.mycityquest;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

/*
 *
 * Affiche les lieux d'une ville choisie dans IndexAcitivty
 *
 *
 */

public class ShowLieuActivity extends AppCompatActivity implements OnMapReadyCallback {
    protected JSONObject lieu;

    protected TextView nom;
    protected TextView ville;
    protected TextView adresse;
    protected TextView description;

    protected GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_lieu);

        nom         = findViewById(R.id.nom);
        adresse     = findViewById(R.id.adresse);
        ville       = findViewById(R.id.ville);
        description = findViewById(R.id.description);

        Bundle bundle = getIntent().getExtras();

        new RetrieveLieu().execute(String.valueOf(bundle.getInt("id")));

        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;
    }

    class RetrievePosition extends AsyncTask<String, Void, String> {
        protected Address adresse;
        @Override
        public String doInBackground(String ...query) {
            Geocoder geocoder = new Geocoder(ShowLieuActivity.this);

            try {
                String locationName =
                    new StringBuilder(lieu.getString("adresse"))
                        .append(", ")
                        .append(lieu.getString("ville"))
                        .toString();

                List<Address> addresses = geocoder.getFromLocationName(locationName, 1);
                this.adresse = addresses.get(0);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return "done";
        }

        @Override
        public void onPostExecute(String result) {
            if (adresse == null)
                return;

            map.addMarker(new MarkerOptions()
                .position(new LatLng(adresse.getLatitude(), adresse.getLongitude()))
                .title("Marker"));

            map.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    new LatLng(adresse.getLatitude(), adresse.getLongitude()),
                    12.0f
                )
            );
        }
    }

    class RetrieveLieu extends AsyncTask<String, Void, String> {
        @Override
        public String doInBackground(String ...query) {
            String result = "";
            try {
                result = HttpRequest.getLieu(Integer.parseInt(query[0]));
            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        public void onPostExecute(String result) {
            try {
                lieu = new JSONObject(result);

                nom.setText(lieu.getString("nom"));
                adresse.setText(lieu.getString("adresse"));
                ville.setText(lieu.getString("ville"));
                description.setText(lieu.getString("descriptif"));

                new RetrievePosition().execute("");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
