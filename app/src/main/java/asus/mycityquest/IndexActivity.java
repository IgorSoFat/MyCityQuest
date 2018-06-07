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
import android.widget.Toast;

public class IndexActivity extends AppCompatActivity {

    private EditText recherche;
    private Spinner spinner;
    private ListView list;
    private String groupe = "Tous";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        recherche = findViewById(R.id.rechercheVille);
        list = this.findViewById(R.id.listLieux);
       // spinner = this.findViewById(R.id.spinnerGroupe);
        HttpRequest httpRequest = new HttpRequest();


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
}
