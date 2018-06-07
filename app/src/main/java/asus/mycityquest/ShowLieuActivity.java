package asus.mycityquest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/*
 *
 * Affiche les lieux d'une ville choisie dans IndexAcitivty
 *
 *
 */

public class ShowLieuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        FloatingActionButton addLieu = (FloatingActionButton) findViewById(R.id.addLieuButton);
        addLieu.bringToFront();
        addLieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ShowLieuActivity.this, "Clic !", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ShowLieuActivity.this,addLieuActivity.class);
                startActivity(intent);
            }
        });

    }


}
