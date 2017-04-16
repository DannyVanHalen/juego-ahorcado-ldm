package com.example.dani.ahorcado;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

/**
 * Created by dani on 16/04/17.
 */

public class HelpDialog extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage("Registrate, o Logeate para poder acceder al juego. Consigue resolver la palabra oculta en pocos turnos"+
                "para alcanzar la  puntuación más alta y ser el mejor en la clasificación general. Si quieres iniciar partida, sólo tienes que pulsar sobre la horca");

        builder.setTitle("Informacion");

        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });



        // Create the AlertDialog object and return it
        return builder.create();
    }


    public void show(FragmentManager supportFragmentManager, String dialog) {
    }
}
