package com.example.dani.ahorcado;

import android.app.Activity;
import android.content.res.AssetManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by dani on 9/04/17.
 */

public class IOStream {

    static final int MIN = 4, MAX = 12, TAM_ARRAY = 126;



    @Nullable
    public static String[] openDiccionary(AppCompatActivity activity){
        //ArrayList<String> salida = new ArrayList<>();
        String [] salida = new String[TAM_ARRAY];
        try{
            AssetManager am = activity.getAssets();
            InputStream is;
            is = am.open("diccionario.txt");
            Scanner in = new Scanner(is);
            int count = 0;
            String s;
            //in.nextLine();

            while(in.hasNext() && count < salida.length){
                //s = in.nextLine().split("")[0];
                s = in.nextLine();
                if(s.length()>MIN && s.length()<=MAX && !s.contains("\t")){
                    salida[count] = limpiar(s);
                    //if(salida.add(s)) {
                    Log.d("IOStream class", "Palabra encontrada nº" + count + ": " + s);
                    count++;
                    //}
                }
            }

            in.close();
            //return salida;

        }catch (Exception e){}

        return salida;

    }

    public static String limpiar(String s){
        String cLimpiar = "áàéèíìóòúù";
        String cLimpios = "aaeeiioouu";

        for(int i = 0; i <cLimpiar.length(); i++){
            s = s.replace(cLimpiar.charAt(i),cLimpios.charAt(i));
        }

        return s;

    }


}
