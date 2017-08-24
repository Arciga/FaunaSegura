package com.example.nachox.faunasegura;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nacho on 15/08/2017.
 */
public class Dbase extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "faunasegura.db";

    public static final String TABLA_NOMBRES = "usuario";
    public static final String TABLA_mascotas = "mascotas";
    public static final String COLUMNA_ID = "_id";
    public static final String COLUMNA_NOMBRE = "nombre";


    private static final String SQL_CREAR  = "create table "
            + TABLA_NOMBRES + "(" + COLUMNA_ID
            + " integer primary key autoincrement, " + COLUMNA_NOMBRE
            + " text not null);";
    private static final String SQL_CREARMASCOTA  = "create table "
            + TABLA_mascotas + "(" + COLUMNA_ID
            + " integer primary key autoincrement, " + COLUMNA_NOMBRE
            + " text not null);";


    public Dbase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREAR);
        db.execSQL(SQL_CREARMASCOTA);
    }
    public void onCreatemascotas(SQLiteDatabase db) {
      //  db.execSQL(SQL_CREAR);
        db.execSQL(SQL_CREARMASCOTA);
    }



    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_NOMBRES);
        onCreate(db);
    }
    public void agregar(String nombre){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMNA_NOMBRE, nombre);

        db.insert(TABLA_NOMBRES, null,values);
        db.close();

    }
    public void agregarmascota(String nombre){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMNA_NOMBRE, nombre);

        db.insert(TABLA_mascotas, null,values);
        db.close();

    }
    public String  obtener(int id){

        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMNA_ID, COLUMNA_NOMBRE};

        Cursor cursor =
                db.query(TABLA_NOMBRES,
                        projection,
                        " _id = ?",
                        new String[] { String.valueOf(id) },
                        null,
                        null,
                        null,
                        null);


        if (cursor != null && cursor.moveToFirst()) {
            cursor.moveToFirst();

            System.out.println("El nombre es " + cursor.getString(1));
            db.close();
        }
        return cursor.getString(1);
    } public String  obtenermascota(int id){

        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMNA_ID, COLUMNA_NOMBRE};

        Cursor cursor =
                db.query(TABLA_mascotas,
                        projection,
                        " _id = ?",
                        new String[] { String.valueOf(id) },
                        null,
                        null,
                        null,
                        null);


        if (cursor != null && cursor.moveToFirst()) {
            cursor.moveToFirst();

            //System.out.println("El nombre es " + cursor.getString(1));
            db.close();
        }
        return cursor.getString(1);
    }
    public void actualizar (String nombre, int id){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nombre",nombre);

        int i = db.update(TABLA_NOMBRES,
                values,
                " _id = ?",
                new String[] { String.valueOf( id ) });
        db.close();

    }
    public boolean eliminar() {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.execSQL("DROP TABLE IF EXISTS " + TABLA_NOMBRES);
            db.execSQL("DROP TABLE IF EXISTS " + TABLA_mascotas);
        onCreate(db);
            db.close();
     //System.out.println("tabla "+TABLA_NOMBRES+" Eliminada");
            return true;

        }catch(Exception ex){
            return false;
        }
    }
    public boolean eliminartablamascotas() {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
           // db.execSQL("DROP TABLE IF EXISTS " + TABLA_NOMBRES);
            db.execSQL("DROP TABLE IF EXISTS " + TABLA_mascotas);
            onCreatemascotas(db);
            db.close();
            //System.out.println("tabla "+TABLA_NOMBRES+" Eliminada");
            return true;

        }catch(Exception ex){
            return false;
        }
    }
}
