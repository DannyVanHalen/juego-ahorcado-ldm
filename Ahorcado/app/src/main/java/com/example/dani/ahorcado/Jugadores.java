package com.example.dani.ahorcado;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

/**
 * Created by dani on 11/04/17.
 */

public class Jugadores {

    private ArrayList<Jugador> jugadores;

    public Jugadores(){
        this.jugadores = new ArrayList<>();
    }
    public Jugadores(ArrayList<Jugador> jugadores){
        this.jugadores = jugadores;
    }

    public ArrayList<Jugador> getJugadores(){
        return this.jugadores;
    }



    public boolean addJugador(Jugador jugador){
        return this.jugadores.add(jugador);
    }

    public boolean isEmpty(){ return this.isEmpty(); }

    public void ordenarTop(){
        /**Collections.sort(this.jugadores, new Comparator<Jugador>() {
            @Override
            public int compare(Jugador o1, Jugador o2) {
                return new Integer(o1.getPuntuacion()).compareTo(new Integer(o2.getPuntuacion()));
            }
        });*/
        Comparator<Jugador> comparator = Collections.reverseOrder();
        Collections.sort(this.jugadores,comparator);
   }




}
