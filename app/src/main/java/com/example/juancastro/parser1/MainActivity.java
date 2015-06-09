package com.example.juancastro.parser1;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;



public class MainActivity extends ListActivity {

    // All static variables
    static final String URL = "http://resources.260mb.net/bbdd-l.xml";
    // XML node keys
    static final String KEY_ANIMALES = "animal"; // parent node
    static final String KEY_ID = "id";
    static final String KEY_NOMBRE_COMUN = "nombre_comun";
    static final String KEY_NOMBRE_CIENT= "nombre_cient";
    static final String KEY_HABITAT = "habitat";
    static final String KEY_CARACTERISTICAS = "caracteristicas";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();

        XMLParser parser = new XMLParser();
        String xml = parser.getXmlFromUrl(URL); // getting XML
        Document doc = parser.getDomElement(xml); // getting DOM element

        NodeList nl = doc.getElementsByTagName(KEY_ANIMALES);

        for (int i = 0; i < nl.getLength(); i++) {
            // creating new HashMap
            HashMap<String, String> map = new HashMap<String, String>();
            Element e = (Element) nl.item(i);
            // adding each child node to HashMap key => value
            map.put(KEY_ID, parser.getValue(e, KEY_ID));
            map.put(KEY_NOMBRE_COMUN, parser.getValue(e, KEY_NOMBRE_COMUN));
            map.put(KEY_NOMBRE_CIENT, parser.getValue(e, KEY_NOMBRE_CIENT));
            map.put(KEY_HABITAT, parser.getValue(e, KEY_HABITAT));
            map.put(KEY_CARACTERISTICAS, parser.getValue(e, KEY_CARACTERISTICAS));

            // adding HashList to ArrayList
            menuItems.add(map);

        }

        // Adding menuItems to ListView
        ListAdapter adapter = new SimpleAdapter(this, menuItems,
                R.layout.lista_animales,
                new String[] { KEY_ID, KEY_NOMBRE_COMUN }, new int[] {
                R.id.id_an, R.id.a_nc});

        setListAdapter(adapter);

        // selecting single ListView item
        ListView lv = getListView();

        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String tx_id = ((TextView) view.findViewById(R.id.id_an)).getText().toString();
                String tx_nombre_comun = ((TextView) view.findViewById(R.id.a_nc)).getText().toString();


                // Starting new intent
                Intent in = new Intent(getApplicationContext(), vista_individual.class);
                in.putExtra(KEY_ID, tx_id);
                in.putExtra(KEY_NOMBRE_COMUN, tx_nombre_comun);

                startActivity(in);

            }
        });
    }
}