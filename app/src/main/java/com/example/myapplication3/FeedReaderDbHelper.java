package com.example.myapplication3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * A class handling actions with database.
 */
public class FeedReaderDbHelper extends SQLiteOpenHelper {
    /**
     * A class defining construction of database.
     */
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_ID = "title";
        public static final String COLUMN_NAME_CONTENT = "content";
        public static final String COLUMN_NAME_DETAILS = "details";
        private static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                        FeedEntry._ID + " INTEGER PRIMARY KEY," +
                        FeedEntry.COLUMN_NAME_ID + " TEXT," +
                        FeedEntry.COLUMN_NAME_CONTENT + " TEXT," +
                        FeedEntry.COLUMN_NAME_DETAILS + " TEXT)";

        private static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;
    }

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";
    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FeedEntry.SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(FeedEntry.SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
    public void insertData(String id, String content, String details ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME_ID, id);
        values.put(FeedEntry.COLUMN_NAME_CONTENT, content);
        values.put(FeedEntry.COLUMN_NAME_DETAILS, details);
        long newRowId = db.insert(FeedEntry.TABLE_NAME, null, values);
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + FeedEntry.TABLE_NAME + " ORDER BY " + FeedEntry.COLUMN_NAME_ID  + " ASC",null);
        return res;
    }
    public boolean saveRating(float rating, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME_CONTENT,String.valueOf(rating));
        Log.d("question: ", name);
        Log.d("Database affected: ", String.valueOf(db.update(FeedEntry.TABLE_NAME, values, "title = ?", new String[] { name } )));
        return true;
    }
    public void clearAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        int deletedRows = db.delete(FeedEntry.TABLE_NAME, null, null);
    }
}
