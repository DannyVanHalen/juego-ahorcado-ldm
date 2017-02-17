package com.example.dani.ahorcado;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dani on 19/10/16.
 */

public class BBDD_Jugadores extends SQLiteOpenHelper {


    private String sqlTable; //"CREATE TABLE "+BBDD_NAME+" (NOMBRE TEXT PRIMARY KEY, PASS TEXT, PUNTUACION INTEGER)";
    private String dropSqlTable;
    private SQLiteDatabase db;

    public BBDD_Jugadores(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, String sqlTable, String dropSqlTable) {
        super(context, name, factory, version);
        this.sqlTable = sqlTable;
        this.dropSqlTable = dropSqlTable;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(this.sqlTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(this.dropSqlTable);
        db.execSQL(this.sqlTable);
    }


}
