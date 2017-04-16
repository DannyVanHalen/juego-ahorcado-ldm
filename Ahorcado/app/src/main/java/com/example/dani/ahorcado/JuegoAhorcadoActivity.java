package com.example.dani.ahorcado;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by dani on 10/04/17.
 */

@SuppressLint("DefaultLocale")
public class JuegoAhorcadoActivity extends AppCompatActivity {

    private static final String NOMBRE_BBDD_Jugadores =  "JUGADORES";
    private static final int BOTONES = 2;
    private final int[] id_botones = {R.id.salir,R.id.top};
    private final String abcd = "qwertyuiopasdfghjklñzxcvbnm";
    private final int[] estadoDibujo = {R.drawable.ahorcado0,R.drawable.ahorcado1,
    R.drawable.ahorcado2,R.drawable.ahorcado3,R.drawable.ahorcado4,R.drawable.ahorcado5,
    R.drawable.ahorcado6,R.drawable.ahorcado7,R.drawable.ahorcadowin};
    private final int[] ids = {R.id.Q,R.id.W,R.id.E,R.id.R,R.id.T,R.id.Y,R.id.U,R.id.I,R.id.O,R.id.P,
                               R.id.A,R.id.S,R.id.D,R.id.F,R.id.G,R.id.H,R.id.J,R.id.K,R.id.L,R.id.Ñ,
                               R.id.Z,R.id.X,R.id.C,R.id.V,R.id.B,R.id.N,R.id.M};

    /** Parte Orientada a la Jugabilidad**/
    private Ahorcado ahorcado;
    private View root;
    private TextView output;
    private TextView marcador_puntos;
    private int estado;
    private TextView teclado[];
    private ImageView dibujo;
    private Button[] botones; /** BOTONES {SALIR,TOP}*/

