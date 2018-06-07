package asus.mycityquest;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ASUS on 04/06/2018.
 */

public class HttpRequest {


    protected static String base = "http://51.38.234.47/html/MyCityQuest/phpmobile/";

    protected static String TAG = "HTTP REQUEST URL : ";

    /**
     * +     * Permet de réaliser une requête GET vers le site et de récupérer
     * +     * la réponse sous forme de chaîne de caractères.
     * +     *
     * +     * @param endpoint - Endpoint du site auquel accéder
     * +     * @return Réponse du serveur (format JSON en principe)
     * +     * @throws IOException
     * +
     */

    public static String get(String urlToRead) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }

    protected static String fullUrl(String endpoint) {
        return base + "/" + endpoint;
    }

    /**
     * +     * Permet de récupérer le résultat (i.e. body) d'une réponse HTTP
     * +     * sous forme de chaîne de caractères.
     * +     *
     * +     * @param response - La réponse HTTP d'un call précédent
     * +     * @return Body de la réponse (au format JSON en principe)
     * +
     */
    protected static String getOut(HttpResponse response) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        response.getEntity().writeTo(out);
        String responseString = out.toString();
        out.close();

        return responseString;
    }

    public static String getLieu(String ville, String groupe) throws Exception {
        String url = base + "searchVilleByNameFilter.php?ville=" + ville + "&categorie=" + groupe;
        Log.e(TAG, url);
        JSONObject resultat = new JSONObject(get(url));

        return get(url);
    }
// Voir si le status est à 1, on se log, si 0 on refuse le log, (voir dans class LoginActivity)
    public static String connecter(String login, String password) throws Exception {
        String url = base + "connexion.php?nomUtilisateur=" + login + "&motDePasse=" + password;
        Log.e(TAG, url);
        JSONObject connexionStatus = new JSONObject(get(url));
        String status = connexionStatus.getString("sucess");
        Log.e(TAG,status);
        return status;
    }

    public static String register(String login, String password, String nom, String prenom) throws Exception {
        String url = base + "inscription.php?Nom=" + nom + "&Prenom=" + prenom + "&Login=" + login
                + "&Password=" + password;
        Log.e(TAG, url);
        return get(url);
    }
}