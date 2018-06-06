package asus.mycityquest;

import android.os.Build;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by ASUS on 04/06/2018.
 */

public class HttpRequest {
    protected static String base = "http://robin-dupret.com:4000";

    /**
     * Permet de réaliser une requête GET vers le site et de récupérer
     * la réponse sous forme de chaîne de caractères.
     *
     * @param endpoint - Endpoint du site auquel accéder
     * @return Réponse du serveur (format JSON en principe)
     * @throws IOException
     */

    public String get(String endpoint) throws IOException {
        HttpResponse response = new DefaultHttpClient().execute(new HttpGet(fullUrl(endpoint)));
        StatusLine statusLine = response.getStatusLine();

        if (statusLine.getStatusCode() == HttpStatus.SC_OK){
            return getOut(response);
        } else {
            response.getEntity().getContent().close();

            throw new IOException(statusLine.getReasonPhrase());
        }
    }

    /**
     * Permet de réaliser une requête POST vers le site en spécifiant un corps
     * pour la requête (au format JSON) et de récupérer la réponse sous forme
     * de chaînes de caractères.
     *
     * Le cast en objet java (vraissemblalement JSONArray ou JSONObject) doit se
     * faire à partir de la chaîne renvoyée et dans le code faisant appel à cette
     * méthode.
     *
     * @param endpoint - Endpoint du site auquel accéder
     * @param body     - Objet JSON à définir en tant que corps de la requête ou null
     * @return Réponse du serveur (format JSON en principe)
     */
    public String post(String endpoint, JSONObject body) throws IOException {
        HttpPost post = new HttpPost(fullUrl(endpoint));

        HttpEntity entity = new ByteArrayEntity(xml.getBytes("UTF-8"));
        post.setEntity(entity);
        HttpResponse response = new DefaultHttpClient().execute(post);
        StatusLine statusLine = response.getStatusLine();

        if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
            return getOut(response);
        } else {
            response.getEntity().getContent().close();

            Log.i("Erreur de call HTTP :", statusLine.getReasonPhrase());
        }
    }


    protected String fullUrl(String endpoint) {
        return base + "/" + endpoint;
    }

    /**
     * Permet de récupérer le résultat (i.e. body) d'une réponse HTTP
     * sous forme de chaîne de caractères.
     *
     * @param response - La réponse HTTP d'un call précédent
     * @return Body de la réponse (au format JSON en principe)
     */
    protected String getOut(HttpResponse response) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        response.getEntity().writeTo(out);
        String responseString = out.toString();
        out.close();

        return responseString;
    }
}
