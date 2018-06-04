package asus.mycityquest;

import android.os.Build;
import android.support.annotation.RequiresApi;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ASUS on 04/06/2018.
 */

public class HttpRequest {
    protected static String base = "http://51.38.234.47/html/MyCityQuest/html";

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String egergetg(String url) throws IOException {
        InputStream is = null;
        String full_url = String.join("/", base, url);

        try {
            final HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setReadTimeout(10000); //milliseconds
            conn.setConnectTimeout(15000); //milliseconds
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            is = conn.getInputStream();
            //Lis l'inputstream et le save dans une string
            return readIt(is);
        } finally {
            //Bien fermer le InputStream
            if (is != null) {
                is.close();
            }
        }
        /*
        // Ici le `HttpGet` peut être changé par autre chose si vous voulez
        // définir une méthode pour chaque verbe HTTP
        HttpResponse response = httpClient.execute(new HttpGet(full_url));
        StatusLine statusLine = response.getStatusLine();

        if (statusLine.getStatusCode() == HttpStatus.SC_OK){
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            response.getEntity().writeTo(out);

            String responseString = out.toString();

            out.close();

            // A partir d'ici, vous avez accès à la chaîne de caractères
            // qui a été renvoyé par votre méthode
            return responseString;
        } else {
            response.getEntity().getContent().close();
            throw new IOException(statusLine.getReasonPhrase());
        }
*/
    }


    private String readIt(InputStream is) throws IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(is));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = r.readLine()) != null) {
            response.append(line).append('\n');
        }
        return response.toString();
    }
}
