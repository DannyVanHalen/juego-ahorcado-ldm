package com.example.dani.ahorcado;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by dani on 16/04/17.
 */

public class Top_Jugadores extends AppCompatActivity {

    private static final String NOMBRE_BBDD_Jugadores = "JUGADORES";


    /** Parte Orientada al uso de la BBDD **/
    private Jugador jugador_actual;
    private Bundle bundle;
    private Jugadores top_jugadores;
    private String sqlTable = "CREATE TABLE " + NOMBRE_BBDD_Jugadores + " (NOMBRE TEXT PRIMARY KEY, PASS TEXT, PUNTUACION INTEGER)";
    private String dropTable = "DROP TABLE IF EXISTS " + NOMBRE_BBDD_Jugadores;
    private BBDD_Jugadores db_jugadores; // = new BBDD_Jugadores(this,NOMBRE_BBDD_Jugadores,null,1,sqlTable,dropTable);
    private SQLiteDatabase db;
    private int version = 1;


    /** Parte Orientada al Layout **/
    private ListView list;
    private Button back;
    private ArrayList<String> lista;
    private ArrayAdapter<String> adaptador ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top_list);
        String folder = "fonts/Gotham.ttf";
        Typeface fuente = Typeface.createFromAsset(getAssets(), folder);
        TextView clasificacion = (TextView) findViewById(R.id.topTitle);
        clasificacion.setTextColor(Color.BLACK);
        clasificacion.setTypeface(fuente);
        this.back = (Button)findViewById(R.id.volver);
        this.back.setTypeface(fuente);
        this.bundle = getIntent().getExtras();
        this.jugador_actual = new Jugador(bundle.getString("nombre"),bundle.getString("pass"),bundle.getInt("puntuacion"));

        this.list = (ListView) findViewById(R.id.topList);
        this. lista = this.leer_clasificacion();
        this.adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,this.lista);
        list.setAdapter(this.adaptador);

    }


    private ArrayList leer_clasificacion() {

        ArrayList<String> salida = new ArrayList<>();
        db_jugadores = new BBDD_Jugadores(this, NOMBRE_BBDD_Jugadores, null, this.version, sqlTable, dropTable);

        this.top_jugadores = new Jugadores();
        this.db = this.db_jugadores.getReadableDatabase();
        try {
            if (db != null) {
                Cursor cursor = this.db.rawQuery("select * from " + this.NOMBRE_BBDD_Jugadores, null);
                try {
                    if (cursor.moveToFirst()) {
                        while (!cursor.isAfterLast()) {
                            String nombre = cursor.getString(0);
                            String pass = cursor.getString(1);
                            Integer puntuacion = cursor.getInt(2);
                            Jugador jugador = new Jugador(nombre, pass, puntuacion);
                            if (this.top_jugadores.addJugador(jugador))
                                cursor.moveToNext();
                        }
                        if(!this.top_jugadores.getJugadores().isEmpty()){
                            this.top_jugadores.ordenarTop();
                            for(Jugador jugador:this.top_jugadores.getJugadores()){
                                if(salida.add(jugador.toString()))
                                    continue;
                            }

                        }
                    }
                } finally {
                    try {
                        cursor.close();
                    } catch (Exception ignore) {
                    }
                }
            }
        } finally {
            try {
                this.db.close();
            } catch (Exception igenore) {
            }
        }

        return salida;

    }

    public void volver_a_jugar(View view){
        Intent i = new Intent(this,JuegoAhorcadoActivity.class);
        i.putExtra("nombre",this.jugador_actual.getNombre());
        i.putExtra("pass",this.jugador_actual.getPass());
        i.putExtra("puntuacion",this.jugador_actual.getPuntuacion());
        startActivity(i);
        this.finish();
    }

}
