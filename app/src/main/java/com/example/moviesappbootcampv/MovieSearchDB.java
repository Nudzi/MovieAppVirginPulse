package com.example.moviesappbootcampv;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MovieSearchDB {
    public static final String KEY_ROWID = "_id";
    public static final String KEY_NAME = "person_name";

    private final String DATABASE_NAME = "MovieSearchDB";
    private final String DATABASE_TABLE = "MovieTable";
    private final int DATABASE_VERSION = 1;

    private DBHelper ourHelper;
    private final Context context;
    private SQLiteDatabase ourDatabase;
    String data = "";

    public MovieSearchDB (Context context){
        this.context = context;
    }

    private class DBHelper extends SQLiteOpenHelper{

        public DBHelper (Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

            String sqlCode = "CREATE TABLE " + DATABASE_TABLE + " (" +
                    KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_NAME + " TEXT NOT NULL);";

            sqLiteDatabase.execSQL(sqlCode);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
            onCreate(sqLiteDatabase);
        }
    }

    public void open() throws SQLException{
        ourHelper = new DBHelper(context);
        ourDatabase = ourHelper.getWritableDatabase();
    }

    public void close(){
        ourHelper.close();
    }

    public void createEntry(String name){
        String [] columns = new String[] {KEY_ROWID, KEY_NAME};

        //reading data
        Cursor cursor = ourDatabase.query(DATABASE_TABLE, columns,null,
                null, null, null, null);
        ArrayList<String> result = new ArrayList<String>();

        int iName = cursor.getColumnIndex(KEY_NAME);

        boolean exist = false;

        cursor.isAfterLast();
        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
            if(cursor.getString(iName).equals(name))
                exist = true;

        cursor.close();ContentValues contentValues = new ContentValues();
        if(!exist) {
            contentValues.put(KEY_NAME, name);
            ourDatabase.insert(DATABASE_TABLE, null, contentValues);
            return;
        }
        contentValues.put(KEY_NAME, "");
        ourDatabase.insert(DATABASE_TABLE, null, contentValues);
    }

    public ArrayList<String> getData(){
        String [] columns = new String[] {KEY_ROWID, KEY_NAME};

        Cursor cursor = ourDatabase.query(DATABASE_TABLE, columns,null,
                null, null, null, null);
        ArrayList<String> result = new ArrayList<String>();

        int iName = cursor.getColumnIndex(KEY_NAME);

        int cursorSize = 0, cursorCount = 0;
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            if(!cursor.getString(iName).equals("")) {
                cursorSize++;
            }
        }
        if(cursorSize<10) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                if(!cursor.getString(iName).equals("")) {
                    result.add(cursor.getString(iName));
                }
            }
        }
        else{
            for (cursor.moveToLast(); !cursor.isBeforeFirst(); cursor.moveToPrevious()) {
                if(cursorCount == 10)break;
                if(!cursor.getString(iName).equals("")) {
                    result.add(cursor.getString(iName));
                    cursorCount++;
                }
            }
        }
        cursor.close();
        return  result;
    }
}
