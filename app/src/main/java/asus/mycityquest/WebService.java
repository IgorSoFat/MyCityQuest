package asus.mycityquest;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Romain Fauquet - 14/03/2018.
 */

public class WebService {
    private String urlBase;

    public WebService() {
        this("http://51.38.234.47/html/MyCityQuest/");
    }

    public WebService(String url) {
        this.urlBase = url;
    }

    private String get(String url) throws IOException {
        String source = "";
        URL oracle = new URL(url);
        URLConnection yc = oracle.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        yc.getInputStream()));
        String inputLine;

        while ((inputLine = in.readLine()) != null)
            source += inputLine;
        in.close();
        return source;
    }

    public Boolean connect(String login, String password) throws Exception {
        String url = urlBase + "connexion.php?login=" + login + "&password=" + password;
        if("true".equals(get(url))) {
            return true;
        } else return false;
    }

    public JSONObject createAccount(String login, String password) throws Exception {
        String url = urlBase + "createaccount.php?login=" + login + "&password=" + password;
        JSONObject result = new JSONObject(get(url));
        /* Upload de l'image, non fonctionnel pour le moment
        if (result.getString("status").equals("0")) {
            String api_key = connect(login, password).getString("apikey");
            uploadPicture(photoBdd, api_key);
        }
        */
        return result;
    }

    public JSONObject getLieu() throws Exception {
        String url = urlBase + "getlieu.php";
        return new JSONObject(get(url));
    }

    public JSONObject getLieu(int idLieu) throws Exception {
        String url = urlBase + "getlieu.php?id_lieu=" + idLieu;
        return new JSONObject(get(url));
    }

    public JSONObject getEvenement() throws Exception {
        String url = urlBase + "getevenement.php";
        return new JSONObject(get(url));
    }

    public JSONObject getEvenement(int idLieu) throws Exception {
        String url = urlBase + "getevenement.php?id_lieu=" + idLieu;
        return new JSONObject(get(url));
    }

    public JSONObject addLieu(){return null;}
}