package asus.mycityquest;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LieuxAdapter extends BaseAdapter {
    protected IndexActivity parent;
    protected ArrayList<JSONObject> list;

    public LieuxAdapter(IndexActivity parent, ArrayList<JSONObject> list) {
        this.parent = parent;
        this.list   = list;
    }

    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    /**
     * Permet de générer une vue (View) pour afficher un lieu donné par rapport
     * à une position donnée.
     *
     * @return Une vue représentant le lieu dans la ListView
     */
    public View getView(int position, View convertView, ViewGroup _) {
        JSONObject lieu = this.list.get(position);

        LinearLayout lieuView = new LinearLayout(parent);
        lieuView.setOrientation(LinearLayout.VERTICAL);
        lieuView.setGravity(Gravity.CLIP_HORIZONTAL);

        LinearLayout.LayoutParams nameParams    = parent.newParams();
        LinearLayout.LayoutParams adresseParams = parent.newParams();

        nameParams.setMargins(parent.pxToDp(20), parent.pxToDp(15), parent.pxToDp(20), parent.pxToDp(10));
        adresseParams.setMargins(parent.pxToDp(20), parent.pxToDp(5), parent.pxToDp(20), parent.pxToDp(10));

        final TextView name    = new TextView(parent);
        final TextView adresse = new TextView(parent);

        try {
            name.setText(lieu.getString("nom"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        name.setTextSize(25);
        name.setLayoutParams(nameParams);

        try {
            adresse.setText(lieu.getString("adresse"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        adresse.setTextSize(18);
        adresse.setLayoutParams(adresseParams);

        lieuView.addView(name);
        lieuView.addView(adresse);

        return lieuView;
    }

    public ArrayList<JSONObject> getList() {
        return list;
    }
}
