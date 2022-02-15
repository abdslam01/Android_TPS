package com.example.androidexamproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Date;

public class Database extends SQLiteOpenHelper {

    private static String DB_NAME = "Chantiers";
    private static final int DB_VERSION = 1;

    public Database(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+DB_NAME+" (" +
                "id INTEGER PRIMARY KEY," +
                "avenue TEXT NOT NULL," +
                "ville TEXT NOT NULL," +
                "lat REAL NOT NULL," +
                "lng REAL NOT NULL," +
                "date_debut TEXT NOT NULL," +
                "date_fin TEXT NOT NULL," +
                "observation TEXT" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean insertData(
            int id, String avenue, String ville, float lat, float lng,
            String date_debut, String date_fin, String observation
    ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues =
                getContentValues(id, avenue, ville, lat, lng, date_debut, date_fin, observation);
        return db.insert(DB_NAME, null, contentValues) == -1;
    }

    public boolean updateData(
            int id, String avenue, String ville, float lat, float lng,
            String date_debut, String date_fin, String observation
    ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues =
                getContentValues(id, avenue, ville, lat, lng, date_debut, date_fin, observation);
        Cursor cursor = db.rawQuery("select * from "+DB_NAME+" where id=?",
                new String[] {id+""});
        if(cursor.getCount() > 0)
            return db.update(DB_NAME, contentValues,
                    "id=?", new String[] {id+""}) != -1;
        return false;
    }

    public boolean deleteData(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+DB_NAME+" where id=?",
                new String[] {id+""});
        if(cursor.getCount() > 0)
            return db.delete(DB_NAME, "id=?",
                    new String[] {id+""}) != -1;
        return false;
    }
    public Cursor selectData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from " + DB_NAME,null);
    }

    public Cursor selectDataById(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from "+DB_NAME+" where id=?",
                new String[] {id+""});
    }

    public void deleteAllData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.rawQuery("delete from " + DB_NAME,null);
    }

    public int getLastInsertedId(){
        Cursor cursor = this.getWritableDatabase()
                .rawQuery("SELECT max(id) from " + DB_NAME, null);
        int rowId;
        if(cursor.getCount() == 0)
            rowId = 0;
        else{
            cursor.moveToFirst();
            rowId = cursor.getInt(0);
        }
        cursor.close();
        return rowId;
    }

    private ContentValues getContentValues(
            int id, String avenue, String ville, float lat, float lng,
            String date_debut, String date_fin, String observation
    ) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("avenue", avenue);
        contentValues.put("ville", ville);
        contentValues.put("lat", lat);
        contentValues.put("lng", lng);
        contentValues.put("date_debut", String.valueOf(date_debut));
        contentValues.put("date_fin", String.valueOf(date_fin));
        contentValues.put("observation", observation);
        return contentValues;
    }
}
