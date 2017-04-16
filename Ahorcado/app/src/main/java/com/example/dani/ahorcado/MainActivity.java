package com.example.dani.ahorcado;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.text.AllCapsTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //private Button player, help, exit;
    //private Activity act;

    protected void change_font(){
        String folder = "fonts/Gotham.ttf";
        TextView head = (TextView)findViewById(R.id.main_titulo);
        Button player = (Button)findViewById(R.id.jugador);
        Button help = (Button)findViewById(R.id.ayuda);
        Button exit = (Button)findViewById(R.id.salida);

        Typeface fuente = Typeface.createFromAsset(getAssets(), folder);
        head.setTypeface(fuente);
        player.setTypeface(fuente);
        help.setTypeface(fuente);
        exit.setTypeface(fuente);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        change_font();
    }

    public void modo_juego(View view){
        Intent i = new Intent(this,LoginActivity.class);
        startActivity(i);
    }

    public void salir(View view){this.finish();}

    public void ayuda(View view){
           HelpDialog dialog = new HelpDialog();
           dialog.show(getSupportFragmentManager(),"Dialog");
    }


}
