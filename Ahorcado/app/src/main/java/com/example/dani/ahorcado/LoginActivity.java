package com.example.dani.ahorcado;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import controller.Jugador;

/**
 * Created by dani on 18/10/16.
 */

public class LoginActivity extends AppCompatActivity {

    private static final String NOMBRE_BBDD_Jugadores =  "JUGADORES";

    private EditText user;
    private EditText pass;

    private Jugador jugador;
    private String  sqlTable = "CREATE TABLE "+NOMBRE_BBDD_Jugadores+" (NOMBRE TEXT PRIMARY KEY, PASS TEXT, PUNTUACION INTEGER)";
    private String  dropTable = "DROP TABLE IF EXISTS "+NOMBRE_BBDD_Jugadores;
    private BBDD_Jugadores db_jugadores ; // = new BBDD_Jugadores(this,NOMBRE_BBDD_Jugadores,null,1,sqlTable,dropTable);
    private SQLiteDatabase db_aux;

    protected void change_font(){
        String folder = "fonts/Gotham.ttf";
        TextView head_user = (TextView)findViewById(R.id.user_tag);
        user = (EditText) findViewById(R.id.user);
        TextView head_pass = (TextView)findViewById(R.id.pass_tag);
        pass = (EditText)findViewById(R.id.pass);
        Button login = (Button)findViewById(R.id.ok);
        Button singin = (Button)findViewById(R.id.singin);
        Button exit = (Button)findViewById(R.id.cancel);
        Typeface fuente = Typeface.createFromAsset(getAssets(), folder);
        head_user.setTypeface(fuente);
        user.setTypeface(fuente);
        head_pass.setTypeface(fuente);
        pass.setTypeface(fuente);
        login.setTypeface(fuente);
        singin.setTypeface(fuente);
        exit.setTypeface(fuente);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog);
        change_font();
        db_jugadores = new BBDD_Jugadores(this,NOMBRE_BBDD_Jugadores,null,1,sqlTable,dropTable);
    }

    private void show_message(Toast t, String msg){
        t = Toast.makeText(this,msg,Toast.LENGTH_SHORT);
        t.setGravity(Gravity.CENTER_VERTICAL,0,0);
        t.show();
    }


    public void validar(View view){
        Toast toast = new Toast(this);
        String usuario = user.getText().toString();
        String password = pass.getText().toString();
        if((!usuario.equals("")) && (!password.equals(""))){
            db_aux = db_jugadores.getReadableDatabase();
            if (db_aux != null) {
                show_message(toast,"Usuario validado");
            }

        }else{
                show_message(toast,"Rellena los campos");
        }
    }

    public void insertar (View view){

        Toast toast = new Toast(this);
        String usuario = user.getText().toString();
        String password = pass.getText().toString();

        if((!usuario.equals(""))&&(!password.equals(""))) {

            db_aux = db_jugadores.getWritableDatabase();
            if (db_aux != null) {
                show_message(toast,"Usuario Registrado. Por favor ahora inicia sesión");
            }

        }else{
            show_message(toast,"Rellena los campos");
        }
    }

    public void atras(View view){
        finish();
    }

}
