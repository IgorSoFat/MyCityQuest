package asus.mycityquest;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

// TEST :>
// biig : 1234

/**
 * A login screen that offers login via email/password.
 */
public class addLieuActivity extends AppCompatActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    // UI references.
    public TextView mNomView;
    public TextView mAdresseView;
    public TextView mVilleView;
    public Spinner mCategorieSpinner;
    public TextView mDescView;

    public String TAG = "AddLieu ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_addlieu);

        Spinner mCategorieSpinner =(Spinner) findViewById(R.id.listType);

        ArrayAdapter <CharSequence> adapter =
                ArrayAdapter.createFromResource(this,R.array.listType,android.R.layout.
                simple_spinner_item); 

        // Set up the login form.
        mNomView = (TextView) findViewById(R.id.nomAdd);
        mAdresseView = (TextView) findViewById(R.id.adresseAdd);
        mVilleView = (TextView) findViewById(R.id.villeAdd);
        mCategorieSpinner = (Spinner) findViewById(R.id.listType);
        mDescView = (TextView) findViewById(R.id.descAdd);


        Button mLieuAddButton = (Button) findViewById(R.id.lieu_valid_button);
        mLieuAddButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Log.e(TAG, "entree attempt Register");
                    attemptRegister();
                    Log.e(TAG, "sortie attempt Register");
                    Toast.makeText(addLieuActivity.this, "Lieu créé avec succès",
                            Toast.LENGTH_LONG).show();

                } catch (Exception e) {

                    Toast.makeText(addLieuActivity.this, "La création a échoué ...",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
    }

    public void attemptRegister() throws Exception {
        HttpRequest httpRequest = new HttpRequest();
        // Reset errors.
        mNomView.setError(null);
        mAdresseView.setError(null);
        mVilleView.setError(null);

        // Store values at the time of the login attempt.
        String nom = mNomView.getText().toString();
        String adresse = mAdresseView.getText().toString();
        String ville = mVilleView.getText().toString();
        String categorie = mCategorieSpinner.toString();
        String desc = mDescView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        /*if (!TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }*/

        // Check for a valid email address.
        if (TextUtils.isEmpty(nom)) {
            mNomView.setError(getString(R.string.error_field_required));
            focusView = mNomView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            new RetrieveFeedTask().execute("");
        }
    }

    class RetrieveFeedTask extends AsyncTask<String, Void, String> {

        private Exception exception;

        protected String doInBackground(String... urls) {
            try {
                attemptRegister();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "done";
        }

        protected void onPostExecute(String feed) {
            // TODO: check this.exception
            // TODO: do something with the feed
        }

        /**
         * Attempts to sign in or register the account specified by the login form.
         * If there are form errors (invalid email, missing fields, etc.), the
         * errors are presented and no actual login attempt is made.
         */
        public void attemptRegister() throws Exception {
            HttpRequest httpRequest = new HttpRequest();
            // Reset errors.

            // Store values at the time of the login attempt.
            String nom = mNomView.getText().toString();
            String adresse = mAdresseView.getText().toString();
            String ville = mVilleView.getText().toString();
            String categorie = mCategorieSpinner.toString();
            String desc = mDescView.getText().toString();

            boolean cancel = false;
            View focusView = null;

            if (cancel) {
                // There was an error; don't attempt login and focus the first
                // form field with an error.
                focusView.requestFocus();
            } else {
                HttpRequest.addLieu(nom,adresse,ville,categorie,desc);
            }
        }
    }
}

