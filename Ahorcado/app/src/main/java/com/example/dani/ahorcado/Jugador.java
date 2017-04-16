package com.example.dani.ahorcado;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by dani on 18/10/16.
 */

public class Jugador implements Comparable<Jugador>{

    //private String mail;
    private String nombre;
    private String pass;
    private int puntuacion;



    public Jugador(String nombre, String pass, Integer puntuacion){
        this.nombre = nombre;
        this.pass = pass;
        this.puntuacion = puntuacion;
    }

    /**
    protected Jugador(Parcel in){
        this.nombre = in.readString();
        this.pass = in.readString();
        this.puntuacion = in.readInt();
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(nombre);
        dest.writeString(pass);
        dest.writeInt(puntuacion);
    }*/

   // public String getMail(){ return  this.mail; }

    //public void setMail(String mail){ this.mail = mail; }



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    /**
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Jugador> CREATOR =
            new Parcelable.Creator<Jugador>(){
                @Override
                public Jugador createFromParcel(Parcel in){
                  return new Jugador(in);
                }

                @Override
                public Jugador[] newArray(int size){
                    return new Jugador[size];
                }

            };*/


    @Override
    public int compareTo(Jugador jugador){
        if(this.puntuacion < jugador.puntuacion){
            return -1;
        }
        if(this.puntuacion > jugador.puntuacion){
            return 1;
        }

        return 0;
    }

    @Override
    public String toString() { return "Nombre: "+this.nombre+" -> "+this.puntuacion+" puntos"; }

}
