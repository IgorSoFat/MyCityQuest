package asus.mycityquest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

public class IndexActivity extends AppCompatActivity {

    private EditText recherche;
    private WebService webService;
    private Spinner spinner;
    private ListView list;
    private String groupe = "Tous";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        recherche = findViewById(R.id.recherche);
        list = this.findViewById(R.id.listContact);
        spinner = this.findViewById(R.id.spinnerGroupe);
        webService = new WebService();

/*
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.listGroupeMainPage, android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //FILTRE
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                groupe = (String) spinner.getItemAtPosition(position);
                if (groupe.equals((String) "Tous")) {
                    contactsGroupe = db.affichageContact();
                } else {
                    contactsGroupe = db.affichageGroupe(groupe);
                }
                information = new String[contactsGroupe.size()];
                for (int i = 0; i < contactsGroupe.size(); i++) {
                    Contact c = contactsGroupe.get(i);
                    information[i] = " \n " + c.getNom() + " " + c.getPrenom();
                }
                final ArrayAdapter<String> afficher = new ArrayAdapter<String>(MainPage.this, android.
                        R.layout.simple_list_item_1, information);
                list.setAdapter(afficher);
                list = findViewById(R.id.listContact);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> afficher, View view, int position, long id) {
                        Contact c = contactsGroupe.get(position);
                        Intent intent = new Intent(MainPage.this, ShowContact.class);
                        intent.putExtra("Contact", c);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        recherche.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            // On appelle le webservice getLieuxByVille
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                contactsRecherche = db.affichageRecherche(s.toString());
                String[] information2 = new String[contactsRecherche.size()];
                for (int i = 0; i < contactsRecherche.size(); i++) {
                    Contact c = contactsRecherche.get(i);
                    information2[i] = " \n " + c.getNom() + " " + c.getPrenom();
                }
                ArrayAdapter<String> afficher2 = new ArrayAdapter<String>(MainPage.this, android.
                        R.layout.simple_list_item_1, information2);
                list.setAdapter(afficher2);
                afficher2.notifyDataSetChanged();
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> afficher, View view, int position, long id) {
                        Contact c = contactsRecherche.get(position);
                        Intent intent = new Intent(MainPage.this, ShowContact.class);
                        intent.putExtra("Contact", c);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}
*/

    }
}
