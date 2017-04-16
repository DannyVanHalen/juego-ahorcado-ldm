package com.example.dani.ahorcado;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
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
    private SQLiteDatabase db;
    private int version = 1;

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
        setContentView(R.layout.login_activity);
        change_font();
        db_jugadores = new BBDD_Jugadores(this,NOMBRE_BBDD_Jugadores,null,this.version,sqlTable,dropTable);
        //db_jugadores.onUpgrade(this.db_jugadores.getWritableDatabase(),this.version,(this.version+1));
        //this.version +=1;
    }

    private void show_message(Toast t, String msg){
        t = Toast.makeText(this,msg,Toast.LENGTH_SHORT);
        t.setGravity(Gravity.CENTER_VERTICAL,0,700);
        t.show();
    }

    private boolean existe_usuario(String usuario){
        boolean encontrado = false;
        db = db_jugadores.getReadableDatabase();
        if(db != null) {
            Cursor busqueda = db.rawQuery("SELECT NOMBRE, PASS, PUNTUACION FROM " + this.NOMBRE_BBDD_Jugadores + " WHERE NOMBRE='"+usuario+"'",null);
            encontrado = busqueda.moveToFirst();
        }
        db.close();
        return encontrado;
    }



    public void validar(View view){
        Toast toast = new Toast(this);
        String usuario = user.getText().toString();
        String password = pass.getText().toString();
        if((!usuario.equals("")) && (!password.equals(""))){
            //db = db_jugadores.getReadableDatabase();
            db = db_jugadores.getReadableDatabase();
            if (db != null) {
                //Cursor busqueda = db.rawQuery("SELECT NOMBRE,PASS FROM "+this.NOMBRE_BBDD_Jugadores+" WHERE NOMBRE='"+usuario+"' AND PASS='"+pass+"'",null);
                String [] campos = {"NOMBRE","PASS","PUNTUACION"};
                String [] args = {usuario,password};
                  Cursor busqueda = db.query(this.NOMBRE_BBDD_Jugadores,campos,"NOMBRE=? AND PASS=?",args,null,null,null);
                if(busqueda.moveToFirst()){
                    //long id = busqueda.getLong(busqueda.getColumnIndex("_id"));
                    this.user.setText("");
                    this.pass.setText("");
                    Intent intent;
                    intent = new Intent(this,JuegoAhorcadoActivity.class);
                    //intent.putExtra("id",id);
                    intent.putExtra("nombre",busqueda.getString(0));
                    intent.putExtra("pass",busqueda.getString(1));
                    intent.putExtra("puntuacion",busqueda.getInt(2));
                    busqueda.close();
                    db.close();
                    startActivity(intent);
                }else{
                    db.close();
                }

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
            if(!this.existe_usuario(usuario)) {
                db = db_jugadores.getWritableDatabase();
                if (db != null) {
                    ContentValues registro = new ContentValues();
                    registro.put("NOMBRE", usuario);
                    registro.put("PASS", password);
                    registro.put("PUNTUACION", 0);
                    db.insert(this.NOMBRE_BBDD_Jugadores, null, registro);
                    db.close();
                    user.setText("");
                    pass.setText("");
                    show_message(toast, "Usuario Registrado. Por favor ahora inicia sesión");
                }
            }else{
                user.setText("");
                pass.setText("");
                show_message(toast,"Ya existe un usuario :"+usuario);
            }

        }else{
            show_message(toast,"Rellena los campos vacíos");
        }
    }

    public void atras(View view){
        finish();
    }



}