    /** Parte Orientada a la BBDD **/
    private Jugador jugador;
    private Jugadores top_jugadores;
    private String  sqlTable = "CREATE TABLE "+NOMBRE_BBDD_Jugadores+" (NOMBRE TEXT PRIMARY KEY, PASS TEXT, PUNTUACION INTEGER)";
    private String  dropTable = "DROP TABLE IF EXISTS "+NOMBRE_BBDD_Jugadores;
    private BBDD_Jugadores db_jugadores ; // = new BBDD_Jugadores(this,NOMBRE_BBDD_Jugadores,null,1,sqlTable,dropTable);
    private SQLiteDatabase db;
    private int version = 1;




    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.juego_ahorcado_activity);

        /** Parametros recibidos desde LoginActivity*/
        Bundle bundle = getIntent().getExtras();
        //Long id = bundle.getLong("id");
        String nombre = bundle.getString("nombre");
        String pass = bundle.getString("pass");
        int puntuacion = bundle.getInt("puntuacion");

        /** Funcionalidad BBDD*/
        db_jugadores = new BBDD_Jugadores(this,NOMBRE_BBDD_Jugadores,null,this.version,sqlTable,dropTable);

        /** Funcionalidad Activity*/
        this.jugador = new Jugador(nombre,pass,puntuacion);
        this.root = findViewById(R.id.root);
        this.teclado = new TextView[27];
        this.output = (TextView) findViewById(R.id.textView);
        this.marcador_puntos = (TextView) findViewById(R.id.contador);
        this.dibujo = (ImageView) findViewById(R.id.SPPatibulo);
        this.estado = 0;
        this.ahorcado = new Ahorcado(this);
        this.output.setText(this.ahorcado.getPalabra_formada());
        this.botones = new Button[BOTONES];
        this.asignar_botones();
        this.activar_desactivar_botones(false);
        this.cambiar_fuente();
        //marcador.setTypeface(fuente);
        this.inicializar_teclado();
        this.reiniciar_partida();

    }


    private void show_message(Toast t, String msg){
        t = Toast.makeText(this,msg,Toast.LENGTH_SHORT);
        t.setGravity(Gravity.CENTER_VERTICAL,0,700);
        t.show();
    }

    private void cambiar_fuente(){
        String folder = "fonts/Gotham.ttf";
        Typeface fuente = Typeface.createFromAsset(getAssets(), folder);
        TextView palabra = (TextView)findViewById(R.id.textView);
        //TextView marcador = (TextView)findViewById(R.id.contador);
        TextView puntos = (TextView) findViewById(R.id.puntos);
        palabra.setTypeface(fuente);
        puntos.setTypeface(fuente);
        for(int i = 0; i < this.botones.length;i++){
            this.botones[i].setTypeface(fuente);
        }
    }

    private void reinicia_valores(){
        if (estado > 7) {
            nuevaPalabra();
        }

        if (estado == 7) {
            if (output.getText().toString().contains(ahorcado.getPalabra()))
                nuevaPalabra();
            else
                output.setText(ahorcado.getPalabra());
        }

        dibujo.setImageResource(estadoDibujo[0]);

    }


    private void reiniciar_partida(){
        this.dibujo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reinicia_valores();
            }
        });
    }

    private void asignar_botones(){
        for(int i = 0; i < this.BOTONES;i++){
            this.botones[i] = (Button)findViewById(this.id_botones[i]);
        }
    }

    private void activar_desactivar_botones(boolean funcion){
        for(int i = 0; i < this.botones.length;i++)
            this.botones[i].setClickable(funcion);
    }

    private void inicializar_teclado(){
        for(int i = 0; i < this.ids.length;i++) {
            this.teclado[i] = (TextView) findViewById(this.ids[i]);
            //this.teclado[i].setTypeface(fuente);
            this.teclado[i].setClickable(true);
            this.teclado[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setChar(view);
                }
            });
        }
    }

    private boolean update (SQLiteDatabase db, String id){
        ContentValues values = new ContentValues();
        values.put("NOMBRE",this.jugador.getNombre());
        values.put("PASS",this.jugador.getPass());
        values.put("PUNTUACION",this.jugador.getPuntuacion());
        db.update(this.NOMBRE_BBDD_Jugadores,values,"NOMBRE=?",new String[]{id});
        return true;
    }

    public void setChar(View v){
        if(this.estado == 7){
            return;
        }

        //char c = ((TextView)v).getText().toString().toLowerCase().charAt(0); //leer caracter de teclado
        char c = ((TextView)v).getText().toString().toLowerCase().charAt(0);
        this.ahorcado.checkWord(c);
        if(this.ahorcado.aparece(c)){
            ((TextView)v).setTextColor(Color.BLUE);
            if(this.ahorcado.fin_partida())
                this.estado = 8;
        }else{
            v.setVisibility(View.INVISIBLE);
            if(this.estado < 7)
                this.estado++;
        }

        //this.output.setText(this.ahorcado.autoresolver());
        this.output.setText(this.ahorcado.getPalabra_formada());
        this.marcador_puntos.setText(String.valueOf(this.ahorcado.getPuntuacion()));
        this.dibujo.setImageResource(this.estadoDibujo[this.estado]);

        boolean partida_acabada = this.estado >= 7;

        if(partida_acabada){
            this.activar_desactivar_botones(true);
            if(this.estado == 7) {
                this.output.setText(this.ahorcado.autoresolver());
                this.show_message(new Toast(this),"Has Perdido la Partida");
            }
            if(this.estado == 8){
                this.jugador.setPuntuacion(this.ahorcado.getPuntuacion() + this.jugador.getPuntuacion());
                this.db = this.db_jugadores.getWritableDatabase();
                if (db != null) {
                    Cursor busqueda = null;
                    try {
                        busqueda = db.rawQuery("SELECT NOMBRE FROM " + this.NOMBRE_BBDD_Jugadores + " WHERE NOMBRE=?", new String[]{this.jugador.getNombre() + ""});
                        if (busqueda.moveToFirst()) {
                            String id = busqueda.getString(busqueda.getColumnIndex("NOMBRE"));
                            if (this.update(db, id)) {
                                this.show_message(new Toast(this), "Has Ganado la Partida");
                            }
                        }
                    } finally {
                        busqueda.close();
                    }

                }
                this.db.close();
            }


        }

    }

    public void nuevaPalabra(){
        this.output.setText(this.ahorcado.nuevaPalabra());
        this.estado = 0;
        for(int i = 0; i < this.teclado.length;i++){
            if(this.root.findViewById(this.ids[i]) != null) {
                this.teclado[i].setVisibility(View.VISIBLE);
                this.teclado[i].setTextColor(Color.GRAY);
            }
        }
    }



    public void mostrarTop(View view){

        Intent i = new Intent(this,Top_Jugadores.class);
        i.putExtra("nombre",this.jugador.getNombre());
        i.putExtra("pass",this.jugador.getPass());
        i.putExtra("puntuacion",this.jugador.getPuntuacion());
        startActivity(i);
        this.finish();

    }


    public void salir(View view){
        this.finish();
    }

}
