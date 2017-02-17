package controller;

/**
 * Created by dani on 18/10/16.
 */

public class Jugador {

    private String nombre;
    private String pass;
    private int puntuacion;



    public Jugador(String nombre, String pass, int puntuacion){
        this.nombre = nombre;
        this.pass = pass;
        this.puntuacion = puntuacion;
    }

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
}
