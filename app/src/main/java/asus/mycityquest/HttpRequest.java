package asus.mycityquest;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

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

        InputStream in;
        int status = conn.getResponseCode();

        if (status != HttpURLConnection.HTTP_OK) {
            in = conn.getErrorStream();
        } else {
            in = conn.getInputStream();
        }

        BufferedReader rd = new BufferedReader(new InputStreamReader(in));
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

    public static String getLieux(String ville, String groupe) throws Exception {
        String url = base + "searchVilleByNameFilter.php?ville=" + ville + "&categorie=" + groupe;
        Log.e(TAG, url);

        return get(url);
    }

    public static String getLieu(Integer id) throws Exception {
        String url = base + "getLieu.php?id=" + id;
        Log.e(TAG, url);

        return get(url);
    }

    // Voir si le status est à 1, on se log, si 0 on refuse le log, (voir dans class LoginActivity)
    public static String connecter(String login, String password) throws Exception {
        String url = base + "connexion.php?nomUtilisateur=" + login + "&motDePasse=" + password;
        Log.e(TAG, url);
        JSONObject connexionStatus = new JSONObject(get(url));
        String status = connexionStatus.getString("sucess");
        Log.e(TAG, status);
        return status;
    }

    public static String register(String login, String password, String nom, String prenom, String categorie) throws Exception {
        String url = base + "inscription.php?Nom=" + nom + "&Prenom=" + prenom + "&Login=" + login
                + "&Password=" + password + "&Categorie=" + categorie;
        Log.e(TAG, url);
        return get(url);
    }

    public static String addLieu(String nom, String adresse, String ville, String categorie, String descriptif) throws Exception {
        String url = base + "addLieu.php?Nom=" + URLEncoder.encode(nom, "UTF-8") + "&Adresse=" + URLEncoder.encode(adresse, "UTF-8") + "&Ville=" + URLEncoder.encode(ville, "UTF-8")
                + "&Categorie=" + URLEncoder.encode(categorie, "UTF-8") + "&Descriptif=" + URLEncoder.encode(descriptif, "UTF-8");
        Log.e(TAG, url);
        return get(url);
    }

    public static JSONArray getAllAdress() throws Exception {
        String url = base + "getAllAdresse.php";
        Log.e(TAG, url);
        JSONArray jsonOArray = new JSONArray(get(url));
        return jsonOArray;
    }
}