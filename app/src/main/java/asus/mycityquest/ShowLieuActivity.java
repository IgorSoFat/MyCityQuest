package asus.mycityquest;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/*
 *
 * Affiche les lieux d'une ville choisie dans IndexAcitivty
 *
 *
 */

public class ShowLieuActivity extends AppCompatActivity {
    protected JSONObject lieu;

    protected TextView nom;
    protected TextView ville;
    protected TextView adresse;
    protected TextView description;

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
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
