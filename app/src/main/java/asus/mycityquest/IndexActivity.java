package asus.mycityquest;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class IndexActivity extends AppCompatActivity {

    private EditText recherche;
    private Spinner spinner;
    private ListView list;
    private String groupe;
    protected float scale;

    protected LieuxAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        recherche = findViewById(R.id.rechercheVille);
        list = this.findViewById(R.id.listLieux);

        this.scale = getResources().getDisplayMetrics().density;

        // +------------------------+
        // | Gestion des catégories |
        // +------------------------+
        spinner = findViewById(R.id.listType);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                groupe = getResources().getStringArray(R.array.listType)[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                groupe = getResources().getStringArray(R.array.listType)[0];
            }
        });

        ArrayAdapter<CharSequence> categoriesAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.listType,
            android.R.layout.simple_spinner_dropdown_item
        );

        spinner.setAdapter(categoriesAdapter);

        // +-------------------------+
        // | Gestion de la recherche |
        // +-------------------------+
        recherche = findViewById(R.id.rechercheVille);

        recherche.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int s, int b, int c) {
                RetrieveResults task = new RetrieveResults();
                task.execute(cs.toString(), groupe);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

            @Override
            public void beforeTextChanged(CharSequence cs, int i, int j, int k) {

            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(IndexActivity.this, ShowLieuActivity.class);

                Bundle bundle = new Bundle();

                try {
                    bundle.putInt("id", adapter.getList().get(i).getInt("id"));
                } catch (JSONException e) {
                    bundle.putInt("id", -1);
                    e.printStackTrace();
                }

                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

        // Bouton d'ajout d'un nouveau lieu
        FloatingActionButton addLieu = (FloatingActionButton) findViewById(R.id.addLieuButton);
        //addLieu.bringToFront();
        addLieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(IndexActivity.this, "Clic !", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(IndexActivity.this,addLieuActivity.class);
                startActivity(intent);
            }
        });
    }

    class RetrieveResults extends AsyncTask<String, Void, String> {
        @Override
        public String doInBackground(String ...query) {
            String result = "";

            try {
                result = HttpRequest.getLieux(query[0], query[1]);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ArrayList lieux = new ArrayList<JSONObject>();

            try {
                JSONArray array = new JSONArray(result);

                for (int i = 0; i < array.length(); i++) {
                    try {
                        lieux.add(array.getJSONObject(i));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                adapter = new LieuxAdapter(IndexActivity.this, lieux);
                list.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Permet de récupérer une nouvelle instance de LayoutParams
     * afin d'appliquer des marges aux éléments de l'interface.
     */
    protected LinearLayout.LayoutParams newParams() {
        return new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        );
    }

    /**
     * Permet de convertir des px en dp pour avoir le même rendu sur
     * tous les terminaux.
     *
     * @param px - La valeur en pixels.
     */
    public int pxToDp(int px) {
        return (int)(scale * px+ 0.5f);
    }
}
