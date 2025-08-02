package com.redrd.restdb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class GestorDb extends SQLiteOpenHelper {
    public GestorDb(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase AppDb) {
        AppDb.execSQL("Create table Vehiculos (" +
                "placa text primary key,"+"marca text,"+"modelo text,"+"precio real)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
