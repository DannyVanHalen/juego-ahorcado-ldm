package com.example.dani.ahorcado;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import java.util.ArrayList;

/**
 * Created by dani on 8/04/17.
 */

public class Ahorcado {

    private static final int MAXINTENTOS = 7;
    private static final int PUNTOS_INTENTO = 100;
    private static final char VACIO = '_';

    private String[] diccionario;
    private char[] letras_usadas;
    private int letra; //indice de letra
    private String palabra;
    private String palabra_formada;
    private int intentos;
    private AppCompatActivity activity;
    //private Jugador jugador;


    public Ahorcado(AppCompatActivity activity){
        this.activity = activity;
        boolean palabra_formada = false;
        while(!palabra_formada){
            try{
                this.diccionario = IOStream.openDiccionary(this.activity);
                this.palabra = randomWord();
                this.palabra_formada = hideChars();
                this.intentos = MAXINTENTOS;
                this.letras_usadas = new char[30];
                this.letra = 0;
                palabra_formada = this.palabra != null && this.palabra_formada != null;
            }catch (Exception ex){
                palabra_formada = false;
            }
        }
        Log.d("Ahorcado","Palabra :"+this.palabra);
        Log.d("Ahoracdo","Palabra Formada "+this.palabra_formada);
    }

    protected String randomWord(){
       //return this.diccionario.get((int)(Math.random()*this.diccionario.size()));
        return this.diccionario[(int)(Math.random()*this.diccionario.length)];
    }

    private String hideChars(){
        String exit = "";
        for(int i = 0; i <this.palabra.length();i++){
            exit +="_ ";
        }
        return exit;
    }

    private int[] wordPositions(char c){
        int [] postitions = new int[this.apariciones(c)];
        int contador = 0;
        for(int i = 0; i < this.palabra.length();i++){
            if(this.palabra.charAt(i) == c){
                postitions[contador] = 2*i;
                contador++;
            }
        }
        return postitions;
    }

    private boolean eatenWord(char c){
        int contador = 0;
        for(int i = 0; i < this.letra ; i++){
            if(this.letras_usadas[i] == c){
                contador++;
            }
        }
        return contador>0;
    }

    private String destaparLetras(char c){
        char [] letras = this.palabra_formada.toCharArray();
        int [] posiciones = this.wordPositions(c);

        for(int i = 0; i < posiciones.length; i++){
            letras[posiciones[i]] = c;
        }

        return String.valueOf(letras);

    }

    public String nuevaPalabra(){
        boolean formada = false;
        while(!formada){
            try{
                this.palabra = this.randomWord();
                this.palabra_formada = this.hideChars();
                this.intentos = MAXINTENTOS;
                this.letras_usadas = new char[30];
                this.letra = 0;
                formada = true;
            }catch (Exception ex){
                formada = false;
            }
        }

        return this.palabra_formada;
    }

    /**

    public boolean palabra_resuelta(){
        return this.palabra_formada.equals(this.palabra) && this.intentos >= 1;
    }

    public boolean gana_partida(){
        return this.palabra_resuelta();
    }

     */

    public boolean aparece(char c){
        return this.apariciones(c) > 0;
    }


    public boolean checkWord(char c){
        boolean encontrado = this.aparece(c);
        if(encontrado){
            this.palabra_formada = this.destaparLetras(c);
            this.letras_usadas[this.letra++] = c;
        }else{
            if(!this.eatenWord(c)){
                this.intentos--;
                this.letras_usadas[this.letra] = c;
            }
        }
        return encontrado;
    }


    public boolean fin_partida(){
        for(int i = 0; i < this.palabra_formada.length();i++){
            if(this.palabra_formada.charAt(i) == '_') return false;
        }
        return true;
    }

    public int getIntentos(){
        return this.intentos;
    }

    public int getPuntuacion(){
        return this.intentos*PUNTOS_INTENTO;
    }

    public String getPalabra() {
        return this.palabra;
    }

    public String getPalabra_formada(){
        return this.palabra_formada;
    }

    public String trail(){
        char c = '-';
        while(!fin_partida()){
            c = this.palabra.charAt((int)(Math.random()*this.palabra.length()));
            if(!this.eatenWord(c)){
                this.checkWord(c);
                return c+"";
            }
        }
        return c+"";
    }


    public int apariciones(char c){
        int contador = 0;
        for(int i = 0; i < this.palabra.length();i++){
            if(this.palabra.charAt(i) == c){
                contador++;
            }
        }
        return contador;
    }

    public String autoresolver(){
        //if(this.palabra.length() == this.palabra_formada.length()){
            this.palabra_formada = this.palabra;
        return this.palabra_formada;
    }





}
