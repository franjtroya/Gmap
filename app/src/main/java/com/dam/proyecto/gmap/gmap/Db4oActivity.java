package com.dam.proyecto.gmap.gmap;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.AndroidSupport;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.query.Predicate;
import com.db4o.query.Query;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

public class Db4oActivity extends AppCompatActivity {

    private static final String TAG = "asdf";

    private ObjectContainer objectContainer;
    private android.widget.DatePicker datePicker;
    private android.widget.TextView lblDatePicker;
    private android.widget.Button btnConsultar;


    public EmbeddedConfiguration getDb4oConfig() throws IOException {
        EmbeddedConfiguration configuration = Db4oEmbedded.newConfiguration();
        configuration.common().add(new AndroidSupport());
        configuration.common().objectClass(Localizacion.class).
                objectField("fecha").indexed(true);
        return configuration;
    }

    private void events(){
        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date fechaConsulta = new GregorianCalendar(datePicker.getYear(),datePicker.getMonth(),
                        datePicker.getDayOfMonth()).getTime();
                ArrayList<Localizacion> queryByDate = queryByDate(fechaConsulta);
                if(queryByDate.size() > 0){
                    lblDatePicker.setText("Esta fecha está disponible");
                } else {
                    lblDatePicker.setText("Esta fecha NO esta disponible");
                }
            }
        });
    }

    private void init(){
        this.btnConsultar =  findViewById(R.id.btnConsultar);
        this.lblDatePicker =  findViewById(R.id.textView);
        this.datePicker =  findViewById(R.id.datePicker);

        events();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db4o);
        init();
    }

    private ObjectContainer openDataBase(String archivo) {
        ObjectContainer objectContainer = null;
        try {
            String name = getExternalFilesDir(null) + "/" + archivo;
            objectContainer = Db4oEmbedded.openFile(getDb4oConfig(), name);
        } catch (IOException e) {
            Log.v(TAG, e.toString());
        }
        return objectContainer;
    }

    public ArrayList<Localizacion> queryByDate(final Date dateQuery){
        ArrayList<Localizacion> arLocs = new ArrayList<>();
        objectContainer = openDataBase("locdb.db4o");
        Localizacion locPrueba = new Localizacion(new Location("Provider"),
                new GregorianCalendar(2018, 2, 12).getTime());
        objectContainer.store(locPrueba);
        objectContainer.commit();

        ObjectSet<Localizacion> locs = objectContainer.query(
                new Predicate<Localizacion>() {
                    @Override
                    public boolean match(Localizacion loc) {
                        return loc.getFecha().equals(dateQuery);
                    }
                });
        for (Localizacion l:locs) {
            arLocs.add(l);
        }
        objectContainer.close();
        return arLocs;
    }

}