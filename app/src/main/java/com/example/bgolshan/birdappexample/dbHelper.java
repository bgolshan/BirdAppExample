package com.example.bgolshan.birdappexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bgolshan on 2/21/2017.
 */

public class dbHelper extends SQLiteOpenHelper {


    private static int DB_VERSION  = 1;
    private static String DB_NAME  = "BIRD_DB";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DESCRIPTION = "description";

    private static final String TABLE_BIRD_NAME = "birds";





    public dbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BIRDS_TABLE = "CREATE TABLE " + TABLE_BIRD_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT, "
                + KEY_DESCRIPTION + " TEXT" + ")";
        db.execSQL(CREATE_BIRDS_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BIRD_NAME);

        // Create tables again
        onCreate(db);
    }




    //CRUD


    public void addBird(Bird bird) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NAME, bird.getName()); // Contact Name
        values.put(KEY_DESCRIPTION, bird.getDescription()); // Contact Phone Number

        // Inserting Row
        db.insert(TABLE_BIRD_NAME, null, values);
        db.close(); // Closing database connection
    }



    public Bird getBird(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_BIRD_NAME, new String[] { KEY_ID,
                        KEY_NAME, KEY_DESCRIPTION }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Bird bird = new Bird(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return contact
        return bird;
    }



    public List<Bird> getAllContacts() {
        List<Bird> birdList = new ArrayList<Bird>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_BIRD_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Bird bird = new Bird();
                bird.setId(Integer.parseInt(cursor.getString(0)));
                bird.setName(cursor.getString(1));
                bird.setDescription(cursor.getString(2));
                // Adding contact to list
                birdList.add(bird);
            } while (cursor.moveToNext());
        }

        // return contact list
        return birdList;
    }




    public int getBirdsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_BIRD_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }



    public int updateBird(Bird bird) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, bird.getName());
        values.put(KEY_DESCRIPTION, bird.getDescription());

        // updating row
        return db.update(TABLE_BIRD_NAME, values, KEY_ID + " = ?",
                new String[] { String.valueOf(bird.getId()) });
    }



    public void deleteBird(Bird contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BIRD_NAME, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getId()) });
        db.close();
    }





}
